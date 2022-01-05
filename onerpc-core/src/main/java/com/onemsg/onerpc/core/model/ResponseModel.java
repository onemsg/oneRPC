package com.onemsg.onerpc.core.model;

import java.util.Objects;

/**
 * rpc 统一响应模型
 * 
 * @author mashugang
 * @since 2021
 */
public final class ResponseModel {

    /** 结果全限定类名 */
    private String type;

    /** 结果 */
    private Object value;

    public Object getValue() {
        return value;
    }

    public void setResult(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResponseModel other = (ResponseModel) obj;
        return Objects.equals(value, other.value) && Objects.equals(type, other.type);
    }

    @Override
    public String toString() {
        return "ResponseModel [value=" + value + ", type=" + type + "]";
    }   
}
