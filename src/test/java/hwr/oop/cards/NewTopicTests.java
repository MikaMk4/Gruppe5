package hwr.oop.cards;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NewTopicTests {

    @Test
    public void canCreateTopicWithName(){

        Topic topic = new Topic("Portugiesisch");

        String testName = topic.getName();
        assertThat(testName).isEqualTo("Portugiesisch");
    }
    @Test
    public void equalTopicSameObject(){
        Topic topic1 = new Topic("Spanisch");
        assertThat(topic1).isEqualTo(topic1);
    }
    @Test
    public void equalTopics(){
        Topic topic1 = new Topic("Spanisch");
        Topic topic2 = new Topic("Spanisch");
        assertThat(topic1).isEqualTo(topic2);
    }
    @Test
    public void notEqualTopics(){
        Topic topic1 = new Topic("Spanisch");
        Topic topic2 = new Topic("Portugisisch");
        assertThat(topic1).isNotEqualTo(topic2);
    }
    @Test
    public void notEqualTopicWithDifferentObject(){
        Topic topic1 = new Topic("Spanisch");
        Card card = new Card("Karte?", "Ja", 1);
        assertThat(topic1).isNotEqualTo(card);
    }

    @Test
    void cardDeleteTest(){
        Topic topic = new Topic("Englisch");
        topic.createCard("Hallo?", "nein");
        var testBool = topic.deleteCard("Hallo?");

        assertThat(testBool).isTrue();
    }

    @Test
    void cardCannotBeDeleted(){
        Topic topic = new Topic("Englisch");
        topic.createCard("Hallo?", "nein");
        var testBool = topic.deleteCard("iaufhiasf?");

        assertThat(testBool).isFalse();
    }
}
