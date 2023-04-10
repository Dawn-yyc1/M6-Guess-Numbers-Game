/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author pisce
 */
public class Round {
    
    //ultimately used String for guess and guess instead of int so I can use
    //length property to limit digits to 4, int only has size property
    private int roundId;
    private int gameId;
    private String guess;
    private String result;
    private LocalDateTime gameTime;

    public Round(int roundId, int gameId, String guess, String result, LocalDateTime gameTime) {
        this.roundId = roundId;
        this.gameId = gameId;
        this.guess = guess;
        this.result = result;
        this.gameTime = gameTime;
    }

    public Round() {
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public LocalDateTime getGameTime() {
        return gameTime;
    }

    public void setGameTime(LocalDateTime gameTime) {
        this.gameTime = gameTime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.guess, other.guess)) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        if (!Objects.equals(this.gameTime, other.gameTime)) {
            return false;
        }
        return true;
    }

    
}
