package com.borges.api_complete.Model.DTO;

import com.borges.api_complete.Model.CardModel;
import com.borges.api_complete.Model.UserModel;

import java.util.List;
import java.util.Objects;

public class CardDTO {
    private String id;
    private UserModel user;
    private List<CardModel> card;


    public CardDTO() {
    }

    public CardDTO(String id, UserModel user, List<CardModel> card) {
        this.id = id;
        this.user = user;
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<CardModel> getCard() {
        return card;
    }

    public void setCard(List<CardModel> card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDTO cardDTO = (CardDTO) o;
        return Objects.equals(id, cardDTO.id) &&
                Objects.equals(user, cardDTO.user) &&
                Objects.equals(card, cardDTO.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, card);
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", card=" + card +
                '}';
    }
}

