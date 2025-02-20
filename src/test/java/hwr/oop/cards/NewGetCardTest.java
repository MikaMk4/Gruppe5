package hwr.oop.cards;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class NewGetCardTest {
    private Lernsession lernsession;
    private Topic topic;
    @BeforeEach
    void setup(){
        lernsession = Lernsession.createLernsessionWith3Boxes();
        topic = new Topic("Random");
        topic.createCard("Penny", "Doku");
        topic.createCard("escalera", "Leiter");
        topic.createCard("caballo", "Pferd");
        lernsession.loadTopic(topic);
    }
    @Test
    void canGetRandomBoxIndex(){
        boolean checkIndexZero = false;
        boolean checkIndexOne = false;
        boolean checkIndexTwo = false;
        int breakLoopIfStuck = 0;

        while (!checkIndexZero || !checkIndexOne || !checkIndexTwo) {
            int randomBoxIndex = lernsession.getRandomBoxIndex();
            breakLoopIfStuck++;


            if (randomBoxIndex == 0) {
                checkIndexZero = true;
            }

            if (randomBoxIndex == 1) {
                checkIndexOne = true;
            }

            if (randomBoxIndex == 2) {
                checkIndexTwo = true;
            }

            if (randomBoxIndex < 0 || randomBoxIndex > 2 || breakLoopIfStuck > 20) {
                break;
            }
        }

        assertThat(checkIndexZero).isTrue();
        assertThat(checkIndexOne).isTrue();
        assertThat(checkIndexTwo).isTrue();



    }
    @Test
    void canGetRandomBoxIndexFromList(){
        int randomBoxIndex = lernsession.getRandomBoxIndexFromList(List.of(1, 2, 3));
        Assertions.assertThat(randomBoxIndex).isIn(List.of(1, 2, 3));
    }
    @Test
    void canGetRandomCardFromRandomBox(){
        Card card = lernsession.getRandomCard();
        Assertions.assertThat(card).isIn(topic.getCardList());
    }
    @Test
    void cannotReturnRandomCardFromEmptyLernsession(){
        Lernsession lernsession1 = Lernsession.createLernsessionWith3Boxes();
        assertThrows(Lernsession.EmptyBoxesException.class, lernsession1::getRandomCard);
    }

    @Test
    void canReturnRandomCard(){

        Boxes mediator = Boxes.createBoxes(3);
        NewBox box = mediator.retrieve(0).get();
        Card card1 = new Card("What is the smallest mammal in the world?", "The bumblebee bat.", 0);
        Card card2 = new Card("What is the highest-grossing movie of all time?", "Avatar, which grossed over $2.7 billion worldwide.", 1);
        Card card3 = new Card("What is the longest word in the English language?", "Pneumonoultramicroscopicsilicovolcanoconiosis", 2);

        box.addCard(card1);
        box.addCard(card2);
        box.addCard(card3);

        Card testCard = box.getRandomCard();

        if (!testCard.equals(card1) && !testCard.equals(card2) && !testCard.equals(card3)){
            fail("Drawn card does not equal one of the test cards.");
        }
    }

}