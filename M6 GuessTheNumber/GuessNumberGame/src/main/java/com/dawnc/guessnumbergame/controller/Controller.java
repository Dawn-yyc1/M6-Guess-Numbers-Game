/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.controller;

import com.dawnc.guessnumbergame.models.Round;
import com.dawnc.guessnumbergame.models.Game;
import com.dawnc.guessnumbergame.service.ServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author pisce
 */
@RestController
@RequestMapping("/api/guessnumbergame")
public class Controller {
    
    @Autowired
    ServiceLayer serviceLayer;

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public String startGame(){
        return serviceLayer.startGame();
    }
    
    @PostMapping("/guess")
    public Round playGame(@RequestBody Round round){
        return serviceLayer.playGame(round);
    }
    
    @GetMapping("/game") 
    public List<Game> getAllGames(){
        return serviceLayer.getAllGames();
    }
    
    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game>findGameId(@PathVariable int gameId){
        Game result = serviceLayer.findGameId(gameId);
        if(result == null){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }    
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/round/{gameId}")
    public ResponseEntity<List<Round>>findRoundId(@PathVariable int gameId){
        List<Round> result = serviceLayer.getAllRounds(gameId);
        if(result == null){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }    
        return ResponseEntity.ok(result);
    }
}
