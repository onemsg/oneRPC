package com.onemsg.onerpc.example;

import com.onemsg.onerpc.server.OneRpcServer;

/**
 * RpcServer
 */
public class RpcServer {

    public static void main(String[] args) {
        
        HelloService hello = new HelloServiceImple();
        OneRpcServer.publish(hello, HelloService.class);
        OneRpcServer.end();
    }
}
