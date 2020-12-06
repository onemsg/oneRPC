package com.onemsg.onerpc.registry.core;

import java.util.Objects;

public final class Address {

    private String host;
    private int port;

    private Address() {
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public static Address of(String socket) {
        String[] host_port = socket.split(":");
        return of(host_port[0], Integer.parseInt(host_port[1]));
    }

    public static Address of(String host, int port) {
        Address a = new Address();
        a.host = host;
        a.port = port;
        return a;
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
