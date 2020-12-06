package com.onemsg.onerpc.server;

import com.onemsg.onerpc.registry.OneRpcRegistry;
import com.onemsg.onerpc.registry.core.Address;
import com.onemsg.onerpc.registry.core.RegistryCenter;
import com.onemsg.onerpc.server.core.HttpServerVerticle;
import com.onemsg.onerpc.server.core.ServiceCenter;
import com.onemsg.onerpc.server.registry.RegistryUtil;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class OneRpcServer {

    private static final ServiceCenter CENTER = ServiceCenter.getInstance();
    private static final RegistryUtil REGISTRY_UTIL;

    static {
        String host = "localhost";
        int port = 18080;
        RegistryCenter registryCenter = OneRpcRegistry.registryCenter();
        REGISTRY_UTIL = new RegistryUtil(registryCenter, Address.of(host, port));

        JsonObject config = new JsonObject()
            .put("host", host)
            .put("port", port);

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(HttpServerVerticle.class, 
            new DeploymentOptions().setInstances(3).setConfig(config) );
    }

    public static <T> void publish(T service, Class<T> clazz){
        CENTER.putProvider(clazz, service);
        REGISTRY_UTIL.register(clazz);
    }

    public static <T> void unpublish(Class<T> clazz){
        CENTER.removeProvider(clazz);
    }

    public static ServiceCenter getServiceCenter(){
        return CENTER;
    }
}
