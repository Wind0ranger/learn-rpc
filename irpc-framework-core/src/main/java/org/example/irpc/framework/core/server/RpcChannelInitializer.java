package org.example.irpc.framework.core.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.example.irpc.framework.core.common.RpcDecoder;
import org.example.irpc.framework.core.common.RpcEncoder;

public class RpcChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.out.println("初始化provider过程");
        ch.pipeline().addLast(new RpcEncoder());
        ch.pipeline().addLast(new RpcDecoder());
        ch.pipeline().addLast(new ServerHandler());
    }
}
