package client.actions;

import io.netty.channel.Channel;
import mora.common.myEnum.ClientEventCode;

import java.util.HashMap;
import java.util.Map;

public abstract class ClientAction {
    public abstract void process(Channel channel, String data);

    //执行code 和 Action的映射关系
    public static Map<ClientEventCode, ClientAction> ClientActionMap = new HashMap<>();
}
