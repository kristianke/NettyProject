package com.hcns.training.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(7000));

        Selector selector = Selector.open();
        SelectionKey sKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            System.out.println("..loop..");
            int select = selector.select(2000);
            if (select == 0){
                System.out.println("Wait for a second");
                continue;
            }else{

            }
            System.out.println("select:::::" + select);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("count of selection keys with event: " + selectionKeys.size());
            System.out.println("count of selection keys: " + selector.keys().size());

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                if (key.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(128));
                    System.out.println("After a client connected, the count of selection keys is:" + selector.keys().size());
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
                    System.out.println(read + "Msg from client:" + new String(buffer.array(),0,read));
                    buffer.clear();
                }

                iterator.remove();

            }
        }
    }
}
