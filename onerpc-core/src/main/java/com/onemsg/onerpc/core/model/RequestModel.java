package com.onemsg.onerpc.core.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public final class RequestModel implements Serializable {

    private static final long serialVersionUID = 1256461449739747811L;

    private String className;
    private String methodName;
    private Object[] params;
    private Class<?>[] parameterTypes;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(parameterTypes);
        result = prime * result + Arrays.deepHashCode(params);
        result = prime * result + Objects.hash(className, methodName);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RequestModel other = (RequestModel) obj;
        return Objects.equals(className, other.className) && Objects.equals(methodName, other.methodName)
                && Arrays.equals(parameterTypes, other.parameterTypes) && Arrays.deepEquals(params, other.params);
    }

    @Override
    public String toString() {
        return "RequestModel [className=" + className + ", methodName=" + methodName + ", parameterTypes="
                + Arrays.toString(parameterTypes) + ", params=" + Arrays.toString(params) + "]";
    }
    
}
