package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import server.handler.ChildChannelInitializer;

@Slf4j
public class ServerApp {
    static final int inetPort = 8085;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(inetPort).childHandler(new ChildChannelInitializer());

            //绑定端口
            ChannelFuture f = boot.bind().sync();

            //阻塞监听
            log.info("服务器启动,端口监听:{}", inetPort);
            f.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
