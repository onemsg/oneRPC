package com.onemsg.onerpc.client.Core;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.onemsg.onerpc.core.Loggers;
import com.onemsg.onerpc.core.model.Models;
import com.onemsg.onerpc.core.model.RequestModel;
import com.onemsg.onerpc.core.model.ResponseModel;

import org.slf4j.Logger;


public class RpcHttpStub {

    private final HttpClient client = HttpClient.newHttpClient();

    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(5, 30, 60, 
    TimeUnit.SECONDS, new ArrayBlockingQueue<>(150));
    
    private RegistryUtil registryUtil;

    public RpcHttpStub(RegistryUtil registryUtil){
        this.registryUtil = registryUtil;
    }

    private static final Logger logger = Loggers.getClientLogger();


    public Object call(RequestModel requestModel){

        byte[] bytes = Models.toBytes(requestModel);
        URI uri = registryUtil.lookup(requestModel.getClassName());

        HttpRequest request = HttpRequest.newBuilder(uri).POST(BodyPublishers.ofByteArray(bytes)).build();

        try {
            HttpResponse<byte[]> response = client.send(request, BodyHandlers.ofByteArray());
            if(response.statusCode() == 200){
                ResponseModel responseModel = Models.toResponseModel(response.body());
                return responseModel.getResult();
            }
        } catch (IOException | InterruptedException e) {
            logger.error("rpc http 连接出错", e);
        }
        return null;
    }

    public Future<Object> asyncCall(RequestModel requestModel){
        return CompletableFuture.supplyAsync( () -> {
            return call(requestModel);
        }, EXECUTOR );
    }



}
