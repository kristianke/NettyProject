package com.hcns.training.nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i=0;i<intBuffer.capacity();i++){
            System.out.println("**"+intBuffer.limit());
            intBuffer.put(i*i);
        }

        intBuffer.flip();
        System.out.println("***"+intBuffer.limit());
        intBuffer.position(2);
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }

}
