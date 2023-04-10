/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.data;

import com.dawnc.guessnumbergame.controller.GameExceptionHandler;
import com.dawnc.guessnumbergame.models.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author pisce
 */
@Repository
public class GamesDaoDB implements GamesDao {
    
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public GamesDaoDB(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }        
        
@Override
@Transactional
public Game addGame(Game game) {
    final String INSERT_GAME = "INSERT INTO game (answer, roundStatus) VALUES(?, ?)";
    jdbcTemplate.update(INSERT_GAME,
            game.getAnswer(),
            game.isRoundStatus());
    int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    game.setGameId(newId);
    return game;
}

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT * FROM game;";
        return jdbcTemplate.query(sql, new gameMapper());
    }

    @Override
    public Game findGameId(int gameId) {
        try{
            final String sql = "SELECT * FROM game WHERE gameId =?;";
            return jdbcTemplate.queryForObject(sql, new gameMapper(), gameId);
        }catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    public void updateGame(Game game) {
        final String sql = "UPDATE game SET roundStatus = ? WHERE gameId = ?";
        jdbcTemplate.update(sql, game.isRoundStatus(), game.getGameId());
    }
    
    //created only for testing, delete everything in table to put database in a known good state
    @Override
    public void deleteAllGames() {
        final String sql = "DELETE FROM game";
        jdbcTemplate.update(sql);
    }
     
    private static final class gameMapper implements RowMapper<Game>{
            
            @Override
            public Game mapRow(ResultSet rs, int index) throws SQLException{
                Game gm = new Game();
                gm.setGameId(rs.getInt("gameId"));
                gm.setAnswer(rs.getString("answer"));
                gm.setRoundStatus(rs.getBoolean("roundStatus")); 
                return gm;
            }
    }
}
