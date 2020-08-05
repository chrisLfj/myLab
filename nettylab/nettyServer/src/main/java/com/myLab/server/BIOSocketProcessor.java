package com.myLab.server;

import java.io.*;
import java.net.Socket;

public class BIOSocketProcessor implements Runnable{
    private Socket socket = null;

    public BIOSocketProcessor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("one processor started: " + Thread.currentThread().getName());
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            //在网络通信中要时刻注意数据的封包和拆包，本质上就需要有一个数据边界，比如一次读一行数据，那换行符就是数据边界，写数据时可以设置成自动flush
            //或者在header中加入一些类似ID，数据包长度等的标识，也是可以方便封包和拆包
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);
            //处理socket的processor线程可以从外部中断，只要还没有被中断它就会一直等待读取从socket对端传来的信息
            //这里加入一个外部中断跳出的逻辑，意味着只要从外部针对这些线程发起中断指令，就可以让这些socket关闭，可以更加灵活的来管理这些socket
            while(!Thread.interrupted()){
                String input = br.readLine();
                System.out.println(String.format("%s say %s", socket.getRemoteSocketAddress(), input));
                pw.println(input);
                if("bye".equals(input))
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(br);
            StreamUtil.close(pw);
            StreamUtil.close(socket);
        }
        System.out.println("server processor " + Thread.currentThread().getName() + " say 'bye'");
    }
}
