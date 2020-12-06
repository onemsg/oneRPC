package com.onemsg.onerpc.example;

import java.util.stream.IntStream;

import com.onemsg.onerpc.client.OneRpcClient;

public class RpcClient {

    public static void main(String[] args) {

        HelloService helloService = OneRpcClient.getService(HelloService.class);

        IntStream.range(0, 100).forEach(i -> {
            String name = "mark-" + i;
            String result = helloService.sayHello(name);
            System.out.println(result);
        });

    }
}
