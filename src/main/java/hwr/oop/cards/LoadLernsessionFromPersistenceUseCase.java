package hwr.oop.cards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadLernsessionFromPersistenceUseCase {
    private final NewPersistenceLoadPort persistenceLoadPort;

    public LoadLernsessionFromPersistenceUseCase(NewPersistenceLoadPort persistenceLoadPort){
        this.persistenceLoadPort = persistenceLoadPort;
    }

    public Lernsession loadLernsession(String filename) {
        Lernsession lernsession;

        List<NewBox> boxList = null;
        boxList = (List)persistenceLoadPort.loadLernsession(filename);
        Boxes boxes = new Boxes(boxList);
        lernsession = Lernsession.createLernsessionFromBoxes(boxes);
        return lernsession;
    }
}
