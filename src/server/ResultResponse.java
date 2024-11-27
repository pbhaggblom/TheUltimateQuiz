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
//        this.playerResult = player.getScore();
//        this.opponentResult = opponent.getScore()
        this.isFinal = isFinal;
    }

    public String getPlayer() {
        return player;
    }

//    public int getPlayerResult() {
//        return player.getResult();
//    }

    public String getOpponent() {
        return opponent;
    }

//    public int getPlayer2Result() {
//        return player1.getResult();
//    }

    public boolean isFinal() {
        return isFinal;
    }
}