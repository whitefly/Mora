package mora.common.myEnum;

public enum ServerEventCode {

    player_nickname_set_done("得到玩家设置的昵称"),

    room_create("创建房间"),
    room_list("列出所有房间"),
    room_player_in("玩家加入房间"),
    room_player_prepare("玩家准备"),
    room_player_Prepare_cancel("玩家取消准备"),
    room_player_exit("玩家退出房间");

    String describe;

    ServerEventCode(String describe) {
        this.describe = describe;
    }
}
