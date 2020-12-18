package com.onemsg.onerpc.registry.core;

import io.etcd.jetcd.Watch.Listener;

public interface RegistryCenter {
    
    boolean register(String name, Address address);

    Address discover(String name);

    boolean unRegister(String name, Address address);

    void watch(String name, Listener listener);

    void close();

}
