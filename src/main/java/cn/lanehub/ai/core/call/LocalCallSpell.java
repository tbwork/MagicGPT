package cn.lanehub.ai.core.call;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocalCallSpell {


    private String packageName;

    private CallSpell innerSpell;


}
