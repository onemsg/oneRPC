package com.onemsg.onerpc.core.model;

import java.io.Serializable;
import java.util.Objects;

public final class ResponseModel implements Serializable {

    private static final long serialVersionUID = -7533173038897247882L;

    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(result);
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
        return Objects.equals(result, other.result);
    }

    @Override
    public String toString() {
        return "ResponseModel [result=" + result + "]";
    }

    
}
