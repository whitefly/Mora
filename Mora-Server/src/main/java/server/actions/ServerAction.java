package server.actions;

import io.netty.channel.Channel;
import mora.common.myEnum.ServerEventCode;

import java.util.HashMap;
import java.util.Map;

public abstract class ServerAction {


    public abstract void process(Channel channel, String data);

    //执行code 和 Action的映射关系
    public static Map<ServerEventCode, ServerAction> serverActionMap = new HashMap<>();
}

