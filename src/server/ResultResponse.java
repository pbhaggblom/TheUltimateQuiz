package server;

import java.io.Serializable;

public class ResultResponse implements Serializable {

    private String player;
    private String opponent;
    private int playerResult;
    private int opponentResult;
    private boolean isFinal;

    public ResultResponse(Player player, Player opponent, boolean isFinal) {
        this.player = player.getName();
        this.opponent = opponent.getName();
        this.playerResult = player.getPoints();
        this.opponentResult = opponent.getPoints();
        this.isFinal = isFinal;
    }

    public String getPlayer() {
        return player;
    }

    public int getPlayerResult() {
        return playerResult;
    }

    public String getOpponent() {
        return opponent;
    }

    public int getOpponentResult() {
        return opponentResult;
    }

    public boolean isFinal() {
        return isFinal;
    }
}
