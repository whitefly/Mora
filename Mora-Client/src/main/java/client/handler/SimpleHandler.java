package client.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import mora.common.myEnum.MsgCode;
import mora.common.util.PushUtil;

@Slf4j
public class SimpleHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("成功连接到服务器");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        PushUtil.pushToServer(ch, MsgCode.Hello, "你好", "各种数据");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String s = ctx.channel().id().asLongText();
        log.info("客户端收到: { }", s);
    }
}
