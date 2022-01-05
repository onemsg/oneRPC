package com.onemsg.onerpc.registry;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A etcd config service
 * 
 * @author onemsg
 * @since 2022-1
 */
public class ConfigService {

    private static final Logger log = LoggerFactory.getLogger(ConfigService.class);

    public static final String DEFAULT_ETCD_PROPERTIES_FILE = "etcd.properties";
    
    public static final String ETCD_ENDPOINTS_KEY = "endpoints";

    private ConfigService() {}

    /**
     * Load etcd properties from classpath:/etcd.properties file
     * @return
     */
    public static EtcdProperties loadEtcdProperties(){
        Properties properties = new Properties();
        try {
            properties.load(ConfigService.class.getResourceAsStream(DEFAULT_ETCD_PROPERTIES_FILE));
            String value = properties.getProperty(ETCD_ENDPOINTS_KEY);

            Objects.requireNonNull(value, "key [" + ETCD_ENDPOINTS_KEY + "] must not be null");

            EtcdProperties etcdProperties = new EtcdProperties();
            etcdProperties.endpoints = value.split(",");

            return etcdProperties;
        } catch (IOException e) {
            log.warn("load {} failed", DEFAULT_ETCD_PROPERTIES_FILE, e);
        }
        return null;
    }

}
