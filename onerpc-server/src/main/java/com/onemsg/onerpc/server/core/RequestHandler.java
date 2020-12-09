package com.onemsg.onerpc.server.core;

import com.onemsg.onerpc.core.model.Models;
import com.onemsg.onerpc.core.model.RequestModel;
import com.onemsg.onerpc.core.model.ResponseModel;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;

public class RequestHandler{
    
    private ServiceDispatcher dispatcher;

    public RequestHandler(ServiceDispatcher dispatcher){
        this.dispatcher = dispatcher;
    }

    void handleRequest(HttpServerRequest request){
        request.bodyHandler(buffer -> {
            byte[] bytes = handleBytes(buffer.getBytes());
            request.response().end(Buffer.buffer(bytes));
        });
    }

    byte[] handleBytes(byte[] requestBytes){
        RequestModel requestModel = Models.toRequestModel(requestBytes);
        ResponseModel responseModel = handle(requestModel);
        return Models.toBytes(responseModel);
    }

    ResponseModel handle(RequestModel requestModel){
        return dispatcher.invoke(requestModel);
    }
}
