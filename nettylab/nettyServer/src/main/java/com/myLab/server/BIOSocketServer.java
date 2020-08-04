package com.myLab.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO编程模型下的SocketServer类，作用
 * 1.负责启动一个ServerSocket实例来监听9000端口
 * 2.自身作为一个线程来运行，不阻塞主线程
 * 3.当接收到一个客户端连接时，就创建一个socket实例，交给一个新的线程去处理
 */
public class BIOSocketServer implements Runnable{

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private ServerSocket serverSocket = null;

    public void start() throws IOException {
        serverSocket = new ServerSocket(9000);//创建一个ServerSocket，监听9000端口
        threadPool.execute(this);//另起一个线程来接收客户端连接请求，不阻塞主线程
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            //accept方法也是一个阻塞方法，直到获取到一个客户端连接时才会继续往下运行
            //下面这段程序使用了while循环，一开始程序阻塞在accept方法这里，当接收到一个客户端请求时返回一个socket，然后进入循环内的逻辑。之后再回到accept方法这里阻塞住，等待下一个连接到来
            //这样一直循环着等待接收连接
            while((socket = serverSocket.accept()) != null){
                System.out.println("client connected, ip:" + socket.getRemoteSocketAddress());
                threadPool.execute(new Processor(socket));
                Thread.sleep(10);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        //关闭ServerSocket和线程池
        if(serverSocket != null && !serverSocket.isClosed()){
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
    }

    class Processor implements Runnable{

        private Socket socket = null;

        public Processor(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader br = null;
            PrintWriter pw = null;
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw = new PrintWriter(socket.getOutputStream(), true);
                //处理socket的processor线程可以被从外部在中断，只要还没有被中断它就会一直等待读取从socket对端传来的信息
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
        }
    }

    public static void main(String[] args) {
        BIOSocketServer socketServer = new BIOSocketServer();
        BufferedReader br = null;
        try {
            socketServer.start();

            System.out.println("Enter 'exit' to exit");
            br = new BufferedReader(new InputStreamReader(System.in));
            String cmd = null;
            //readLine方法是阻塞方法，程序运行到这里的时候会阻塞当前线程，不会往下运行，直到读到一行数据(以换行符为边界确定一行数据)才会继续往下执行
            //readLine方法只会一次读取一行数据，不会自动继续读取，因此这里使用了while循环，可以让程序读完一行之后再回来读取下一行输入，直到读到“exit”才推出循环
            while((cmd = br.readLine()) != null){
                if("exit".equalsIgnoreCase(cmd)){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(br);
            socketServer.close();
        }
    }
}
