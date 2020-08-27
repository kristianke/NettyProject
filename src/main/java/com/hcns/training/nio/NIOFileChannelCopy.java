package com.hcns.training.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class NIOFileChannelCopy {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("2.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("2copy.txt");

        FileChannel channel = fileInputStream.getChannel();
        FileChannel channel1 = fileOutputStream.getChannel();

        channel1.transferFrom(channel,0,channel.size());

        channel.close();
        channel1.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
