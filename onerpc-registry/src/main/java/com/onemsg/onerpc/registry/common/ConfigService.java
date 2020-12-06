package com.onemsg.onerpc.registry.common;

import java.io.IOException;
import java.util.Properties;

public class ConfigService {

    public static final String ETCD_PROPERTIES_FILE = "etcd.properties";
    public static final String ETCD_ENDPOINTS_KEY = "etcd.endpoints";
    private static final Properties etcdProperties;

    static {
        etcdProperties = new Properties();
        try {
            etcdProperties.load(ConfigService.class.getClassLoader().getResourceAsStream(ETCD_PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static Properties getEtcdProperties(){
        return etcdProperties;
    }

    public static String[] endPoints(){
        String value = etcdProperties.getProperty(ETCD_ENDPOINTS_KEY);
        return value.split(",");
    }

}
