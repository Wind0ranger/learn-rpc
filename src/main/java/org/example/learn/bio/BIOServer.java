package org.example.learn.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

    private static ExecutorService executors = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8088));
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("获取新链接");
            executors.execute(() -> {
                dealSocket(socket);
            });
        }
    }

    private static void dealSocket(Socket socket) {
        while (true) {
            try {
                InputStream inputStream = socket.getInputStream();
                byte[] data = new byte[1024];
                int len = inputStream.read(data);
                if (len != 1) {
                    System.out.println("data:" + new String(data, 0, len));
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("response data".getBytes());
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
