package com.onemsg.onerpc.example;

/**
 * HelloServiceImple
 */
public class HelloServiceImple implements HelloService{

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
