package hwr.oop.cards;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;

class NewTopicTests {

    @Test
    void canCreateTopicWithName(){

        Topic topic = new Topic("Portugiesisch");

        String testName = topic.getName();
        assertThat(testName).isEqualTo("Portugiesisch");
    }
    @Test
    void equalTopicSameObject(){
        Topic topic1 = new Topic("Spanisch");
        assertThat(topic1.equals(topic1)).isTrue();
    }
    @Test
    void equalTopics(){
        Topic topic1 = new Topic("Spanisch");
        Topic topic2 = new Topic("Spanisch");
        assertThat(topic1.equals(topic2)).isTrue();
    }

    @Test
    void notEqualTopics(){
        Topic topic1 = new Topic("Spanisch");
        Topic topic2 = new Topic("Portugisisch");
        assertThat(topic1.equals(topic2)).isFalse();
    }

    @Test
    void notEqualTopicsWithDifferentCardLists() {
        Topic topic1 = new Topic("Spanisch");
        topic1.createCard("Question 1", "Answer 1");

        Topic topic2 = new Topic("Spanisch");
        topic2.createCard("Question 2", "Answer 2");

        assertThat(topic1.equals(topic2)).isFalse();
    }

    @Test
    void notEqualTopicAndCard() {
        Topic topic1 = new Topic("Topic 1");
        Card topic2 = new Card(" Bob", "der Baumeeister", 1);

        assertThat(topic1.equals(topic2)).isFalse();
    }

}
