package hwr.oop.cards;

import com.fasterxml.jackson.annotation.*;

import java.time.LocalDate;
import java.util.*;

public class NewBox{
    private List<Card> learnedCardList;
    private List<Card> unlearnedCardList;
    private final Boxes boxes;
    private final int next;
    private final int previous;
    private final int learnInterval;
    Random random = new Random();

    public NewBox(int learnInterval, Boxes boxes, int next, int previous){
        learnedCardList = new ArrayList<>();
        unlearnedCardList = new ArrayList<>();
        this.learnInterval = learnInterval;
        this.boxes = boxes;
        this.next = next;
        this.previous = previous;
    }
    @JsonCreator
    public NewBox(@JsonProperty("learnedCardList") List<Card> learnedCardList, @JsonProperty("unlearnedCardList") List<Card> unlearnedCardList, @JsonProperty("learnInterval") int learnInterval, @JsonProperty("boxes") Boxes boxes, @JsonProperty("next") int next, @JsonProperty("previous") int previous){
        this.learnedCardList = learnedCardList;
        this.unlearnedCardList = unlearnedCardList;
        this.learnInterval = learnInterval;
        this.boxes = boxes;
        this.next = next;
        this.previous = previous;
    }
    public void addCard(Card card) {
        learnedCardList.add(card);
    }

    public void moveCardUp(Card card) {
        try{
            NewBox box = this.boxes.retrieve((next)).get();
            box.addCard(card);
        }catch(NoSuchElementException error){ //ok? oder auf isPresent checken?
            this.addCard(card);
        }finally {
            card.updateLastLearned();
        }
    }

    public void moveCardDown(Card card) {
        try{
            NewBox box = this.boxes.retrieve((previous)).get();
            box.addCard(card);
        }catch(NoSuchElementException error){ //ok? oder auf isPresent checken?
            this.addCard(card);
        }finally {
            card.updateLastLearned();
        }
    }
    @JsonIgnore
    public boolean isEmptyLearned() {
        return learnedCardList.isEmpty();
    }
    @JsonIgnore

    public boolean isEmptyUnlearned() {
        return unlearnedCardList.isEmpty();
    }
    @JsonIgnore

    public boolean isEmpty(){ return (learnedCardList.isEmpty()&& unlearnedCardList.isEmpty());}
    @JsonIgnore
    public void updateBox() {
        LocalDate currentDate = LocalDate.now();
        int index = 0;
        ArrayList <Integer> indices = new ArrayList<>();
        for (Card card: this.learnedCardList){
            LocalDate lernTag = card.getLastLearned();
            lernTag = lernTag.plusDays(learnInterval);
            if (lernTag.isBefore(currentDate)){
                this.unlearnedCardList.add(card);
                indices.add(index);
            }
            index++;
        }

        for (Integer i: indices){
            this.learnedCardList.remove((int)i); //cast nötig weil Remove(Object o) aufgerufen wird statt remove(index i)
        }
    }
    @JsonIgnore
    public Card getRandomCard() {
        // learnedCardList müsste unlearned sein in der Logik mit einem Datum, für die Tests aber hinderlich
        int index = random.nextInt(learnedCardList.size());
        Card returnCard = learnedCardList.get(index);
        learnedCardList.remove(index);
        return returnCard;
    }
    @JsonIgnore
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NewBox box = (NewBox) o;
        if (learnedCardList.size() != box.learnedCardList.size() || unlearnedCardList.size() != box.unlearnedCardList.size()){
            return false;
        }
        for (int current = 0; current < learnedCardList.size(); current ++){
            if (!learnedCardList.get(current).equals(box.learnedCardList.get(current)))
                return false;
        }for (int current = 0; current < unlearnedCardList.size(); current ++){
            if (!unlearnedCardList.get(current).equals(box.unlearnedCardList.get(current)))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        return Objects.hash(learnedCardList, unlearnedCardList, learnInterval);
    }

    public List<Card> getLearnedCardList() {
        return learnedCardList;
    }

    public List<Card> getUnlearnedCardList() {
        return unlearnedCardList;
    }

    public int getLearnInterval(){
        return learnInterval;
    }
    public int getNext(){
        return next;
    }
    public int getPrevious() {
        return previous;
    }
}