package com.myLab.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 连接到服务器
 * 发送一个或者多个消息
 * 对于每个消息，等待并接收从服务器发回的相同的消息
 * 关闭连接
 */
public class EchoClient {
    private final String host;
    private final int port;


    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();//连接到远程节点，阻塞当前线程直到连接完成
            future.channel().closeFuture().sync();//阻塞，直到channel关闭
        } finally {
            group.shutdownGracefully().sync();//阻塞，关闭线程池并且释放所有的资源
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        if(args.length != 2){
//            System.err.println("Usage: " + EchoClient.class.getSimpleName() + " <host> <port>");
//            return;
//        }
//        String host = args[0];
//        int port = Integer.parseInt(args[1]);
        new EchoClient("localhost", 8999).start();
    }
}
