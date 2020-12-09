package com.onemsg.onerpc.client.Core;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

import com.onemsg.onerpc.registry.core.Address;
import com.onemsg.onerpc.registry.core.RegistryCenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RegistryUtil {
    
    private RegistryCenter registryCenter;
    private final ConcurrentHashMap<String, URI> map = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger("ONERPC-CLIENT");

    public RegistryUtil(RegistryCenter registryCenter){
        this.registryCenter = registryCenter;
    }

    public URI lookup(String name){
        return lookup(name, false);
    }

    public URI lookup(String name, boolean poll){
        if(poll) return map.put(name, poll(name));
        if(map.containsKey(name)) return map.get(name);
        URI uri = poll(name);
        map.put(name, uri);
        return uri;
    }

    private URI poll(String name){
        Address address = registryCenter.discover(name);
        logger.info("服务发现 [{}: {}]", name, address);
        return URI.create("http://" + address.toString() + "/");
    }

}
