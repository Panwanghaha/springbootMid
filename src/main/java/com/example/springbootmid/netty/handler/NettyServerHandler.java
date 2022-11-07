package com.example.springbootmid.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author :Panking
 * @date : 2022/10/27
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger(String.valueOf(NettyServerHandler.class));

    /**
     * 读取客户端的数据，也可以在这个方法中给ctx赋值，也就是给客户端返回的应答
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf in = (ByteBuf)msg;
//        int readableBytes = in.readableBytes();
//        byte[] bytes =new byte[readableBytes];
//        in.readBytes(bytes);
//        System.out.println("服务端接收的倒的消息："+new String(bytes));
//        System.out.println("远程连接到的地址为："+ctx.channel().remoteAddress());
        //System.out.print(in.toString(CharsetUtil.UTF_8));
        System.out.println("服务端接收到一条来自客户端的消息：" + msg);
        ctx.write("我是服务端，我收到客户端的消息了，我现在给你返回");
        // logger.error("服务端接受的消息 : " + msg);
    }

    /**
     * 发生异常时关闭
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 读取数据完毕,就刷新ctx对象，把应答的信息推送给客户端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        //也可以使用下面的方法，一次性将应答信息写入并且推送出去，但是一般不这么用，一般是在read方法中返回应答信息，处理复杂的业务逻辑，在complete方法中推送
        //ctx.writeAndFlush(Unpooled.copiedBuffer("Hello World,I am Server.", CharsetUtil.UTF_8));
    }

    /**
     * 将object类型转为字节数组的方法
     *
     * @param object
     * @return
     * @throws IOException
     */
    public static byte[] objectToBytes(final Serializable object) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
            return baos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
    }
}
