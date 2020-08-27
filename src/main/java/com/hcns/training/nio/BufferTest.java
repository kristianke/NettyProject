package com.hcns.training.nio;

import java.nio.ByteBuffer;

public class BufferTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putDouble(3.23);
        buffer.putInt(23);
        buffer.putInt(99);
        buffer.putChar('a');
        buffer.putChar('C');
        buffer.putLong(3L);

        buffer.flip();
        System.out.println(buffer.getLong());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getChar());

    }
}
