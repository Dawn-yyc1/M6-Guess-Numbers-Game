/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.data;

import com.dawnc.guessnumbergame.models.Game;
import java.util.List;

/**
 *
 * @author pisce
 */
public interface GamesDao {
     
    Game addGame(Game game);
    List<Game> getAllGames();
    Game findGameId(int gameId);
    void updateGame(Game game);
    void deleteAllGames();
}
