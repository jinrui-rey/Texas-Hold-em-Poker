import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cheater{

    private final PokerHand playerHand;
    private final ArrayList<Card> communityHand = new ArrayList<>();
    private final Deck deck;

    public Cheater(PokerHand handCards, Deck deck) {
        this.playerHand = new PokerHand();
        playerHand.addCard(handCards.getCard(0));
        playerHand.addCard(handCards.getCard(1));

        communityHand.addAll(handCards.handCards.subList(2,handCards.handCards.size()));
        this.deck = deck;
    }


    public double calculateWinningProb(int numSimulation){
        int wins = 0;


        for(int i=0; i<numSimulation; i++){
            deck.shuffleRemaining();

            ArrayList<Card> remainingDeck = new ArrayList<>(List.of(deck.getLeft()));

            PokerHand opponentHand = new PokerHand();
            opponentHand.addCard(remainingDeck.removeFirst());
            opponentHand.addCard(remainingDeck.removeFirst());

            ArrayList<Card> fullCommunityCards = new ArrayList<>(communityHand);
            while(fullCommunityCards.size()<5){
                fullCommunityCards.add(remainingDeck.removeFirst());
            }

            PokerHand playerHandCopy = new PokerHand();
            playerHandCopy.addCard(playerHand.getCard(0));
            playerHandCopy.addCard(playerHand.getCard(1));
            for(Card card: fullCommunityCards){
                playerHandCopy.addCard(card);
                opponentHand.addCard(card);
            }

            int playerCredit = playerHandCopy.getCardsCredit();
            int opponentCredit = opponentHand.getCardsCredit();

            if (playerCredit > opponentCredit) wins += 1;
        }
        return (double) wins / numSimulation * 100;
    }



}
