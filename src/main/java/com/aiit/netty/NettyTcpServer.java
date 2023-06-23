package com.aiit.netty;


import com.aiit.controller.MessageController;
import com.aiit.netty.protocol.MessageDecoder;
import com.aiit.netty.protocol.MessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyTcpServer {

    @Autowired
    private MessageController messageController;

    public void startServer() throws Exception {
        //boss处理连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //worker处理具体业务
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                //设置线程队列等待的个数
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new MessageEncoder())
                                .addLast(new MessageDecoder())
                                .addLast(new TcpHandler(messageController));
                    }
                });
        //绑定端口
        ChannelFuture channelFuture = bootstrap.bind(5858).sync();
        //对关闭通道进行监听
        channelFuture.channel().closeFuture().sync();
    }
}
