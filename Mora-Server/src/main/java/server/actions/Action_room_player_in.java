package server.actions;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import mora.common.gameEntity.ClientSide;
import mora.common.gameEntity.Room;
import mora.common.myEnum.ClientEventCode;
import mora.common.util.PushUtil;
import server.RoomManagement;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Action_room_player_in extends ServerAction {
    @Override
    public void process(Channel channel, String data) {
        //用户加入房间
        Integer roomId = Integer.parseInt(data);
        Room room = RoomManagement.roomMap.get(roomId);
        if (room != null) {
            ClientSide clientSide = RoomManagement.clientSideMap.get(channel.id().asLongText());
            room.addPlayer(clientSide);
            clientSide.setRoomId(room.getId());
            log.info("玩家[ {} ]加入 {} 号房间", clientSide.getNickName(), room.getId());
            //通知等待的其他有人来了
            noticeOthers(room, clientSide);
        }
    }

    public void noticeOthers(Room room, ClientSide client) {
        String content = String.format("玩家[ %s ]加入到本房间", client.getNickName());
        List<ClientSide> players = room.getPlayers();
        List<Channel> others = players.stream().filter(x -> x != client).map(ClientSide::getChannel).collect(Collectors.toList());
        PushUtil.pushToAllClient(others, ClientEventCode.notice_room_player_in, null, content);
    }
}
