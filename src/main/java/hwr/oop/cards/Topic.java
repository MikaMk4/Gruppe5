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
    public boolean deleteCard(String question){
        Card card = searchCardByQuestion(question);

        if (card == null){
            return false;
        } else {
            cardList.remove(card);
            return true;
        }
    }

    @JsonIgnore
    private Card searchCardByQuestion(String question){
        for (Card card : cardList){
            if (card.getQuestion().equals(question)){
                return card;
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic)) return false;
        Topic topic = (Topic) o;
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
}
