package com.onemsg.onerpc.client.Core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.onemsg.onerpc.core.model.RequestModel;

class RpcInvocationHandler implements InvocationHandler {

    private String className;
    private RpcHttpStub stub;

    protected <T> RpcInvocationHandler(Class<T> interfaceClass, RpcHttpStub stub){
        className = interfaceClass.getName();
        this.stub = stub;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestModel requestModel = new RequestModel();
        requestModel.setClassName(className);
        requestModel.setMethodName(method.getName());
        requestModel.setParameterTypes(method.getParameterTypes());
        requestModel.setParams(args);
        return stub.call(requestModel);
    }
}