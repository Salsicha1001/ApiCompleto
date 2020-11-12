package com.borges.api_complete.Controller;


import com.borges.api_complete.Model.CardUser;
import com.borges.api_complete.Model.DTO.CardDTO;
import com.borges.api_complete.Service.CardYugiohService;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/card")
public class CardController {

    @Autowired
    private CardYugiohService cardService;


    @PostMapping(value = "/add")
    public Object CardAdd(@RequestBody CardUser card) throws ExecutionException, InterruptedException {
        return cardService.saveCardUser(card);
    }

    @GetMapping("/cart/{email}")
    public List GetCardCart(@PathVariable String email) throws ExecutionException, InterruptedException {
        return (List) cardService.getCardsUser(email);
    }

    @GetMapping("/remove/{id}")
    public Object removeFavorite(@PathVariable String id) throws ExecutionException, InterruptedException {
        return cardService.deleteFavoriteCard(id);

    }

    @GetMapping(value = "/all")
    public Object getAll(@RequestParam(value ="offset", defaultValue = "0")Integer offset,
                       @RequestParam(value="num", defaultValue = "10") Integer num) throws IOException, JSONException {


       return cardService.findAll(num,offset);

    }
    @GetMapping(value = "/spell")
    public Object getSpellCard(@RequestParam(value ="offset", defaultValue = "0")Integer offset,
                               @RequestParam(value="num", defaultValue = "10") Integer num) throws IOException, JSONException {
        return cardService.findSpellCard(offset, num);
    }
    @GetMapping(value = "/trap")
    public String getTrapCard(@RequestParam(value ="offset", defaultValue = "0")Integer offset,
                              @RequestParam(value="num", defaultValue = "10") Integer num) throws IOException, JSONException {
        return cardService.findTrapCard(offset, num);
    }
    @GetMapping(value = "/trap/{race}")
    public Object getTrapRaceCard(@PathVariable String race,
                                  @RequestParam(value ="offset", defaultValue = "0")Integer offset,
                                  @RequestParam(value="num", defaultValue = "10") Integer num) throws IOException, JSONException {
    return cardService.findRaceTrap(race,offset, num);
    }
    @GetMapping(value = "/spell/{race}")
    public Object getSpellRaceCard(@PathVariable String race,
                                   @RequestParam(value ="offset", defaultValue = "0")Integer offset,
                                   @RequestParam(value="num", defaultValue = "10") Integer num) throws IOException, JSONException {
        return cardService.findRaceSpell(race,offset, num);
    }

    @GetMapping(value = "/monsterType/{type}")
    public Object getTypeCardMonster(@PathVariable String type,
                                     @RequestParam(value ="offset", defaultValue = "0")Integer offset,
                                     @RequestParam(value="num", defaultValue = "10") Integer num) throws IOException, JSONException {
        return cardService.FindMonsterCard(type, null,offset, num);
    }
    @GetMapping(value = "/monsterRace/{race}")
    public Object getRaceCardMonster(@PathVariable String race,
                                     @RequestParam(value ="offset", defaultValue = "0")Integer offset,
                                     @RequestParam(value="num", defaultValue = "10") Integer num) throws IOException, JSONException {
        return cardService.FindMonsterCard(null, race,offset, num);
    }

    @GetMapping(value = "/monster/{type}/{race}")
    public Object getMonster(@PathVariable String type,@PathVariable String race,
                             @RequestParam(value ="offset", defaultValue = "0")Integer offset,
                             @RequestParam(value="num", defaultValue = "10") Integer num) throws IOException, JSONException {
        return cardService.FindMonsterCard(type, race,offset, num);
    }
}
