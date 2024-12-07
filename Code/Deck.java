import java.util.Arrays;
import java.util.Random;

public class Deck {

    private Card[] deck;
    private int dealt;

    public Deck() {
        int cardNumber = 0;

        deck = new Card[52];

        for (Card.Suits s: Card.Suits.values()) {
            for(Card.Cards c: Card.Cards.values()) {
                deck[cardNumber] = new Card(c,s);
                cardNumber++;
            }
        }
        dealt = 0;
    }

    public void shuffle2() {
        Random rand = new Random();
        Card temp;

        for (int i = 0; i < deck.length; i++) {
            temp = deck[i];
            deck[i] = deck[rand.nextInt(deck.length)];
            deck[rand.nextInt(deck.length)] = temp;
        }
        dealt = 0;
    }

    public void shuffle() {
        Random rand = new Random();
        for (int i = deck.length - 1; i > 0; i--) {
            // Pick a random index from 0 to i
            int j = rand.nextInt(i + 1);
            // Swap deck[i] with deck[j]
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
        dealt = 0; // Reset the dealt counter after shuffling
    }

    public void shuffleRemaining(){
        Random rand = new Random();
        // Shuffle only the portion of the deck from 'dealt' to the end
        for (int i = deck.length - 1; i > dealt; i--) {
            // Pick a random index from 'dealt' to i
            int j = rand.nextInt(i - dealt + 1) + dealt;
            // Swap deck[i] with deck[j]
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }


    public Card dealCard() {
        if (dealt == 52){
            System.out.println("No more cards to deal!");
            return null;}
        else{
            dealt++;
            return deck[dealt-1];
        }
    }

    public Card[] getLeft(){
        return Arrays.copyOfRange(deck,dealt,deck.length);

    }

}

