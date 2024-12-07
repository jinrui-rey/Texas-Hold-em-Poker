import java.util.*;

public class PokerHand extends CardsInHand{

    private int clubs;
    private int spades;
    private int hearts;
    private int diamonds;

    private boolean Straight;
    private boolean Flush;
    private boolean ThreeKind;
    private boolean FullHouse;
    private boolean Pair;
    private boolean TwoPair;
    private boolean FourKind;
    private boolean StraightFlush;
    private boolean High = false;


    public PokerHand() {
        super(7);
    }

    public void isFlush(){

        clubs=0;
        spades=0;
        hearts=0;
        diamonds=0;
        Flush=false;

        for (Card handCard : handCards) {
            String suit = String.valueOf(handCard.getSuit()); // Get the suit of the card
            switch (suit) {
                case "Clubs":
                    clubs++;
                    break;
                case "Spades":
                    spades++;
                    break;
                case "Hearts":
                    hearts++;
                    break;
                case "Diamonds":
                    diamonds++;
                    break;
            }
        }

        if((clubs >= 5) || (spades >= 5) || (hearts >= 5) || (diamonds >= 5))
            Flush = true;

//        System.out.println("Flush:"+Flush);

    }

    public void isPairRelated(){

        Pair = TwoPair = ThreeKind = FullHouse = FourKind = StraightFlush = false;


        for(int i = 1; i<=14;i++){
            int count = 0;
            for(Card card : handCards){
                if(card.getCardValue() == i)
                    count++;
            }

            if (count == 2) { // A Pair found
                if (Pair) {
                    TwoPair = true; // If a Pair already exists, set TwoPair to true
                } else if (ThreeKind) {
                    FullHouse = true; // If a ThreeKind exists, it forms a FullHouse
                } else {
                    Pair = true; // First Pair
                }
            } else if (count == 3) { // Three of a kind found
                if (Pair) {
                    FullHouse = true; // If a Pair exists, it forms a FullHouse
                }
                ThreeKind = true; // Set ThreeKind to true
            } else if (count == 4) { // Four of a kind found
                FourKind = true;
            }
        }

//        System.out.println(" Pair:"+Pair+"\n Two Pair:"+TwoPair+"\n Three Pair:"+
//                ThreeKind+"\n FullHouse "+FullHouse+"\n FourKind:"+FourKind);
    }

    public void isStraight() {
        Straight = false;
        ArrayList<Card> newCards = new ArrayList<>(handCards);

        newCards.sort(new Comparator<Card>() {
            public int compare(Card o1, Card o2) {
                return Integer.compare(o1.getCardValue(), o2.getCardValue());
            }
        });

        // Check for consecutive values
        for (int i = 0; i < newCards.size() - 4; i++) {
            boolean isConsecutive = true;
            for (int j = i; j < i + 4; j++) {
                if (newCards.get(j).getCardValue() + 1 != newCards.get(j + 1).getCardValue()) {
                    isConsecutive = false;
                    break;
                }
            }
            if (isConsecutive) {
                Straight = true;
            }
        }

       //System.out.println("Straight:"+Straight);
    }

    public int getCardsCredit(){
        this.isStraight();
        this.isFlush();
        this.isPairRelated();

        if (Straight && Flush) return 10;
        else if (FourKind) return 9;
        else if (FullHouse) return 8;
        else if (Flush) return 7;
        else if (Straight) return 6;
        else if (ThreeKind) return 5;
        else if (TwoPair) return 4;
        else if (Pair) return 3;
        else return 2;

    }



}
