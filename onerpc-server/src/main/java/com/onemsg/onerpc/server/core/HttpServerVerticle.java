package com.onemsg.onerpc.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpConnection;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.net.SocketAddress;

public class HttpServerVerticle extends AbstractVerticle {
    
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 18080;
    private static final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);
    private RpcHttpStub requestHandler;

    @Override
    public void start() throws Exception {

        ServiceDispatcher dispatcher = new ServiceDispatcher();
        requestHandler = new RpcHttpStub(dispatcher);

        HttpServerOptions options = new HttpServerOptions();
        options
            .setHost(config().getString("host"))
            .setPort(config().getInteger("port").intValue())
            .setSsl(false)
            .setMaxWebSocketFrameSize(1000000);

        HttpServer server = vertx.createHttpServer(options);

        server.connectionHandler(this::handleConnection);
        server.requestHandler(this::handleRequest);
        server.exceptionHandler(this::handleException);

        server.listen(result -> {
            if(result.succeeded()){
                logger.info("RPC Http Server 已启动: port={}", result.result().actualPort());
            }else{
                logger.error("RPC Http Server 启动失败", result.cause());
            }
        });

    }

    private void handleConnection(HttpConnection connection){
        SocketAddress address = connection.remoteAddress();
        logger.info("建立新连接: {}:{}", address.host(), address.port());
        connection.closeHandler( e -> logger.info("连接已关闭: {}:{}", address.host(), address.port()));
    }

    private void handleRequest(HttpServerRequest request){
        // logger.info("http version : {}", request.version());
        requestHandler.handleRequest(request);
        request.exceptionHandler(this::handleException);
    }

    private void handleException(Throwable t){
        logger.warn("http 连接出现错误", t);
    }

}
