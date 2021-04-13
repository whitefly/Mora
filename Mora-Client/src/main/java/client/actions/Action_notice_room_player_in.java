package client.actions;

import io.netty.channel.Channel;


public class Action_notice_room_player_in extends ClientAction {
    /**
     * 在玩家加入房间后等待过程后,有新玩家加入
     *
     * @param channel
     * @param data
     */
    @Override
    public void process(Channel channel, String data) {
        System.out.println(data);
    }
}
