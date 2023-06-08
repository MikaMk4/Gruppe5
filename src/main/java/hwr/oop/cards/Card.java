package hwr.oop.cards;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {
    private final int id;
    private String question;
    private String answer;
    private LocalDate lastLearned;

    public Card(@JsonProperty("question") String question, @JsonProperty("answer") String answer, @JsonProperty("id") int id) {

        this.question = question;
        this.answer = answer;
        this.id = id;
        this.lastLearned = LocalDate.now();
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Card)) {
            return false;
        }
        Card other = (Card) obj;
        return Objects.equals(question, other.question) && Objects.equals(answer, other.answer) && Objects.equals(id, other.id);
    }
    @JsonIgnore
    public void edit(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public int getId() { return id; }

    public LocalDate getLastLearned() {
        return lastLearned;
    }
    @JsonIgnore
    public void updateLastLearned(){
        lastLearned = LocalDate.now();
    }
}
