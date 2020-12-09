package com.onemsg.onerpc.example;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import com.onemsg.onerpc.client.OneRpcClient;

public class RpcClient {

    public static void main(String[] args) {

        HelloService helloService = OneRpcClient.getService(HelloService.class);
        
        // Stream.of("Neil", "Kat", "Sator", "Nolan", "Priya")
        //     .parallel()
        //     .forEach( name -> {
        //         IntStream.range(0, 10)
        //             .forEach(i -> {
        //                 String result = helloService.sayHello(name);
        //                 System.out.println(result);
        //             });
        //     });

        var random = ThreadLocalRandom.current();
        int[] nums = random.ints(100, 1, 1000000).toArray();
        int k = random.nextInt(1, 50);

        int[] result = helloService.findTopK(nums, k);
        System.out.println(Arrays.toString(result));
    }
}
