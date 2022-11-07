package com.example.springbootmid.netty.client;

import com.example.springbootmid.netty.handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.TimeUnit;

/**
 * @author :Panking
 * @date : 2022/10/28
 */
public class NettyClient {
    private SocketChannel socketChannel;

    public void run(Object msg) {
        //配置线程组
        EventLoopGroup group = new NioEventLoopGroup();
        //创建服务启动器
        Bootstrap bootstrap = new Bootstrap();

        //配置参数
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new StringEncoder())
                                .addLast(new StringDecoder())
                                .addLast(new NettyClientHandler());
                    }
                })
                .remoteAddress("127.0.0.1", 1000);

        //连接
        ChannelFuture future = bootstrap.connect();
        System.out.println("客户端正在连接服务端...");
        //客户端断线重连逻辑
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                System.out.println("连接Netty服务端成功");
                future.channel().writeAndFlush(msg);
            } else {
                System.out.println("连接失败，进行断线重连");
                future1.channel().eventLoop().schedule(() -> run(msg), 20, TimeUnit.SECONDS);
            }
        });
        socketChannel = (SocketChannel) future.channel();

    }

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        nettyClient.run("hello world");
    }
}
