package com.onemsg.onerpc.registry.core;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.options.GetOption;

public class EtcdRegistryCenter implements RegistryCenter {

    private static final Logger logger = LoggerFactory.getLogger(EtcdRegistryCenter.class);
    private static final GetOption GET_OPTION;

    static {
        GET_OPTION = GetOption.newBuilder().withLimit(1).build();
    }

    private Client client;
    private KV kvClient;

    public EtcdRegistryCenter(String... endpoints){
        client = Client.builder().endpoints(endpoints).build();
        kvClient = client.getKVClient();
    }

    @Override
    public boolean register(String name, Address address) {
        try {
            kvClient.put(encode(name), encode(address.toString())).get(5L, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("ETCD put 操作失败", e);
            return false;
        }
        return true;
    }

    @Override
    public Address discover(String name) {
        List<KeyValue> list;
        try {
            list = kvClient.get(encode(name), GET_OPTION).get(5, TimeUnit.SECONDS).getKvs();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("ETCD get 操作失败", e);
            return null;
        }
        if (list.isEmpty() || list.get(0).getValue().isEmpty()){
            logger.warn("ETCD get key={} 为空", name);
            return null;
        }
        return Address.of(decode(list.get(0).getValue()));
    }

    @Override
    public boolean unRegister(String name, Address address) {
        Address a = discover(name);
        if (address.equals(a)) {
            try {
                kvClient.delete(encode(name)).get(5, TimeUnit.SECONDS);
                return true;
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                logger.error("ETCD delete 操作失败", e);
            }
        }
        return false;
    }
    
    private ByteSequence encode(String value) {
        return ByteSequence.from(value, StandardCharsets.UTF_8);
    }

    private String decode(ByteSequence byteSequence) {
        return byteSequence.toString(StandardCharsets.UTF_8);
    }
}
