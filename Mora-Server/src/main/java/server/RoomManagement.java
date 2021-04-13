package server;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import mora.common.gameEntity.ClientSide;
import mora.common.gameEntity.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
public class RoomManagement {

    /**
     * 这里包含所有玩家
     * 所有的房间
     */

    //房间号id生成器
    private static AtomicInteger roomIdMaker = new AtomicInteger(0);

    public static Map<String, ClientSide> clientSideMap = new ConcurrentHashMap<>();
    public static Map<Integer, Room> roomMap = new ConcurrentHashMap<>();

    public static Integer genNextRoomId() {
        return roomIdMaker.incrementAndGet();
    }


    public static void addClientSide(ClientSide clientSide) {
        clientSideMap.put(clientSide.getId(), clientSide);
    }

    public static void addRoom(Room room) {
        roomMap.put(room.getId(), room);
    }

    public static List<Room> roomList() {
        return new ArrayList<>(roomMap.values());
    }

    public static Room getRoomById(Integer roomId) {
        return roomMap.get(roomId);
    }

    public static ClientSide getClientById(String clientId) {
        return clientSideMap.get(clientId);
    }

    public static void deleteClient(String clientId) {
        ClientSide clientSide = clientSideMap.remove(clientId);

        Integer roomId;
        if (clientSide != null && (roomId = clientSide.getRoomId()) != null) {
            Room room = roomMap.get(roomId);
            room.removePlayer(clientSide);
            if (room.playerCount() == 0) {
                roomMap.remove(roomId);
            }
        }
        log.info("玩家[ {} ]移除管理集合", clientSide.getNickName());
    }
}
