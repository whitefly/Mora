package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import mora.common.entity.TransferData;
import mora.common.gameEntity.ClientSide;
import mora.common.gameEntity.Room;
import mora.common.myEnum.ClientEventCode;
import mora.common.myEnum.ServerEventCode;
import mora.common.util.PushUtil;
import server.RoomManagement;
import server.actions.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    public ServerHandler() {
        initMap();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        String s = ctx.channel().id().asLongText();
        log.info("接受到新连接: {}", s);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO: 2021/4/12 未来引入系统,现在展示每次连接成功输入昵称
        //新链接来临后,要求客户端尝试昵称
        Channel channel = ctx.channel();
        PushUtil.pushToClient(channel, ClientEventCode.player_nickname_set, null, null);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TransferData.TransferDataProtoc) {
            TransferData.TransferDataProtoc td = (TransferData.TransferDataProtoc) msg;
            String code = td.getCode();
            ServerEventCode serverEventCode = ServerEventCode.valueOf(code);
            ServerAction action = ServerAction.serverActionMap.get(serverEventCode);
            action.process(ctx.channel(), td.getData());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //链接断开,删除用户信息
        Channel channel = ctx.channel();
        String channelId = channel.id().asLongText();
        log.info("client断线: {}", channelId);
        ClientSide clientById = RoomManagement.getClientById(channelId);
        Room roomById = RoomManagement.getRoomById(clientById.getRoomId());
        RoomManagement.deleteClient(channelId);

        //通知房间内的其他人
        String content = String.format("玩家[ %s ]退出本房间", clientById.getNickName());
        List<Channel> others = roomById.getPlayers().stream().map(ClientSide::getChannel).collect(Collectors.toList());
        PushUtil.pushToAllClient(others, ClientEventCode.notice_room_player_out, null, content);
    }

    private static void initMap() {
        ServerAction.serverActionMap.put(ServerEventCode.player_nickname_set_done, new Action_player_nickname_set_done());
        ServerAction.serverActionMap.put(ServerEventCode.room_create, new Action_room_create());
        ServerAction.serverActionMap.put(ServerEventCode.room_list, new Action_room_list());
        ServerAction.serverActionMap.put(ServerEventCode.room_player_in, new Action_room_player_in());
    }

}
