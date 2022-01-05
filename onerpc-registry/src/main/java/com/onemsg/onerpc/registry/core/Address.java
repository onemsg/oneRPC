package com.onemsg.onerpc.registry.core;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

/**
 * A node address with host and port
 * 
 * @author mashuguang
 * @since 2022-1
 */
@Immutable
public final class Address {

    private final String host;
    private final int port;

    private Address(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }

    /**
     * Create a address with format "host:port" 
     * @param socket host:port
     * @return a address
     */
    public static Address of(String socket) {
        Objects.requireNonNull(socket);
        String[] hostPort = socket.split(":");
        return of(hostPort[0], Integer.parseInt(hostPort[1]));
    }

    /**
     * Create a address with host and port
     * @param host host
     * @param port port
     * @return
     */
    public static Address of(String host, int port) {
        Objects.requireNonNull(host);
        return new Address(host, port);
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        return Objects.equals(host, other.host) && port == other.port;
    }

}
