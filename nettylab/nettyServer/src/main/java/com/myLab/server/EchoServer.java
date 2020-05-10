package com.myLab.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 绑定需要监听的端口并接受连接请求
 * 配置Channel，以将有关的入站消息通知给EchoServerHandler实例
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
//        if(args.length != 1){
//            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
//            return;
//        }
//        int port = Integer.parseInt(args[0]);
        new EchoServer(8999).start();
    }

    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();//创建EventLoopGroup
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)//指定所使用的的NIO传输Channel
                    .localAddress(new InetSocketAddress(port))//使用指定端口设置套接字地址
                    .childHandler(new ChannelInitializer<SocketChannel>() {//添加一个EchoServerHandler到子Channel的ChannelPipeline
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);//EchoServerHandler被标注为@shareable，所以对于连接到该server的客户端，我们可以总是使用同样的实例。
                        }
                    });
            ChannelFuture future = b.bind().sync();//同步绑定服务器，调用sync()方法阻塞当前线程等待直到绑定完成
            future.channel().closeFuture().sync();//获取Channel的CloseFuture，并且阻塞当前线程直到完成
        } finally {
            group.shutdownGracefully().sync();//关闭EventLoopGroup释放所有资源，包括所有被创建的线程
        }
    }
}
