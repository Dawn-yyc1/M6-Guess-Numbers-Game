/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.data;

import com.dawnc.guessnumbergame.models.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class RoundsDaoDB implements RoundsDao {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(gameId, guess, result, gameTime) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(INSERT_ROUND, round.getGameId(),round.getGuess(), round.getResult(), round.getGameTime());
        
        int newRound = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRound);

        return round;
    }

    //gets all rounds played by gameId
    @Override
    public List<Round> getAllRounds(int gameId) {
        try{
            final String sql = "SELECT * FROM round WHERE gameId = ? ORDER BY gameTime";
            return jdbcTemplate.query(sql, new roundMapper(), gameId);
        }catch(DataAccessException ex){
            return null;
        }
    }

     //created only for testing, delete everything in table to put database in a known good state
    @Override
    public void deleteAllRounds() {
        final String sql = "DELETE FROM round";
        jdbcTemplate.update(sql);
    }
    
    private static final class roundMapper implements RowMapper<Round>{
        
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException{
            Round rd = new Round();
            rd.setRoundId(rs.getInt("roundId"));
            rd.setGameId(rs.getInt("gameId"));
            rd.setGuess(rs.getString("guess"));
            rd.setResult(rs.getString("result"));
            Timestamp timestamp = rs.getTimestamp("gameTime");
            rd.setGameTime(timestamp.toLocalDateTime());
            return rd;
        }
    }
}
