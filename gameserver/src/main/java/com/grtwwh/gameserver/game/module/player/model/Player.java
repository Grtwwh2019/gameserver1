package com.grtwwh.gameserver.game.module.player.model;

/**
 * 玩家对象
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/27 15:01
 */
public class Player {

    private long playerId;

    private String name;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", name='" + name + '\'' +
                '}';
    }
}
