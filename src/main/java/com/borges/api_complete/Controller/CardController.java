package com.borges.api_complete.Controller;


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
    public Object CardAdd(@RequestBody CardDTO cardDTO) throws ExecutionException, InterruptedException {
        return cardService.saveCardUser(cardDTO);
    }

    @GetMapping("/cart")
    public List GetCardCart(@RequestBody String email){
        return (List) cardService.getCardsUser(email);
    }


    @GetMapping(value = "/spell")
    public String getSpellCard() throws IOException, JSONException {
        return cardService.findSpellCard();
    }
    @GetMapping(value = "/trap")
    public String getTrapCard() throws IOException, JSONException {
        return cardService.findTrapCard();
    }
    @GetMapping(value = "/trap/{race}")
    public Object getTrapRaceCard(@PathVariable String race) throws IOException, JSONException {
    return cardService.findRaceTrap(race);
    }
    @GetMapping(value = "/spell/{race}")
    public Object getSpellRaceCard(@PathVariable String race) throws IOException, JSONException {
        return cardService.findRaceSpell(race);
    }

    @GetMapping(value = "/monsterType/{type}")
    public Object getTypeCardMonster(@PathVariable String type) throws IOException, JSONException {
        return cardService.FindMonsterCard(type, null);
    }
    @GetMapping(value = "/monsterRace/{race}")
    public Object getRaceCardMonster(@PathVariable String race) throws IOException, JSONException {
        return cardService.FindMonsterCard(null, race);
    }

    @GetMapping(value = "/monster/{type}/{race}")
    public Object getMonster(@PathVariable String type,@PathVariable String race) throws IOException, JSONException {
        return cardService.FindMonsterCard(type, race);
    }
}
