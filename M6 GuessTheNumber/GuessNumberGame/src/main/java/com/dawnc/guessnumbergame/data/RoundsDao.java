/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.data;

import com.dawnc.guessnumbergame.models.Round;
import java.util.List;

/**
 *
 * @author pisce
 */
public interface RoundsDao {
    
    Round addRound(Round round);
    List<Round> getAllRounds(int gameId);
    void deleteAllRounds();
   
}
