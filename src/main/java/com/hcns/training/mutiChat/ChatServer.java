package com.hcns.training.mutiChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private final int PORT = 7000;

    public ChatServer() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));

        selector = Selector.open();
        SelectionKey sKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void forwardMsg(String msg,SocketChannel channel) throws IOException {
        for (SelectionKey key : selector.keys()) {
            SelectableChannel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel){
                if (targetChannel == channel){
                    continue;
                }
                SocketChannel socketChannel = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                socketChannel.write(buffer);
            }else {
                System.out.println("channel type:" + targetChannel.getClass().getCanonicalName());
            }
        }
    }

    public void gatherMsg() throws IOException {
        while (true) {
            int select = selector.select(2000);
            if (select == 0) {
//                System.out.println("Wait for a second");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                if (key.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println(socketChannel.getRemoteAddress() + "已上线");

                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(128));

                    System.out.println("After a client connected, the count of selection keys is:" + selector.keys().size()
                    + "\t channel hashcode:" + socketChannel.hashCode());
                }

                if (key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    int read = channel.read(buffer);
                    if (read == -1){
                        channel.close();
                        continue;
                    }
//                    buffer.flip();
                    String msg = new String(buffer.array(), 0, read);
                    System.out.println(read + "Msg from "+ channel.getRemoteAddress()+": " + msg);
                    forwardMsg(msg,channel);
                    buffer.clear();
                }

                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ChatServer chatServer = new ChatServer();
        chatServer.gatherMsg();
    }
}
