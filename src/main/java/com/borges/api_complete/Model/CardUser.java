package com.borges.api_complete.Model;

import java.util.Objects;

public class CardUser {

    private String id;
    private String user_email;
    private CardModel card;

    public CardUser() {
    }

    public CardUser(String id, String user_email, CardModel card) {
        this.id = id;
        this.user_email = user_email;
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public CardModel getCard() {
        return card;
    }

    public void setCard(CardModel card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardUser cardUser = (CardUser) o;
        return Objects.equals(id, cardUser.id) &&
                Objects.equals(user_email, cardUser.user_email) &&
                Objects.equals(card, cardUser.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_email, card);
    }

    @Override
    public String toString() {
        return "CardUser{" +
                "id='" + id + '\'' +
                ", user_email='" + user_email + '\'' +
                ", card=" + card +
                '}';
    }
}
