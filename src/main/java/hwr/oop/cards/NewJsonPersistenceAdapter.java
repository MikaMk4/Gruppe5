package hwr.oop.cards;

import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class NewJsonPersistenceAdapter implements NewPersistenceSavePort, NewPersistenceLoadPort {

    static final String EXCEPTION_TEXT = "persistenceInstanceName should not be empty.";

    public void saveLernsession(Collection<NewBox> boxen, String persistenceInstanceName) {

        if (persistenceInstanceName.isEmpty()){

            throw new IllegalArgumentException(EXCEPTION_TEXT);
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(new File(persistenceInstanceName), boxen);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Topic loadTopic(String persistenceInstanceName) {

        if (persistenceInstanceName.isEmpty()){

            throw new IllegalArgumentException(EXCEPTION_TEXT);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(persistenceInstanceName))) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json = reader.readLine();
            return mapper.readValue(json, new TypeReference<>() {
            });
        } catch (IOException e){
            throw new PersistenceException(e);
        }
    }

    public Collection<NewBox> loadLernsession(String persistenceInstanceName) {

        if (persistenceInstanceName.isEmpty()){

            throw new IllegalArgumentException(EXCEPTION_TEXT);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TypeReference<List<NewBox>> typeReference = new TypeReference<List<NewBox>>(){};
        List<NewBox> boxes = null;
        try {
            boxes = mapper.readValue(new File(persistenceInstanceName), typeReference);
        } catch (IOException e) {
            throw new PersistenceException(e);
        }

        Map<Integer, Card> cards = new HashMap<>();
        for (NewBox box : boxes) {
            for (Card card : box.getLearnedCardList()) {
                cards.put(card.getId(), card);
            }
            for (Card card : box.getUnlearnedCardList()) {
                cards.put(card.getId(), card);
            }
        }

        List<NewBox> result = new ArrayList<>();
        for (NewBox box : boxes) {
            ArrayList<Card> learnedCardList = new ArrayList<>();
            ArrayList<Card> notLearnedCardList = new ArrayList<>();
            for (Card card : box.getLearnedCardList()) {
                learnedCardList.add(cards.get(card.getId()));
            }
            for (Card card : box.getUnlearnedCardList()) {
                notLearnedCardList.add(cards.get(card.getId()));
            }
            int learnInterval = box.getLearnInterval();
            int previousBox = box.getPrevious();
            int nextBox = box.getNext();
            result.add(new NewBox(learnedCardList, notLearnedCardList, learnInterval, null, nextBox, previousBox ));
        }

        return boxes;
    }
    @Override
    public void saveTopic(Topic topic, String persistenceInstanceName) {

        if (persistenceInstanceName.isEmpty()){

            throw new IllegalArgumentException(EXCEPTION_TEXT);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(persistenceInstanceName))) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(topic);
            writer.write(json);
        } catch (IOException e){
            throw new PersistenceException(e);
        }
    }
}
