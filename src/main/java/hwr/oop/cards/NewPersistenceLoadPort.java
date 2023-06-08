package hwr.oop.cards;

import java.io.IOException;
import java.util.Collection;

public interface NewPersistenceLoadPort {
    Topic loadTopic(String persistenceInstanceName);

    Collection<NewBox> loadLernsession(String persistenceInstanceName);
}
