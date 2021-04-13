package client.handler;

import client.actions.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import mora.common.entity.TransferData;
import mora.common.myEnum.ClientEventCode;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    public ClientHandler() {
        initMap();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("成功连接到服务器");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TransferData.TransferDataProtoc) {
            TransferData.TransferDataProtoc td = (TransferData.TransferDataProtoc) msg;
            String code = td.getCode();
            ClientEventCode clientEventCode = ClientEventCode.valueOf(code);
            ClientAction action = ClientAction.ClientActionMap.get(clientEventCode);
            action.process(ctx.channel(), td.getData());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接断开...进程退出");
        System.exit(2);
    }

    private static void initMap() {
        ClientAction.ClientActionMap.put(ClientEventCode.player_nickname_set, new Action_nickname_set());
        ClientAction.ClientActionMap.put(ClientEventCode.room_choose, new Action_room_choose());
        ClientAction.ClientActionMap.put(ClientEventCode.notice_room_player_in, new Action_notice_room_player_in());
        ClientAction.ClientActionMap.put(ClientEventCode.notice_room_player_out, new Action_notice_room_player_out());
    }
}
