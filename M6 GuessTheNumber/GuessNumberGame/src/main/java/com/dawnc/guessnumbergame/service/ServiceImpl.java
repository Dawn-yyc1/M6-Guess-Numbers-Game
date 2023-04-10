/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dawnc.guessnumbergame.service;

import com.dawnc.guessnumbergame.data.GamesDao;
import com.dawnc.guessnumbergame.data.RoundsDao;
import com.dawnc.guessnumbergame.models.Round;
import com.dawnc.guessnumbergame.models.Game;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pisce
 */
@Service
public class ServiceImpl implements ServiceLayer {
    
    @Autowired
    GamesDao gDao;
    
    @Autowired
    RoundsDao rDao;
    
    @Override
    public String startGame(){
        Game game = new Game();   
        String randomNumber = generateNum();
        String hideNumber = maskNumber(randomNumber);
        game.setAnswer(randomNumber);
        game = gDao.addGame(game);
        return "GameId: " + game.getGameId() + "\tAnswer: " + hideNumber;
    }
    
    //get answer from Game table
    //compare answer and guess to get result
    @Override
    public Round playGame(Round round){
        String answer = gDao.findGameId(round.getGameId()).getAnswer();
        String guess = round.getGuess();  
        round.setGuess(guess);
        round.setRoundId(round.getRoundId());
        round.setGameTime(LocalDateTime.now());
        String result = findResult(answer, guess);
        round.setResult(result);

        if(guess.equals(answer)){ 
           int finishedRound = round.getGameId();
           round.setRoundId(finishedRound);
           Game game = gDao.findGameId(finishedRound);
           game.setRoundStatus(true);
           gDao.updateGame(game);
       }
       return rDao.addRound(round);
    }
    
    @Override
    public List<Game> getAllGames() {
        List<Game> hideGames = gDao.getAllGames();
//        for(Game h: hideGames){
//            if(!h.isRoundStatus()) {
//            h.setAnswer("####");
//            }
//        }
        hideGames.stream()
                .filter(gm -> (!gm.isRoundStatus()))  //if roundStatus is false
                .forEachOrdered(gm -> {
            gm.setAnswer("####");
        });
        return hideGames;
    }

    @Override
    public Game findGameId(int gameId) {
        Game game = gDao.findGameId(gameId);
        if(game.isRoundStatus() == false){
            game.setAnswer("####");
        }
        return game;
    }

    @Override
    public List<Round> getAllRounds(int gameId) {
        return rDao.getAllRounds(gameId);
    }
    
    private String maskNumber(String str){
        String maskedNumber = str.replace(str, "####");
        return maskedNumber;
    }
    
    //generates random ArrayList[i,j,k,m]
    private String generateNum(){
        Random rand = new Random();
//        int randomNum = rand.nextInt(10000-1000) + 1000;

        ArrayList<Integer> randomNumList = new ArrayList<>();
        
        
        for(int i = 0; i<1; i++) {
            randomNumList.add(rand.nextInt(10-1)+1);
            for (int j = 0; j < 3; j++) {
                randomNumList.add(rand.nextInt(10));               
                    if((Objects.equals(randomNumList.get(j), randomNumList.get(randomNumList.size()-1))) 
                            && Objects.equals(randomNumList.get(j), randomNumList.get(j+1))
                            && (Objects.equals(randomNumList.get(i), randomNumList.get(j)))){
                        randomNumList.remove(randomNumList.get(randomNumList.size()-1));
                        randomNumList.add(rand.nextInt(10));
                    }
            }
        }   
        
        
     
        
//        for(int i = 0; i<1; i++) {
//            randomNumList.add(rand.nextInt(10-1)+1);
//            for (int j = 0; j < 1; j++) {  
//                randomNumList.add(rand.nextInt(10));
//                if((Objects.equals(randomNumList.get(j), randomNumList.get(i)))){
//                    randomNumList.remove(randomNumList.get(j));
//                    randomNumList.add(rand.nextInt(10));
//                    }
//                for (int k = 0; k < 1; k++) {
//                    randomNumList.add(rand.nextInt(10));
//                    if((Objects.equals(randomNumList.get(k), randomNumList.get(j)))){
//                        randomNumList.remove(randomNumList.get(k));                    
//                        randomNumList.add(rand.nextInt(10));
//                    }    
//                    checklastNum:
//                    for (int m = 0; m < 1; m++) {
//                        randomNumList.add(rand.nextInt(10));
//                        if((Objects.equals(randomNumList.get(k), randomNumList.get(m)))){
//                            randomNumList.remove(randomNumList.get(m));
//                            randomNumList.add(rand.nextInt(10));
//                            continue checklastNum;
//                        }
//                }   
//            }
//        }
//    }

        //returns [n1,n2,n3,n4] so have remove brackets and commas
        String str = "";
        for (Integer f : randomNumList) {
            str += f;
    }
        return str;
    }


    //char[] vs array[]: Strings are immutable therefore String[] are immutable,
    //char[] is mutable + less built-in functions
    //it can be manipulated, contain single characters as its elements 
    //i.e. String[]a = "1234"; vs char[]a = 1,2,3,4;
    //enhanced for-loop vs traditional loops: traditional loops have more control over incrementing
    //tried regex but that is more for pattern matching like password patterns
    private String findResult (String guess, String answer){

        char[] guessArray = guess.toCharArray();
        char[] answerArray = answer.toCharArray();
        
        int partialMatch = 0;
        int exactMatch = 0;
        
        for(int i=0; i<guessArray.length; i++){
            if(guess.charAt(i) == (answer.charAt(i))){
                exactMatch++;
            }else if(guess.indexOf(answer.charAt(i))>=1){
                partialMatch++;
            }            
        }     

        String result = "e:" + exactMatch + " " + "p:" + partialMatch;
        return result;
    }
    
    
}
