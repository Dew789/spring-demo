package com.example.demo.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
public class SocketServerAIO {

    public static void main(String[] args) {
        try {
            AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8080));
            CompletionHandler<AsynchronousSocketChannel, Object> handler = new CompletionHandler<AsynchronousSocketChannel,
                    Object>() {
                @Override
                public void completed(final AsynchronousSocketChannel result, final Object attachment) {
                    // 继续监听下一个连接请求
                    serverSocketChannel.accept(attachment, this);
                    try {
                        System.out.println("2. SERVER GOT CONNECTION FROM CLIENT : client - " + result.getRemoteAddress().toString());
                        ByteBuffer readBuffer = ByteBuffer.allocate(128);
                        result.read(readBuffer).get();
                        System.out.println(new String(readBuffer.array()));

                        result.write(ByteBuffer.wrap("bye".getBytes())).get();

                        result.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(final Throwable exc, final Object attachment) {
                    System.out.println(exc.getMessage());
                }
            };

            System.out.println("0. SERVER STARTED TO LISTEN");
            serverSocketChannel.accept(null, handler);

            while (true) {
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
