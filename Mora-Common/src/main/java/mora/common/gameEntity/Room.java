package mora.common.gameEntity;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Room {

    public Room(Integer id) {
        this.id = id;
    }

    protected String name; //房间名
    protected Integer id; //房间唯一id
    protected String ownerId; //房间创建者id

    //游戏参与者
    List<ClientSide> players = new ArrayList<>();

    //游戏旁观者
    List<ClientSide> watcher = new ArrayList<>();

    public boolean addPlayer(ClientSide clientSide) {
        // TODO: 2021/4/13 是否会产生并发问题?
        players.add(clientSide);
        return true;
    }

    public boolean removePlayer(ClientSide clientSide) {
        return players.remove(clientSide);
    }

    public int playerCount() {
        return players.size();
    }
}
