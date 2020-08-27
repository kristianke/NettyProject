package com.hcns.training.mutiChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ChatClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",7000);
        boolean connect = socketChannel.connect(inetSocketAddress);
        while (!socketChannel.finishConnect()){
            System.out.println("客户端非阻塞重连...");
        }
        System.out.println("I am " + socketChannel.getLocalAddress());
        while(true) {
            byte[] input = new byte[10];
            System.out.println("请输入：");
            int read = System.in.read(input);
            if ((new String(input)).startsWith("quit")){
                break;
            }
            ByteBuffer buffer = ByteBuffer.wrap(input,0,read);
            socketChannel.write(buffer);
        }
    }
}
