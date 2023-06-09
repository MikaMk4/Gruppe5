package hwr.oop.cards;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateNewBoxTest {
    @Test
    void cantLoadEmptyBoxes(){
        List<NewBox> emptyBoxList = new ArrayList<>();
        assertThrows(IllegalStateException.class, () -> new Boxes(emptyBoxList));
    }
    @Test
     void canContainCards(){
        Boxes boxes = Boxes.createBoxes(3);
        Card card = new Card(" Bob", "der Baumeeister", 1);
        NewBox box = boxes.retrieve(1).get(); //no checking for isPresent()
        box.addCard(card);
        Assertions.assertThat(box.isEmptyLearned()).isFalse();
    }
    @Test
    void testEquals(){
        Boxes mediator = Boxes.createBoxes(3);
        NewBox box1 = mediator.retrieve(0).get();
        NewBox box2 = mediator.retrieve(0).get();
        Card card = new Card("1", "2", 3);
        box1.addCard(card);
        box2.addCard(card);
        assertThat(box1).isEqualTo(box2);
    }
    @Test
    void testEqualsNot(){
        Boxes mediator = Boxes.createBoxes(3);
        NewBox box1 = mediator.retrieve(0).get();
        NewBox box2 = mediator.retrieve(1).get();
        Card card = new Card("1", "2", 3);
        Card card2 = new Card("blabal", "2", 4);
        box1.addCard(card);
        box2.addCard(card2);
        assertThat(box1).isNotEqualTo(box2);
    }

    @Test
    void unlearnedCardListIsEmpty(){
        Boxes boxes = Boxes.createBoxes(3);
        NewBox box = boxes.retrieve(1).get();
        boolean isEmpty = box.isEmptyUnlearned();
        assertThat(isEmpty).isTrue();
    }
    @Test
    void unlearnedCardListIsNotEmpty(){
        NewPersistenceLoadPort pa = new NewJsonPersistenceAdapter();
        Topic topic;
        topic = pa.loadTopic("oldCard");

        Card card = topic.getCardList().get(0);
        Boxes boxes = Boxes.createBoxes(3);
        NewBox box = boxes.retrieve(0).get();
        box.addCard(card); //landet in learned
        box.updateBox();
        boolean isEmpty = box.isEmptyUnlearned();
        assertThat(isEmpty).isFalse();
    }
    @Test
    void learnedCardListIsEmpty(){
        Boxes boxes = Boxes.createBoxes(3);
        NewBox box = boxes.retrieve(1).get();
        boolean isEmpty = box.isEmptyLearned();
        assertThat(isEmpty).isTrue();
    }
    @Test
    void learnedCardListIsNotEmpty(){
        Card card = new Card("Test", "Frage", 0);
        Boxes boxes = Boxes.createBoxes(3);
        NewBox box = boxes.retrieve(1).get();
        box.addCard(card);
        boolean isEmpty = box.isEmptyLearned();
        assertThat(isEmpty).isFalse();
    }
    @Test
    void canUpdateBox(){
        NewPersistenceLoadPort pa = new NewJsonPersistenceAdapter();
        Topic topic;
        topic = pa.loadTopic("oldCard");

        Card card = topic.getCardList().get(0);
        Boxes boxes = Boxes.createBoxes(3);
        NewBox box = boxes.retrieve(0).get();
        box.addCard(card); //landet in learned
        box.updateBox();
        boolean isEmptyUnlearned = box.isEmptyUnlearned();
        assertThat(box.isEmptyLearned()).isTrue();
        assertThat(isEmptyUnlearned).isFalse();
    }

    @Test
    void lastLearnedIsUpdatedAfterMovingCardUp(){
        NewPersistenceLoadPort pa = new NewJsonPersistenceAdapter();
        Topic topic;
        topic = pa.loadTopic("oldCard");
        Card card = topic.getCardList().get(0);
        LocalDate oldCompDate = card.getLastLearned();
        Boxes boxes = Boxes.createBoxes(3);
        NewBox box = boxes.retrieve(0).get();
        box.addCard(card); //landet in learned
        box.moveCardUp(card);
        NewBox box2 = boxes.retrieve(1).get();
        LocalDate compDate = box2.getRandomCard().getLastLearned();
        assertThat(compDate).isNotEqualTo(oldCompDate);
        assertThat(compDate).isEqualTo(LocalDate.now());
    }@Test
    void lastLearnedIsUpdatedAfterMovingCardDown(){
        NewPersistenceLoadPort pa = new NewJsonPersistenceAdapter();
        Topic topic;
        topic = pa.loadTopic("oldCard");
        Card card = topic.getCardList().get(0);
        LocalDate oldCompDate = card.getLastLearned();
        Boxes boxes = Boxes.createBoxes(3);
        NewBox box2 = boxes.retrieve(1).get();
        box2.addCard(card); //landet in learned
        box2.moveCardDown(card);
        NewBox box1 = boxes.retrieve(0).get();
        LocalDate compDate = box1.getRandomCard().getLastLearned();
        assertThat(compDate).isNotEqualTo(oldCompDate);
        assertThat(compDate).isEqualTo(LocalDate.now());
    }

    @Test
    void boxIsEmptyAfterDrawingAllCards(){
        Boxes boxes = Boxes.createBoxes(3);
        NewBox box = boxes.retrieve(1).get();
        Card card1 = new Card("What is the smallest mammal in the world?", "The bumblebee bat.", 0);
        Card card2 = new Card("What is the highest-grossing movie of all time?", "Avatar, which grossed over $2.7 billion worldwide.", 1);
        Card card3 = new Card("What is the longest word in the English language?", "Pneumonoultramicroscopicsilicovolcanoconiosis", 2);

        box.addCard(card1);
        box.addCard(card2);
        box.addCard(card3);

        box.getRandomCard();
        box.getRandomCard();
        box.getRandomCard();

        assertThat(box.isEmpty()).isTrue();
    }
    @Test
    void canGetLearnInterval(){
        Boxes mediator = Boxes.createBoxes(3);
        NewBox box1 = mediator.retrieve(0).get();
        NewBox box2 = mediator.retrieve(1).get();
        NewBox box3 = mediator.retrieve(2).get();
        assertThat(box1.getLearnInterval()).isEqualTo(1);
        assertThat(box2.getLearnInterval()).isEqualTo(3);
        assertThat(box3.getLearnInterval()).isEqualTo(7);
    }
    @Test
    void canGetNextBox(){
        Boxes mediator = Boxes.createBoxes(3);
        NewBox compBox1 = mediator.retrieve(0).get();
        NewBox compBox2 = mediator.retrieve(1).get();
        NewBox compBox3 = mediator.retrieve(2).get();
        NewBox box2 = mediator.retrieve(compBox1.getNext()).get();
        NewBox box3 = mediator.retrieve(compBox2.getNext()).get();
        assertThat(box2).isEqualTo(compBox2);
        assertThat(box3).isEqualTo(compBox3);
        Optional opt = mediator.retrieve(compBox3.getNext());
        assertThrows(NoSuchElementException.class, () -> opt.get());
    }
    @Test
    void canGetPreviousBox(){
        Boxes mediator = Boxes.createBoxes(3);
        NewBox compBox1 = mediator.retrieve(0).get();
        NewBox compBox2 = mediator.retrieve(1).get();
        NewBox compBox3 = mediator.retrieve(2).get();
        NewBox box2 = mediator.retrieve(compBox3.getPrevious()).get();
        NewBox box1 = mediator.retrieve(compBox2.getPrevious()).get();
        assertThat(box2).isEqualTo(compBox2);
        assertThat(box1).isEqualTo(compBox1);
        Optional opt = mediator.retrieve(compBox1.getPrevious());
        assertThrows(NoSuchElementException.class, () -> opt.get());
    }
}

