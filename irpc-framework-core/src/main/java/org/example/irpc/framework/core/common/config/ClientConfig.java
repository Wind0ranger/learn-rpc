package org.example.irpc.framework.core.common.config;

/**
 * 客户端配置
 * 端口号 服务地址
 */
public class ClientConfig {

    private Integer port;

    private String serverAddr;

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
