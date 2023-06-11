package hwr.oop.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Topic {

    private final String name;
    private List<Card> cardList;

    public Topic(@JsonProperty("name") String name){
        this.name = name;
        cardList = new ArrayList<>();
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void createCard(String question, String answer){
        int id = cardList.size();
        Card newCard = new Card(question, answer, id);
        cardList.add(newCard);
    }
    @JsonIgnore
    public boolean deleteCard(Card card){
        return cardList.remove(card);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic topic)) return false;
        if (cardList.size() != topic.cardList.size()) {
            return false;
        }
        for (int current = 0; current < cardList.size(); current++) {
            boolean notequal = !cardList.get(current).equals(topic.cardList.get(current));
            if (notequal) {
                return false;
            }
        }
        return Objects.equals(name, topic.name);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, cardList);
    }
}
