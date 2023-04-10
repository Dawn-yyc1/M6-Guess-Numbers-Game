/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.service;

import com.dawnc.guessnumbergame.models.Round;
import com.dawnc.guessnumbergame.models.Game;
import java.util.List;

/**
 *
 * @author pisce
 */
public interface ServiceLayer {
    
    public String startGame();
    
    public Round playGame(Round round);
    
    List<Game> getAllGames();
    
    Game findGameId(int gameId);
 
    List<Round> getAllRounds(int gameId);
    
}
