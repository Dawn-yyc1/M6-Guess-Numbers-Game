/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.data;

import com.dawnc.guessnumbergame.TestApplicationConfiguration;
import com.dawnc.guessnumbergame.models.Game;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
public class GamesDaoDBTest {
    
    @Autowired
    GamesDao gamesDao;
    
    @Autowired
    RoundsDao roundsDao;
    
    public GamesDaoDBTest() {
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
     * Test of addGame method, of class GamesDaoDB.
     * add game then retrieve game by gameId
     */
    @Test   
    public void testAddGame() {
        Game game = new Game();
        game.setAnswer("9876");
        game.setRoundStatus(false);
        gamesDao.addGame(game);
        
        Game fromDao = gamesDao.findGameId(game.getGameId());
        
        assertEquals(game, fromDao);
        
    }

    /**
     * Test of getAllGames method, of class GamesDaoDB.
     * add games and retrieves all games in database
     */
    @Test
    public void testGetAllGames() {
        Game game1 = new Game();
        game1.setAnswer("1234");
        game1.setRoundStatus(false);
        gamesDao.addGame(game1);
        
        Game game2 = new Game();
        game2.setAnswer("5678");
        game2.setRoundStatus(false);
        gamesDao.addGame(game2);
        
        List<Game> gameList = gamesDao.getAllGames();
        
        assertEquals(2, gameList.size());
        assertTrue(gameList.contains(game1));
        assertTrue(gameList.contains(game2));
        
        
    }

    /**
     * Test of findGameId method, of class GamesDaoDB.
     */
    @Test
    public void testFindGameId() {
        Game game1 = new Game();
        game1.setAnswer("4567");
        game1.setRoundStatus(false);
        gamesDao.addGame(game1);
        
        Game fromDao = gamesDao.findGameId(game1.getGameId());
        
        //gameId in Game table is the same as the one retrieved from Dao
        assertEquals(game1.getGameId(), fromDao.getGameId());
    }

    /**
     * Test of updateGame method, of class GamesDaoDB.
     */
    @Test
    public void testUpdateGame() {
        
        //added 2 unfinished games where roundStatus is set to false or "0"
        Game testGame = new Game();
        testGame.setAnswer("8765");
        testGame.setRoundStatus(false);
        testGame = gamesDao.addGame(testGame);
        
        Game game1 = new Game();
        game1.setAnswer("7654");
        game1.setRoundStatus(false);
        gamesDao.addGame(game1);
        
        Game fromDaoTestGame = gamesDao.findGameId(testGame.getGameId());
        Game fromDaoGame1 = gamesDao.findGameId(game1.getGameId());
        
        //test if unfinished game is set to false and the correct gameId is updated
        assertTrue(fromDaoTestGame.isRoundStatus() == false);
        assertEquals(fromDaoTestGame.getGameId(), testGame.getGameId());
        assertTrue(fromDaoGame1.isRoundStatus() == false);
        
        //setting testGame roundstatus to true or "1"; testGame is finished
        testGame.setRoundStatus(true);
        fromDaoTestGame.setRoundStatus(true);
        
        //checking if roundStatus is updated to true in the Game table
        assertTrue(testGame.isRoundStatus() == true);
        
        //check if the gameId retrieved from Dao is the same as the gameId added to Dao
        assertEquals(fromDaoTestGame.getGameId(), testGame.getGameId());
        
        //check if the roundStatus is different: testGame should be true and game1 should be false
        assertNotEquals(fromDaoTestGame.isRoundStatus(), fromDaoGame1.isRoundStatus());
    }
    
}
