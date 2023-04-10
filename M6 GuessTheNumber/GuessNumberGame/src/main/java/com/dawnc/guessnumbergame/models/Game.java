 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.models;

import java.util.Objects;

/**
 *
 * @author pisce
 */
public class Game {
    
    private int gameId;
    private String answer;
    private boolean roundStatus;

    public Game(int gameId, String answer, boolean roundStatus) {
        this.gameId = gameId;
        this.answer = answer;
        this.roundStatus = roundStatus;
    }

    public Game() {
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    //default = 0 which mean false, a value of 1 = true
    public boolean isRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(boolean roundStatus) {
        this.roundStatus = roundStatus;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (this.roundStatus != other.roundStatus) {
            return false;
        }
        if (!Objects.equals(this.answer, other.answer)) {
            return false;
        }
        return true;
    }
    
    
}
