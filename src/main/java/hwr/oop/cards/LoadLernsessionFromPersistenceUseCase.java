package hwr.oop.cards;

import java.util.List;

public class LoadLernsessionFromPersistenceUseCase {
    private final NewPersistenceLoadPort persistenceLoadPort;

    public LoadLernsessionFromPersistenceUseCase(NewPersistenceLoadPort persistenceLoadPort){
        this.persistenceLoadPort = persistenceLoadPort;
    }

    public Lernsession loadLernsession(String filename) {
        Lernsession lernsession;

        List<NewBox> boxList;
        boxList = (List<NewBox>)persistenceLoadPort.loadLernsession(filename);
        Boxes boxes = new Boxes(boxList);
        lernsession = Lernsession.createLernsessionFromBoxes(boxes);
        return lernsession;
    }
}
