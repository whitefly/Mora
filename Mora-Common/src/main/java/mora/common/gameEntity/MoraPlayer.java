package mora.common.gameEntity;

import lombok.Data;
import mora.common.myEnum.Card;
import mora.common.myEnum.PlayStatus;

@Data
public class MoraPlayer extends ClientSide {

    private PlayStatus playStatus;
    private boolean prepare;//是否准备
    private Card card;
}
