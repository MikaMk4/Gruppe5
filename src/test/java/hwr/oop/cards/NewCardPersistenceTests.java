package hwr.oop.cards;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewCardPersistenceTests {

    @Nested
    class emptyPersistenceInstanceNameThrowsExceptionTests{

        @Test
        void load(){

            NewJsonPersistenceAdapter pa = new NewJsonPersistenceAdapter();

            assertThrows(IllegalArgumentException.class, () -> pa.loadTopic(""));
        }

        @Test
        void save(){

            NewJsonPersistenceAdapter pa = new NewJsonPersistenceAdapter();

            Topic topic = new Topic("testTopic");
            topic.createCard("", "");
            assertThrows(IllegalArgumentException.class, () -> pa.saveTopic(topic, ""));
        }
    }

    @Nested
    class saveCardTests{

        @Test
         void canSaveSingleCard(){

            NewPersistenceSavePort persistenceSavePort = new NewJsonPersistenceAdapter();
            NewPersistenceLoadPort persistenceLoadPort = new NewJsonPersistenceAdapter();

            Topic topic = new Topic("testTopic");
            topic.createCard("Question?", "Answer!");
            persistenceSavePort.saveTopic(topic, "test");

            Topic testTopic;
            testTopic = persistenceLoadPort.loadTopic("test");

            Assertions.assertThat(testTopic).isEqualTo(topic);
        }

        @Test
         void canSaveMultipleCards(){

            NewPersistenceSavePort persistenceSavePort = new NewJsonPersistenceAdapter();
            NewPersistenceLoadPort persistenceLoadPort = new NewJsonPersistenceAdapter();

            Topic topic = new Topic("testTopic");
            topic.createCard("Question?", "Answer!");
            topic.createCard("Frage?", "Antwort!");
            persistenceSavePort.saveTopic(topic, "test");

            Topic testTopic;
            testTopic = persistenceLoadPort.loadTopic("test");

            Assertions.assertThat(testTopic).isEqualTo(topic);
        }

        @Test void makeSureToOverwritePreviousSaves(){

            NewPersistenceSavePort persistenceSavePort = new NewJsonPersistenceAdapter();
            NewPersistenceLoadPort persistenceLoadPort = new NewJsonPersistenceAdapter();

            Topic topic = new Topic("testTopic");
            topic.createCard("Question?", "Answer!");
            persistenceSavePort.saveTopic(topic, "test");

            Topic topic1 = new Topic("testTopic");
            topic.createCard("Question??????", "Answer!!!!!!");
            persistenceSavePort.saveTopic(topic1, "test");

            Topic testTopic;
            testTopic = persistenceLoadPort.loadTopic("test");

            Assertions.assertThat(testTopic).isEqualTo(topic1);
        }
    }

    @Nested
    class loadCardTests{
        Topic savedTopic;
        @BeforeEach
        void setUp(){
            NewPersistenceSavePort pa = new NewJsonPersistenceAdapter();

            savedTopic = new Topic("testTopic");
            savedTopic.createCard("Question?", "Answer!");
            savedTopic.createCard("Frage?", "Antwort!");
            pa.saveTopic(savedTopic, "test");
        }

        @Test
        void canLoadSavedContent(){

            NewPersistenceLoadPort pa = new NewJsonPersistenceAdapter();

            Topic loadedTopic;
            loadedTopic = pa.loadTopic("test");

            Assertions.assertThat(loadedTopic).isEqualTo(savedTopic);
        }
    }
}
