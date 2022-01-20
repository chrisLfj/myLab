package com.myLab.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NIOTimeServer implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public NIOTimeServer(int port) {
        try {
            selector = Selector.open();//打开多路复用器实例
            //1.创建ServerSocketChannel 2.设置为非阻塞模式 3.绑定端口，设置最大连接数 3.将该channel注册到selector中并且与网络连接请求事件accept进行绑定
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //提供外界停止的方法
    public void stop() {
        this.stop = true;
    }
    @Override
    public void run() {
        //无限循环中持续使用多路复用器来轮询socket事件，stop==true则跳出循环
        while (!stop) {
            try {
                //多路复用器间隔1s轮询一次，select方法调用过程是阻塞的
                selector.select(1000);
                Set<SelectionKey> keys = selector.keys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                //处理轮询出来的所有事件
                while (it.hasNext()) {
                    key = it.next();
//                    it.remove();
                    handleInput(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //多路复用器关闭时，所有注册在上面的Channel和Pipe等资源都会被自动去掉注册并关闭
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //处理链接事件
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = ssc.accept();//三次握手建立连接，返回socketchannel
                socketChannel.configureBlocking(false);//将socketchannel设置为feizus
                socketChannel.register(selector, SelectionKey.OP_READ);//将socketchannel注册到selector中监听read事件
            }
            //处理可读事件
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order: " + body);
                    String currentTime = "QUERY TIME ORDER".equals(body) ? new Date().toString() : "BAD ORDER";
                    doWrite(socketChannel, currentTime);//返回响应信息给对端
                } else if (readBytes < 0) {
                    key.cancel();
                    socketChannel.close();
                }else
                    ;
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }
}
