package com.hcns.training.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class BIOServer {

    public static void main(String[] args) {

        ExecutorService threadPool = new ThreadPoolExecutor(1,10,30, TimeUnit.SECONDS,
                new SynchronousQueue<>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        try {
            ServerSocket serverSocket = new ServerSocket(6666);

            while (true){
                System.out.println("Waitting for connect....");
                Socket socket = serverSocket.accept();
                System.out.println("**ThreadName:" + Thread.currentThread().getName()+",id:\t"+Thread.currentThread().getId());

                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("***T:" + Thread.currentThread().getName()+",\t"+Thread.currentThread().getId());
                        handler(socket);
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void handler(Socket socket){
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();

            while (true){
                System.out.println("##Waitting for read...#**T:" + Thread.currentThread().getName()+",\t"+Thread.currentThread().getId());

                int read = inputStream.read(bytes);
                if (read != -1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
