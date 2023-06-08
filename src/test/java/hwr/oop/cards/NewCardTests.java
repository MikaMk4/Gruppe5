package hwr.oop.cards;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class NewCardTests {


    @Test
    void testEquals(){

        Card card1 = new Card("1", "2", 3);
        Card card2 = new Card("1", "2", 3);
        assertThat(card1).isEqualTo(card2);
    }

    @Test
    void testEqualsNot(){

        Card card1 = new Card("1", "2", 3);
        Card card2 = new Card("2", "1", 3);
        assertThat(card1).isNotEqualTo(card2);
    }
    @Test
    void testEqualsNotOtherClass(){
        Card card = new Card("1", "2", 1);
        Topic topic = new Topic("Spanisch");
        assertThat(card.equals(topic)).isFalse();
    }
    @Test
    void canCreateCard() {

        Topic topic = new Topic("Spanisch");

        topic.createCard("Is 30 dollars too much for Cyberpunk?", "Yes");

        Card testCard = topic.getCardList().get(0);
        assertThat(testCard).isNotNull();
    }
    @Test
    void canDeleteCard(){
        Topic topic = new Topic("Spanisch");
        topic.createCard("Tisch", "table");
        topic.deleteCard(topic.getCardList().get(0));
        assertThat(topic.getCardList()).isEmpty();
    }
    @Test
    void cannotDeleteCard(){
        Topic topic = new Topic("Spanisch");
        Card card = new Card("Fischkutter", "fishcutter", 1);
        assertThat(topic.deleteCard(card)).isFalse();
    }

    @Test
    void canGetQuestion(){

        Card card = new Card("What was the first item to be sold on Ebay?", "A broken laser pointer", 0);

        String testQuestion = card.getQuestion();
        assertThat(testQuestion).isEqualTo("What was the first item to be sold on Ebay?");
    }
    @Test
    void canGetAnswer(){

        Card card = new Card("What is the largest living organism on earth?", "The Great Barrier Reef", 0);

        String testQuestion = card.getAnswer();
        assertThat(testQuestion).isEqualTo("The Great Barrier Reef");
    }

    @Test
    void canGetId(){

        Card card = new Card("Test?", "Ja!", 42);

        int id = card.getId();
        assertThat(id).isEqualTo(42);
    }
    @Test
    void canGetLastLearned(){
        Card card = new Card("Test?", "Ja!", 42);
        LocalDate date = LocalDate.now();
        assertThat(date).isEqualTo(card.getLastLearned());
    }
    @Test
    void canEditCard(){
        Lernsession lernsession = Lernsession.createLernsessionWith3Boxes();
        Topic topic = new Topic("Spanisch");
        topic.createCard("Soja", "beste!!!");
        lernsession.loadTopic(topic);
        Card card = lernsession.getRandomCard();
        card.edit("Tofu", "auch Beste");
        assertThat(topic.getCardList().get(0).getQuestion()).isEqualTo("Tofu");
    }
    @Test
    void canUpdateLastLearned(){
        NewPersistenceLoadPort pa = new NewJsonPersistenceAdapter();
        Topic topic;
        topic = pa.loadTopic("oldCard");

        Card card = topic.getCardList().get(0);
        LocalDate oldLastLearned = card.getLastLearned();
        card.updateLastLearned();
        LocalDate newLastLearned = card.getLastLearned();
        assertThat(oldLastLearned).isNotEqualTo(newLastLearned);
        assertThat(newLastLearned).isEqualTo(LocalDate.now());
    }
}
