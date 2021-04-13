package server.actions;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import mora.common.gameEntity.ClientSide;
import server.RoomManagement;

@Slf4j
public class Action_player_nickname_set_done extends ServerAction {


    @Override
    public void process(Channel channel, String data) {
        //这里直接拿Channel作为玩家id;

        ClientSide cs = new ClientSide();
        cs.setNickName(data);
        cs.setId(channel.id().asLongText());
        cs.setState("空闲");
        cs.setChannel(channel);

        //加入管理
        RoomManagement.addClientSide(cs);
        log.info("新玩家加入管理: {}", cs);
    }
}
