package cn.lanehub.ai.executors.call;

import cn.lanehub.ai.core.call.CallSpell;
import cn.lanehub.ai.exceptions.Assert;
import cn.lanehub.ai.executors.ITask;
import cn.lanehub.ai.model.Arg;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CallTask implements ITask {



    private CallSpell callSpell;

    private Map<String,String> argValueMap;


    public CallTask(CallSpell callSpell, List<String> argList){
        this.callSpell = callSpell;
        Assert.judge(callSpell.getGptFeedArgs().size() == argList.size(), "The input parameters provided by GPT is in bad format.");
        argValueMap = new HashMap<>();
        for(int i = 0 ; i < argList.size(); i++){
            Arg arg = callSpell.getGptFeedArgs().get(i);
            argValueMap.put(arg.getName(), argList.get(i));
        }
    }

}
