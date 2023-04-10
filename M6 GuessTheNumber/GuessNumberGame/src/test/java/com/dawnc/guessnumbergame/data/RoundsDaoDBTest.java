/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.data;

import com.dawnc.guessnumbergame.TestApplicationConfiguration;
import com.dawnc.guessnumbergame.models.Game;
import com.dawnc.guessnumbergame.models.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author pisce
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundsDaoDBTest {
    
    @Autowired
    RoundsDao roundsDao;
    
    @Autowired
    GamesDao gamesDao;
    
    public RoundsDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    //all records in Round and Game tables is deleted to put database in known good state
    @Before
    public void setUp() {
        roundsDao.deleteAllRounds();
        gamesDao.deleteAllGames();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addRound method, of class RoundsDaoDB.
     * addRound and getAllRounds is tested together
     * created 1 game and 2 rounds
     */
    @Test
    public void testAddRoundAndGetAllRounds() {

        Game game1 = new Game();
        game1.setGameId(game1.getGameId());
        game1.setAnswer("9876");
        game1.setRoundStatus(false);
        game1 = gamesDao.addGame(game1);
             
        //roundId is not set because it's auto-generated in SQL
        Round round1 = new Round();
        round1.setGameId(game1.getGameId());
        round1.setGuess("1234");
        round1.setGameTime(LocalDateTime.now());
        round1.setResult("e:0 p:0");
        round1 = roundsDao.addRound(round1);
        
        Round round2 = new Round();
        round2.setGameId(game1.getGameId());
        round2.setGuess("1046");
        round2.setGameTime(LocalDateTime.now());
        round2.setResult("e:0 p:2");
        round2 = roundsDao.addRound(round2);
        
        List<Round> fromDao = new ArrayList<>();
        fromDao.add(round1);
        fromDao.add(round2);
                
        fromDao = roundsDao.getAllRounds(game1.getGameId());
        
        //2 rounds are in ArrayList
        assertEquals(2, fromDao.size());
        
        //gameId in the Round table is the same as gameId in the Game table
        assertEquals(round1.getGameId(), game1.getGameId());
        assertEquals(round2.getGameId(), game1.getGameId());
        
    }
    
}
