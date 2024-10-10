package com.magicvector.ai.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Arg {

    /**
     * 是否为数字
     */
    boolean isNumber;

    /**
     * 参数名称
     */
    String name;

    /**
     * 参数描述，注意只有当参数名称可以直接传达出参数的含义时，这个字段才建议为空，否则可能会出差错
     */
    String description;

    boolean required;

}
