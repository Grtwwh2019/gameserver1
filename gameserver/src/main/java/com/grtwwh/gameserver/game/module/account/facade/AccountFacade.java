package com.grtwwh.gameserver.game.module.account.facade;

import com.grtwwh.gameserver.base.event.EventReceiver;
import com.grtwwh.gameserver.base.socket.session.SocketSession;
import com.grtwwh.gameserver.game.module.account.packet.AccountLoginReq;
import com.grtwwh.gameserver.game.module.account.packet.AccountLoginReq1;
import com.grtwwh.gameserver.game.module.player.model.Player;
import org.springframework.stereotype.Component;

/**
 * 门面类，接收协议的层面
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/26 11:06
 */
@Component
public class AccountFacade {


    @EventReceiver
    public void accountLogin(SocketSession socketSession, AccountLoginReq req) {
        Player player = new Player();
        player.setName("nihao");
        player.setPlayerId(3768);
        socketSession.bindMain(player);
        System.out.println(socketSession.getSocketAddress() + "_data:" + req);
    }

    @EventReceiver
    public void accountLogin1(Player player, AccountLoginReq1 req) {
        System.out.println(player.toString() + "_data:" + req);
    }


}
