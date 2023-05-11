package cn.lanehub.ai.executors.search;

import cn.lanehub.ai.executors.ITask;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class SearchTask implements ITask {

    private String engineName;

    private String keywords;

    private Date startDate;

    private Date endDate;

}
