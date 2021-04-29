package com.grtwwh.gameserver.game.module.account.packet;

import com.grtwwh.proto.LoginProto;
import com.grtwwh.gameserver.base.socket.anno.SocketProtocol;
import com.grtwwh.gameserver.game.packet.PacketId;

/**
 * 账号登录协议
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/26 10:13
 */
@SocketProtocol(packetId = PacketId.ACCOUNT_LOGIN_REQ, protoClass = LoginProto.AccountLoginReq.class)
public class AccountLoginReq {

    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return account;
    }
}
