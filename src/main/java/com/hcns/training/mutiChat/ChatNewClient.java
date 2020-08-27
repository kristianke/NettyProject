package com.hcns.training.mutiChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ChatNewClient {
    private Selector selector;
    private SocketChannel socketChannel;
    private final String HOST = "127.0.0.1";
    private final int PORT = 7000;
    private String username;

    public ChatNewClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is coming...");
    }

    public void reveiveMsg(){
        try {
//            System.out.println("[select() method] block waiting....");
            int select = selector.select(9000);
            if (select > 0){

                final SelectionKey[] key = {null};
                selector.selectedKeys().stream().forEach(selectionKey -> {
                    if (selectionKey.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(128);
                        try {
                            int read = socketChannel.read(buffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(new String(buffer.array()));
                        //需要记录之后移除此条
                        key[0] = selectionKey;
                    }
                });
                selector.selectedKeys().remove(key[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        msg = username + ": " + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private volatile Boolean hold = true;

    public static void main(String[] args) throws IOException {
        ChatNewClient chatNewClient = new ChatNewClient();
        Thread thread = new Thread(()->{
            while (chatNewClient.hold){
                chatNewClient.reveiveMsg();
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            System.out.println("退出循环");
        });
        thread.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if ("quit".equals(s)){
                chatNewClient.hold = false;
                break;
            }
            chatNewClient.sendMsg(s);

        }

    }
}
