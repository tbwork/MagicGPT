package cn.lanehub.ai.executors.view;

import cn.lanehub.ai.executors.ITask;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewTask implements ITask {


    private String url;

    private String cookies;

    private boolean ignoreImage;

    private boolean ignoreVideo;

}
