package cn.lanehub.ai.executors.call.impl;

import cn.lanehub.ai.core.call.CallSpell;
import cn.lanehub.ai.core.call.CallSpellType;
import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.executors.ITask;
import cn.lanehub.ai.executors.call.CallTask;
import cn.lanehub.ai.executors.call.ICallExecutor;
import cn.lanehub.ai.local.function.impl.InstanceFunction;
import cn.lanehub.ai.local.function.impl.StaticFunction;
import cn.lanehub.ai.network.impl.restful.GetRestfulAccess;
import cn.lanehub.ai.network.impl.restful.PostRestfulAccess;
import org.apache.commons.lang.text.StrSubstitutor;

import java.util.Map;

public class CallExecutor implements ICallExecutor {

    public static final ICallExecutor INSTANCE = new CallExecutor();

    private CallExecutor() {
    }

    @Override
    public String execute(ITask task) {
        Assert.judge(task instanceof CallTask, "The call executor can only execute CallTask, the input task is " + task.getClass().getTypeName());
        CallTask callTask = (CallTask) task;
        CallSpell callSpell = callTask.getCallSpell();
        CallSpellType callSpellType = callSpell.getCallSpellType();

        String url = callSpell.getUrl();
        switch (callSpellType){
            case REST_POST_CALL:{
                return PostRestfulAccess.INSTANCE.access(url,  this.getHeaders(callTask), this.getQuerys(callTask), this.getBody(callTask));
            }
            case REST_GET_CALL:{
                return GetRestfulAccess.INSTANCE.access(url, null, this.getQuerys(callTask), null);
            }
            case LOCAL_FUNCTION_CALL: {
                return InstanceFunction.INSTANCE.execute(url, callSpell.getGptFeedArgs(), callTask.getArgValueMap());
            }
            case LOCAL_STATIC_FUNCTION_CALL: {
                return StaticFunction.INSTANCE.execute(url, callSpell.getGptFeedArgs(), callTask.getArgValueMap());
            }
            default: {
            }
        }

        return "";
    }



    Map<String, String> getHeaders( CallTask callTask) {

        return callTask.getCallSpell().getHeaders();
    }

    Map<String, String> getQuerys( CallTask callTask){
        return callTask.getCallSpell().getQuerys();
    }

    String getBody( CallTask callTask){

        StrSubstitutor strSubstitutor = new StrSubstitutor(callTask.getArgValueMap());
        String body = strSubstitutor.replace(callTask.getCallSpell().getRequestTemplate());
        return body;
    }
}
