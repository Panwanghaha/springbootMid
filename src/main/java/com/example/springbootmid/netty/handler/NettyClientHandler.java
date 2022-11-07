package com.example.springbootmid.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * @author :Panking
 * @date : 2022/10/28
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(String.valueOf(NettyClientHandler.class));
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static byte[] responseByte;

    /**
     * 管道就绪时触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("云上建立连接时：" + sdf.format(new Date()));
        //ctx.writeAndFlush(Unpooled.copiedBuffer("Hello World,I am Client.", CharsetUtil.UTF_8));
    }

    /**
     * 管道读取事件时会触发该方法,
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端收到服务端返回的消息：" + msg);
        byte[] bytes = objectToBytes((Serializable) msg);
        //byte []scaleMsg=(byte[]) msg;
        StringBuffer originalData = new StringBuffer();
        for (byte b : bytes) {
            originalData.append(b + " ");
        }
        System.out.println(originalData.toString());
        if (bytes != null) {
            responseByte = bytes;
        }


    }

    /**
     * 发生异常时触发
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

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
