package com.onemsg.onerpc.client.Core;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

import com.onemsg.onerpc.registry.core.Address;
import com.onemsg.onerpc.registry.core.RegistryCenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Watch.Listener;
import io.etcd.jetcd.watch.WatchResponse;
import io.etcd.jetcd.watch.WatchEvent.EventType;

public class RegistryUtil {

    private RegistryCenter registryCenter;
    private final ConcurrentHashMap<String, URI> map = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger("ONERPC-CLIENT");

    public RegistryUtil(RegistryCenter registryCenter) {
        this.registryCenter = registryCenter;
    }

    public URI lookup(String name) {
        return lookup(name, false);
    }

    public URI lookup(String name, boolean poll) {
        if (poll)
            return map.put(name, poll(name));
        if (map.containsKey(name))
            return map.get(name);
        URI uri = poll(name);
        map.put(name, uri);
        return uri;
    }

    private URI poll(String name) {
        Address address = registryCenter.discover(name);
        logger.info("服务发现 [{}: {}]", name, address);
        return createURI(address);
    }

    public void watch(String name) {
        Listener listener = new EtcdListener(name);
        registryCenter.watch(name, listener);
    }

    private URI createURI(Address address){
        return URI.create("http://" + address.toString() + "/");
    }

    private URI createURI(String socket){
        return URI.create("http://" + socket + "/");
    }

    private class EtcdListener implements Listener {

        private String kName;

        EtcdListener(String kName){
            this.kName = kName;
        }

        @Override
        public void onNext(WatchResponse response) {

            // final String kName = null;
            response.getEvents().forEach( event -> {
                if(event.getEventType().equals(EventType.PUT)){
                    KeyValue kv = event.getKeyValue();
                    if(kv.getKey().toString(StandardCharsets.UTF_8).equals(kName)){
                        URI uri = createURI(kv.getValue().toString(StandardCharsets.UTF_8));
                        map.put(kName, uri);
                    }
                }else if(event.getEventType().equals(EventType.DELETE)){
                    KeyValue kv = event.getKeyValue();
                    if(kv.getKey().toString(StandardCharsets.UTF_8).equals(kName)){
                        map.remove(kName);
                    }
                }
            });
        }

        @Override
        public void onError(Throwable throwable) {
            logger.warn("服务发现 watch 事件出现错误", throwable);
        }

        @Override
        public void onCompleted() {
            logger.info("服务发现 watch 事件完成");
        }

    }

}
