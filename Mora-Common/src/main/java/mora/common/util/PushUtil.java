package mora.common.util;

import io.netty.channel.Channel;
import mora.common.entity.TransferData;
import mora.common.myEnum.ClientEventCode;
import mora.common.myEnum.ServerEventCode;

import java.util.List;

public class PushUtil {

    public static void pushToServer(Channel channel, ServerEventCode code, String info, String data) {
        TransferData.TransferDataProtoc.Builder builder = TransferData.TransferDataProtoc.newBuilder();

        builder.setCode(code.name());
        if (info != null) builder.setInfo(info);
        if (data != null) builder.setData(data);

        TransferData.TransferDataProtoc build = builder.build();
        //往客户端发送信息
        channel.writeAndFlush(build);
    }

    public static void pushToClient(Channel channel, ClientEventCode code, String info, String data) {
        TransferData.TransferDataProtoc.Builder builder = TransferData.TransferDataProtoc.newBuilder();

        builder.setCode(code.name());
        if (info != null) builder.setInfo(info);
        if (data != null) builder.setData(data);

        TransferData.TransferDataProtoc build = builder.build();
        //往客户端发送信息
        channel.writeAndFlush(build);
    }

    public static void pushToAllClient(List<Channel> channels, ClientEventCode code, String info, String data) {
        for (Channel c : channels) {
            pushToClient(c, code, info, data);
        }
    }
}
