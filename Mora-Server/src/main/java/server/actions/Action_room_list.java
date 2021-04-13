package server.actions;

import io.netty.channel.Channel;
import mora.common.gameEntity.Room;
import mora.common.myEnum.ClientEventCode;
import mora.common.util.PushUtil;
import server.RoomManagement;

import java.util.List;

public class Action_room_list extends ServerAction {
    @Override
    public void process(Channel channel, String data) {
        //列出所有房间
        StringBuilder sb = genAllRoomInfo();
        PushUtil.pushToClient(channel, ClientEventCode.room_choose, null, sb.toString());
    }

    private StringBuilder genAllRoomInfo() {
        List<Room> rooms = RoomManagement.roomList();
        StringBuilder sb = new StringBuilder();
        for (Room room : rooms) {
            sb.append(room.getId());
            sb.append(".");
            sb.append(room.getName());
            sb.append("  (已有 ").append(room.playerCount()).append(" 个玩家)");
            sb.append("\n");
        }
        return sb;
    }


}
