package com.onemsg.onerpc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统一日志提供者
 */
public class Loggers {
    
    private static final Logger SERVER_LOGGER = LoggerFactory.getLogger("Server-Logger");
    private static final Logger CLIENT_LOGGER = LoggerFactory.getLogger("Client-Logger");


    public static Logger getServerLogger(){ return SERVER_LOGGER; }

    public static Logger getClientLogger(){ return CLIENT_LOGGER; }
}
