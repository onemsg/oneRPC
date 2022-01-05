package com.onemsg.onerpc.registry.core;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Watch.Listener;
import io.etcd.jetcd.options.GetOption;

/**
 * A RegistryCenter implement with Etcd
 * 
 * @author mashuguang
 * @since 2022-1
 */
public class EtcdRegistryCenter implements RegistryCenter {

    private static final Logger log = LoggerFactory.getLogger(EtcdRegistryCenter.class);

    private static final GetOption GET_OPTION = GetOption.newBuilder().withLimit(1).build();

    private final Client client;

    private final KV kvClient;

    public EtcdRegistryCenter(String... endpoints){
        Objects.requireNonNull(endpoints);
        client = Client.builder().endpoints(endpoints).build();
        kvClient = client.getKVClient();
    }

    @Override
    public boolean register(String name, Address address) {
        try {
            kvClient.put(encode(name), encode(address.toString())).join();
            return true;
        } catch (Exception e) {
            log.warn("register a service node failed, {} {}", name, address, e);
        }
        return false;
    }

    @Override
    public Address discover(String name) {
        List<KeyValue> list;
        try {
            list = kvClient.get(encode(name), GET_OPTION).join().getKvs();
        } catch (Exception e) {
            log.warn("discover a service address failed, {}", name, e);
            return null;
        }
        if (list.isEmpty() || list.get(0).getValue().isEmpty()){
            log.warn("dont discover service address, {}", name);
            return null;
        }
        return Address.of(decode(list.get(0).getValue()));
    }

    @Override
    public boolean unRegister(String name, Address address) {
        Address address2 = discover(name);
        if ( Objects.equals(address, address2) ) {
            try {
                kvClient.delete(encode(name)).join();
                return true;
            } catch (Exception e) {
                log.warn("unregister service node failed, {} {}", name, address, e);
            }
        }
        return false;
    }
    
    @Override
    public void watch(String name, Listener listener){
        client.getWatchClient().watch(encode(name), listener);
        log.info("etcd is watching {}", name);
    }

    @Override
    public void close() {
        kvClient.close();
        client.close();
        log.info("EtcdRegistryCenter is closed");
    }

    private static ByteSequence encode(String value) {
        return ByteSequence.from(value, StandardCharsets.UTF_8);
    }

    private static String decode(ByteSequence byteSequence) {
        return byteSequence.toString(StandardCharsets.UTF_8);
    }
}
