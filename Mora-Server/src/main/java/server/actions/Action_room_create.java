package server.actions;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import mora.common.gameEntity.ClientSide;
import mora.common.gameEntity.Room;
import server.RoomManagement;

@Slf4j
public class Action_room_create extends ServerAction {
    @Override
    public void process(Channel channel, String data) {
        String channelId = channel.id().asLongText();
        ClientSide clientSide = RoomManagement.clientSideMap.get(channelId);
        //创建房间
        Integer roomId = RoomManagement.genNextRoomId();

        Room room = new Room(roomId);
        room.setOwnerId(channelId);
        room.setName(data);
        RoomManagement.addRoom(room);
        log.info("玩家[ {} ]创建 {} 号房间", clientSide.getNickName(), room.getId());

        //加入房间
        room.addPlayer(clientSide);
        log.info("玩家[ {} ]加入 {} 号房间", clientSide.getNickName(), room.getId());
    }
}
