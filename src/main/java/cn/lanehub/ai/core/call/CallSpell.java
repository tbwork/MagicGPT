package cn.lanehub.ai.core.call;

import cn.lanehub.ai.core.spell.ISpell;
import cn.lanehub.ai.model.Arg;
import cn.lanehub.ai.model.SpellType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Call的添加方式有：
 * 1. 通过代码进行注册，工程师在代码中自己构造CallSpell对象。<br>
 * 2. 通过继承API接口，注册器自动发现和注册CallSpell对象。<br>
 * 3. 通过用户手动录入API的信息，系统帮助用户构造CallSpell对象。<br>
 * 4. 通过Swagger等接口文档系统导入，系统自动构造和注册CallSpell对象。<br>
 * 5. 通过MagicGPT注解标注某个方法，该方法则会被自动构造和注册CallSpell对象。<br>
 */
@Data
public class CallSpell implements ISpell {

    private final String apiName;
    private final String description;
    private final String url;
    /**
     * 需要GPT提供的参数
     */
    private final List<Arg> gptFeedArgs;
    private final CallSpellType callSpellType;
    private final Map<String, String> headers;
    private final Map<String, String> querys;
    /**
     * 如
     * {
     *     "name":${name}
     * }
     * 其中${name}为gptFeedArgs中的某个参数。
     *
     */
    private final String requestTemplate;


    private CallSpell(Builder builder) {
        this.apiName = builder.apiName;
        this.url = builder.url;
        this.gptFeedArgs = builder.gptFeedArgs;
        this.callSpellType = builder.callSpellType;
        this.headers = builder.headers;
        this.querys = builder.querys;
        this.requestTemplate = builder.requestTemplate;
        this.description = builder.description;
    }

    @Override
    public SpellType getType() {
        return SpellType.CALL;
    }

    public static class Builder {
        private String apiName;

        private String description;
        private String url;
        private List<Arg> gptFeedArgs;
        private CallSpellType callSpellType;
        private Map<String, String> headers;
        private Map<String, String> querys;
        private String requestTemplate;


        public Builder setApiName(String apiName) {
            this.apiName = apiName;
            return this;
        }


        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }


        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setGptFeedArgs(List<Arg> gptFeedArgs) {
            this.gptFeedArgs = gptFeedArgs;
            return this;
        }

        public Builder setCallSpellType(CallSpellType callSpellType) {
            this.callSpellType = callSpellType;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder setQuerys(Map<String, String> querys) {
            this.querys = querys;
            return this;
        }

        public Builder setRequestTemplate(String requestTemplate) {
            this.requestTemplate = requestTemplate;
            return this;
        }

        public CallSpell build() {
            return new CallSpell(this);
        }
    }
}
