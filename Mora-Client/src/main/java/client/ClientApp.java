package client;

import client.handler.DefaultChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientApp {
    static final int inetPort = 8085;
    static final String serverAddress = "127.0.0.1";

    public void startUpClient() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new DefaultChannelInitializer());
            Channel channel = bootstrap.connect(serverAddress, inetPort).sync().channel();
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ClientApp clientApp = new ClientApp();
        clientApp.startUpClient();
    }
}
