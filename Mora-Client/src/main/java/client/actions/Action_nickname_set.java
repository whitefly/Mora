package client.actions;

import io.netty.channel.Channel;
import mora.common.myEnum.ServerEventCode;
import mora.common.util.PushUtil;

import java.util.Scanner;

public class Action_nickname_set extends ClientAction {


    @Override
    public void process(Channel channel, String data) {
        //显示提示信息
        System.out.println("请输入昵称");
        System.out.print("[mora-cli]:");

        //不能关流(System.in关闭后无法再开启)
        Scanner scanner = new Scanner(System.in);
        String nickName = scanner.nextLine();
        PushUtil.pushToServer(channel, ServerEventCode.player_nickname_set_done, null, nickName);

        System.out.println("-----操作选项----");
        System.out.println("1.创建新房间");
        System.out.println("2.列出所有房间");
        System.out.println("-----输入操作编号----");

        System.out.print("[mora-cli]:");
        String op = scanner.nextLine().trim();
        if ("1".equals(op)) {
            //创建房间
            System.out.println("-----操作选项----");
            System.out.println("请给房间命名");
            System.out.print("[mora-cli]:");
            String nameName = scanner.nextLine();
            PushUtil.pushToServer(channel, ServerEventCode.room_create, null, nameName);
        } else if ("2".equals(op)) {
            //列出所有的房间
            PushUtil.pushToServer(channel, ServerEventCode.room_list, null, null);
        }
    }
}
