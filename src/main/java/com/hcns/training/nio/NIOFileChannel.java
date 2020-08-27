package com.hcns.training.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {
    public static void main(String[] args) throws IOException {
        File file = new File("1.txt");
        System.out.println(file.getCanonicalPath()+file);
        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel channel01 = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel channel02 = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(128);
        while (true){
            //buffer复位，别忘记！！
            buffer.clear();
            int read = channel01.read(buffer);
            System.out.println("read length:" + read);
            if (read != -1){
                buffer.flip();
                channel02.write(buffer);
            }else {
                break;
            }
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
