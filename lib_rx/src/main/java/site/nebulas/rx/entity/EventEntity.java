package site.nebulas.rx.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nebula
 * @version 1.0.0
 * @date 2018/9/10
 */
public class EventEntity {
    private int code;
    private String name;

    private Object data;

    private EventEntity(Builder builder){
        this.code = builder.code;
        this.name = builder.name;
        this.data = builder.data;
    }

    public static class Builder{
        private int code;
        private String name;
        private Object data;
        private Map<String, Object> objectMap = new HashMap<>();

        public Builder setCode(int code){
            this.code = code;
            return this;
        }
        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setData(Object data){
            this.data = data;
            return this;
        }

        public Builder putData(String key, Object data){
            objectMap.put(key, data);
            this.data = objectMap;
            return this;
        }

        public EventEntity build(){
            return new EventEntity(this);
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData(String key) {
        return ((Map)data).get(key);
    }
}
