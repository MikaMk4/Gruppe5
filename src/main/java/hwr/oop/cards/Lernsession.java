package hwr.oop.cards;

import java.util.*;

public class Lernsession {
    private final Boxes mediator;
    private final int numberOfBoxes;
    private Random random = new Random();

    // saving Boxes reference or reference to the box
    public static Lernsession createLernsessionWith3Boxes(){
        Boxes boxes = Boxes.createBoxes(3);
        return new Lernsession(boxes);
    }
    public static Lernsession createLernsessionWith5Boxes(){
        Boxes boxes = Boxes.createBoxes(5);
        return new Lernsession(boxes);
    }public static Lernsession createLernsessionWith7Boxes(){
        Boxes boxes = Boxes.createBoxes(7);
        return new Lernsession(boxes);
    }
    public static Lernsession createLernsessionFromBoxes(Boxes boxes){
        return new Lernsession(boxes);
    }

    private Lernsession(Boxes mediator) {
        this.mediator = mediator;
        this.numberOfBoxes = mediator.getBoxAmount();
    }

    public Boxes getBoxes() {
        return mediator;
    }

    public int getRandomBoxIndex() {
        return random.nextInt(numberOfBoxes - 1);

    }
    public int getRandomBoxIndexFromList(List<Integer> indexList){
        return indexList.get(random.nextInt(indexList.size()));
    }

    public Card getRandomCard(){
        NewBox box;
        List<Integer> indexList = new ArrayList<>();
        for(int current = 0; current < numberOfBoxes; current++){
            indexList.add(current);
        }
        while (!indexList.isEmpty()) {
            Integer index = getRandomBoxIndexFromList(indexList); //keine int, da bei remove sonst index statt Object
            indexList.remove(index);
            box = mediator.retrieve(index).get();
            if (!box.isEmptyLearned()) { //eigentlich unlearned,aber für Test so
                return box.getRandomCard();
            }
        }
        throw new EmptyBoxesException();
    }

    // loading new Topic
    void loadTopic(Topic topic) {
        List<Card> cardList = topic.getCardList();
        NewBox box = mediator.retrieve(0).get();
        for (Card card : cardList) {
            box.addCard(card);
        }
    }
    public static class EmptyBoxesException extends RuntimeException{
        public EmptyBoxesException(){
            super ("All Boxes are empty!");
        }
    }
}
