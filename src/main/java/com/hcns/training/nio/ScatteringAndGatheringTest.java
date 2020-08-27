package com.hcns.training.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.stream.Stream;

public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(7);

        SocketChannel acceptChannel = serverSocketChannel.accept();
        int messageLength = 12;
        while (true){
            int byteRead = 0;

            while (byteRead < messageLength){
                long read = acceptChannel.read(byteBuffers);
                byteRead += read;
                System.out.println("byteRead : " + byteRead + "\t ");
                Arrays.stream(byteBuffers).map((buffer) -> buffer.position() +" ,limit: " + buffer.limit()).forEach(System.out::println);
                Arrays.stream(byteBuffers).map((buffer) -> "Content: " + new String(buffer.array())).forEach(System.out::println);
            }

            Arrays.stream(byteBuffers).forEach(Buffer::flip);

            long byteWrite = 0;
            while (byteWrite < messageLength){
                long write = acceptChannel.write(byteBuffers);
                byteWrite += write;
            }

            Stream.of(byteBuffers).forEach(Buffer::clear);

            System.out.println("byteRead: " + byteRead + "\t byteWrite: " + byteWrite + "\t messageLength : " + messageLength);
        }
    }
}
