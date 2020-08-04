package com.myLab.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOSocketClient {
    public static void main(String[] args) {
        BufferedReader ir = null;
        BufferedReader sr = null;
        String cmd = null;
        PrintWriter pw = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 9000);
            ir = new BufferedReader(new InputStreamReader(System.in));//从控制台读取输入的流
            sr = new BufferedReader(new InputStreamReader(socket.getInputStream()));//从socket读取数据的流
            pw = new PrintWriter(socket.getOutputStream());//向socket写入数据的流
            System.out.println("Say 'bye' to exit");
            //循环堵塞读取控制台输入
            while((cmd = ir.readLine()) != null){
                pw.println(cmd);//向socket写入信息，阻塞当前线程，直到传输完成
                String response = sr.readLine();//从socket读取信息，阻塞当前线程，直到传输完成
                System.out.println(String.format("Server say %s", response));
                if("bye".equalsIgnoreCase(response))
                    break;//如果读到了bye，则跳出循环
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(ir);
            StreamUtil.close(sr);
            StreamUtil.close(pw);
            StreamUtil.close(socket);
        }
        System.out.println("bye");
    }
}
