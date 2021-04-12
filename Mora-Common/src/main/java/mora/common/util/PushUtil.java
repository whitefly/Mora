package mora.common.util;

import io.netty.channel.Channel;
import mora.common.entity.TransferData;
import mora.common.myEnum.MsgCode;

public class PushUtil {

    public static void pushToServer(Channel channel, MsgCode code, String info, String data) {
        TransferData.TransferDataProtoc.Builder builder = TransferData.TransferDataProtoc.newBuilder();

        builder.setCode(code.name());
        builder.setInfo(info);
        builder.setData(data);

        TransferData.TransferDataProtoc build = builder.build();
        //往客户端发送信息
        channel.writeAndFlush(build);
    }
}
