package com.borges.api_complete.Model;

import java.util.List;
import java.util.Objects;

public class CardModel {

    private int id;
    private String name_en;
    private String name;
    private String type;
    private String desc;
    private int atk;
    private int def;
    private String level;
    private String race;
    private String attribute;
    private String archetype;
    private String scale;
    private String linkval;
    private String linkmarkers;
    private String price;
    private List card_images;

    public CardModel() {
    }

    public CardModel(int id, String name_en, String name, String type, String desc, int atk, int def, String level, String race, String attribute, String archetype, String scale, String linkval, String linkmarkers, String price, List card_images) {
        this.id = id;
        this.name_en = name_en;
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.atk = atk;
        this.def = def;
        this.level = level;
        this.race = race;
        this.attribute = attribute;
        this.archetype = archetype;
        this.scale = scale;
        this.linkval = linkval;
        this.linkmarkers = linkmarkers;
        this.price = price;
        this.card_images = card_images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getArchetype() {
        return archetype;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLinkval() {
        return linkval;
    }

    public void setLinkval(String linkval) {
        this.linkval = linkval;
    }

    public String getLinkmarkers() {
        return linkmarkers;
    }

    public void setLinkmarkers(String linkmarkers) {
        this.linkmarkers = linkmarkers;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List getcard_images() {
        return card_images;
    }

    public void setcard_images(List card_images) {
        this.card_images = card_images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardModel cardModel = (CardModel) o;
        return id == cardModel.id &&
                atk == cardModel.atk &&
                def == cardModel.def &&
                Objects.equals(name_en, cardModel.name_en) &&
                Objects.equals(name, cardModel.name) &&
                Objects.equals(type, cardModel.type) &&
                Objects.equals(desc, cardModel.desc) &&
                Objects.equals(level, cardModel.level) &&
                Objects.equals(race, cardModel.race) &&
                Objects.equals(attribute, cardModel.attribute) &&
                Objects.equals(archetype, cardModel.archetype) &&
                Objects.equals(scale, cardModel.scale) &&
                Objects.equals(linkval, cardModel.linkval) &&
                Objects.equals(linkmarkers, cardModel.linkmarkers)&&
                 Objects.equals(price, cardModel.price)&&
                Objects.equals(card_images, cardModel.card_images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name_en, name, type, desc, atk, def, level, race, attribute, archetype, scale, linkval, linkmarkers, price, card_images);
    }

    @Override
    public String toString() {
        return "CardModel{" +
                "id=" + id +
                ", name_en='" + name_en + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", atk=" + atk +
                ", def=" + def +
                ", level='" + level + '\'' +
                ", race='" + race + '\'' +
                ", attribute='" + attribute + '\'' +
                ", archetype='" + archetype + '\'' +
                ", scale='" + scale + '\'' +
                ", linkval='" + linkval + '\'' +
                ", linkmarkers='" + linkmarkers + '\'' +
                ", price='" + price + '\'' +
                "img = '"+card_images+'\''+
                '}';
    }
}
