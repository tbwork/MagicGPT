package cn.lanehub.ai.executors.view.impl;

import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.executors.ITask;
import cn.lanehub.ai.executors.view.IViewExecutor;
import cn.lanehub.ai.executors.view.ViewTask;
import cn.lanehub.ai.network.impl.simulate.GetSimulateAccess;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ViewExecutor implements IViewExecutor {

    public static final IViewExecutor INSTANCE = new ViewExecutor();

    @Override
    public String execute(ITask task) {
        Assert.judge(task instanceof ViewTask, "The view executor can only execute SearchTask, the input task is " + task.getClass().getTypeName());
        ViewTask viewTask = ((ViewTask) task);

        String domString = GetSimulateAccess.INSTANCE.access(viewTask.getUrl(), null, null, null);

        return this.getHumanReadableText(domString);

    }


    private String getHumanReadableText(String domString){
        StringBuilder stringBuilder = new StringBuilder();
        Document document = Jsoup.parse(domString);
        //获取 head 标签 title 信息
        Elements titleList = document.getElementsByTag("title");
        if(titleList != null) {
            for (Element element : titleList) {
                if (isVisible(element)) {
                    stringBuilder.append(element.text() +"\n");
                }
            }
        }
        //获取 body中的可见文本
        Elements bodyList = document.getElementsByTag("body");
        if(bodyList != null && bodyList.size() > 0){
            Element body = bodyList.get(0);
            dealWithViewElement(stringBuilder, body);
        }
        return stringBuilder.toString();
    }

    /**
     * 递归处理dom节点
     * @param stringBuilder
     * @param parentElements
     */
    private void dealWithViewElement(StringBuilder stringBuilder, Element parentElements) {
        Elements allElements = parentElements.children();
        for (Element element : allElements) {
            if (isVisible(element)) {
                //最小标签，然后处理对应的文本操作
                if (element.tagName().equals("script")) {
                    continue;
                }
                //处理换行
                else if (element.tagName().equals("br") || element.tagName().equals("pre") || element.tagName().equals("p")) {
                    stringBuilder.append("\n");
                }
                //处理文字链接
                else if (element.tagName().equals("a")) {
                    stringBuilder.append("[" + element.text() + "]" + "(" + element.attr("href") + ")");
                }
                //处理图片
                else if (element.tagName().equals("image")) {
                    String imageName = element.attr("name");
                    if (StringUtils.isBlank(imageName)) {
                        imageName = "图片";
                    }
                    stringBuilder.append("[" + imageName + "]" + "(" + element.attr("src") + ")");
                }
                //处理视频
                else if (element.tagName().equals("video")) {
                    String videoName = element.attr("name");
                    if (StringUtils.isBlank(videoName)) {
                        videoName = "视频";
                    }
                    stringBuilder.append("[" + videoName + "]" + "(" + element.attr("src") + ")");
                }
                //如果没有子标签了，就按照默认文本处理
                else if (element.children() == null || element.children().size() == 0) {
                    stringBuilder.append(element.text());

                }
                //如果上述条件都不满足，继续递归
                else {
                    dealWithViewElement(stringBuilder, element);
                }

            }
        }
    }

    public static boolean isVisible(Element element) {
        String display = element.attr("style").toLowerCase();
        if (display.contains("display:none") || display.contains("visibility:hidden")) {
            return false;
        }
        return true;
    }
}

