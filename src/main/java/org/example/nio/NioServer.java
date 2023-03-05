package org.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer extends Thread {

    ServerSocketChannel serverSocketChannel;
    Selector selector;
    SelectionKey selectionKey;

    private void initServer() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        //设置为非阻塞模式,默认serverSocketChannel是采用了阻塞模式
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8088));
        selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        while (true) {
            try {
                int selectKey = selector.select();
                if (selectKey > 0) {
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keySet.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            System.out.println("conn is acceptable");
                            socketChannel.configureBlocking(false);
                            //将当前的channel交给selector对象监管，并且有selector对象管理它的读事件
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                            int len = channel.read(byteBuffer);
                            if (len > 0) {
                                byteBuffer.flip();
                                byte[] byteArray = new byte[byteBuffer.limit()];
                                byteBuffer.get(byteArray);
                                System.out.println("NioSocketServer receive from client:" + new String(byteArray,0,len));
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        } else {

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    serverSocketChannel.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    public static void main(String args[]) throws IOException {
        NioServer server = new NioServer();
        server.initServer();
        server.start();
    }
}
