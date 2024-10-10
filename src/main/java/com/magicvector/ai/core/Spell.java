package com.magicvector.ai.core;

import com.magicvector.ai.model.Arg;
import lombok.Data;

import java.util.List;

/**
 * Call的添加方式有：
 * 1. 通过代码进行注册，工程师在代码中自己构造Spell对象。<br>
 * 2. 通过继承API接口，注册器自动发现和注册Spell对象。<br>
 * 3. 通过用户手动录入API的信息，系统帮助用户构造Spell对象。<br>
 * 4. 通过Swagger等接口文档系统导入，系统自动构造和注册Spell对象。<br>
 * 5. 通过MagicGPT注解标注某个静态方法，该方法则会被自动构造和注册Spell对象。<br>
 */
@Data
public class Spell {

    private final String apiName;
    private final String description;
    private final String className;
    private final String methodName;
    /**
     * 需要GPT提供的参数
     */
    private final List<Arg> gptFeedArgs;

    private Spell(Builder builder) {
        this.apiName = builder.apiName;
        this.description = builder.description;
        this.className = builder.className;
        this.methodName = builder.methodName;
        this.gptFeedArgs = builder.feedArgs;
    }


    public static class Builder {
        private String apiName;

        private String description;

        private String className;

        private String methodName;

        private List<Arg> feedArgs;

        public Builder setApiName(String apiName) {
            this.apiName = apiName;
            return this;
        }
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setClassName(String className) {
            this.className = className;
            return this;
        }

        public Builder setMethodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder setFeedArgs(List<Arg> feedArgs) {
            this.feedArgs = feedArgs;
            return this;
        }


        public Spell build() {
            return new Spell(this);
        }
    }
}
