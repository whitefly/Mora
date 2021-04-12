package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import mora.common.entity.TransferData;


@Slf4j
public class SimpleHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        String s = ctx.channel().id().asLongText();
        log.info("接受到新连接: {}", s);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("fuck");
        if (msg instanceof TransferData.TransferDataProtoc) {
            TransferData.TransferDataProtoc td = (TransferData.TransferDataProtoc) msg;
            log.info("code:{}  info:{} data:{}", td.getCode(), td.getInfo(), td.getData());
        }
    }

}
