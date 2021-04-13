package mora.common.myEnum;

public enum ClientEventCode {

    player_nickname_set("玩家设置昵称"),

    room_choose("游戏房间选择"),

    notice_room_player_in("有新玩家加入"),

    notice_room_player_out("现在的玩家退出");

    String describe;

    ClientEventCode(String describe) {
        this.describe = describe;
    }
}
