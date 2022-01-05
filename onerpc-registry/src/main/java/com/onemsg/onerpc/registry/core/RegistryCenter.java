package com.onemsg.onerpc.registry.core;

import io.etcd.jetcd.Watch.Listener;

/**
 * Registry center and service discover
 * 
 * @author mashuguang
 * @since 2022-1
 */
public interface RegistryCenter {
    
    /**
     * Register a new service node
     * @param name service name
     * @param address service node address
     * @return if register succeed return true else false
     */
    boolean register(String name, Address address);

    /**
     * Unregister a service node
     * @param name service name
     * @param address service node address
     * @return if unregister succeed return true else false
     */
    boolean unRegister(String name, Address address);

    /**
     * Find a node address for specific service
     * @param name service name
     * @return a node address or null if not find
     */
    Address discover(String name);

    /**
     * Add a listener to watch a service
     * @param name service name
     * @param listener a etcd listener
     */
    void watch(String name, Listener listener);

    /**
     * Close this registryCenter
     */
    void close();

}
