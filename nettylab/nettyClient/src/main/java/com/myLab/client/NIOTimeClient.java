package com.myLab.client;

import io.netty.buffer.ByteBuf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOTimeClient implements Runnable {
    private String host;
    private int port;
    private SocketChannel socketChannel;
    private Selector selector;
    private volatile boolean stop;

    public NIOTimeClient(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    @Override
    public void run() {
        //尝试连接服务端
        try {
            doConnection();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        //无限循环使用多路复用器轮询事件，并处理事件
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    handleInput(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //关闭多路复用器，所有注册在上面的channel和pipe等资源都将去掉注册并关闭
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
            SocketChannel sc = (SocketChannel) key.channel();
            //处理连接事件
            if (key.isConnectable()) {
                if (sc.finishConnect()) {//判断是否连接成功
                    sc.register(selector, SelectionKey.OP_READ);
//                    doWrite(sc);
                    //连接成功之后另启一个线程，专门负责读取控制台输入，然后将其发送到服务端
                    new Thread(() ->{
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        String cmd;
                        while (true) {
                            try {
                                if ((cmd = br.readLine()) != null) {
                                    byte[] cmdBytes = cmd.getBytes();
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(cmdBytes.length);
                                    writeBuffer.put(cmdBytes);
                                    writeBuffer.flip();
                                    sc.write(writeBuffer);
                                    if (!writeBuffer.hasRemaining()) {
                                        System.out.println("Send order 2 server succeed.");
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();

                } else {
                    System.out.println("连接失败");
                    System.exit(1);//连接失败，进程退出
                }
            }
            //处理可读事件
            if (key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("Now is: " + body);
//                    this.stop = true;
                } else if (readBytes < 0) {
                    //对端链路关闭
                    key.cancel();
                    sc.close();
                } else {
                    ; //读到0子节，忽略
                }
            }
        }
    }

    private void doConnection() throws IOException {
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            System.out.println("连接成功");
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            System.out.println("注册OP_CONNECT");
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        socketChannel.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println("Send order 2 server succeed.");
        }
    }

    public static void main(String[] args) {
        int port = 9001;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        new Thread(new NIOTimeClient("127.0.0.1", port), "Thread-NIOTimeClient").start();
    }
}
