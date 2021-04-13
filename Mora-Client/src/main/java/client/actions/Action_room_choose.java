package client.actions;

import io.netty.channel.Channel;
import mora.common.myEnum.ServerEventCode;
import mora.common.util.PushUtil;

import java.util.Scanner;

public class Action_room_choose extends ClientAction {
    @Override
    public void process(Channel channel, String data) {
        //选择游戏房间加入

        System.out.println("请选择游戏编号加入游戏(输入e退出)");
        System.out.println(data);
        System.out.print("[mora-cli]:");
        Scanner scanner = new Scanner(System.in);

        String roomId = scanner.nextLine().trim();

        PushUtil.pushToServer(channel, ServerEventCode.room_player_in, null, roomId);
    }
}
