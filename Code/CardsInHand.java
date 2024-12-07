import java.util.ArrayList;

public class CardsInHand {
    private int maxSize;
    public ArrayList<Card> handCards;

    public CardsInHand(int maxSize) {
        this.maxSize = maxSize;
        handCards = new ArrayList<>();
    }

    public boolean addCard(Card card) {
        if (handCards.size() >= maxSize) return false;

        handCards.add(card);
        return true;
    }

    public int getSize(){
        return handCards.size();
    }

    public void clearHandCards(){
        handCards.clear();
    }

    public Card getCard(int index){
        return handCards.get(index);
    }

    public ArrayList<Card> getHandCards(){
        return handCards;
    }



}
