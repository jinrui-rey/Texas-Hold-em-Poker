public class Card {
    private String UID;
    private Suits suit;
    private Cards card;


    public enum Cards{

        Two(2),
        Three(3),
        Four(4),
        Five(5),
        Six(6),
        Seven(7),
        Eight(8),
        Nine(9),
        Ten(10),
        Jack(11),
        Queen(12),
        King(13),
        Ace(14);

        private final int value;

        Cards(int value){
            this.value = value;
        }

    };

    public enum Suits{
        Hearts,
        Diamonds,
        Clubs,
        Spades;
    };

    public Card(Cards card, Suits suit){
        this.card = card;
        this.suit = suit;
        this.UID = suit.toString();
    }

    public Suits getSuit(){
        return this.suit;
    }

    public Cards getCard(){
        return this.card;
    }

    public int getCardValue(){
        return this.card.value;
    }

    public String getUID(){
        return this.UID;
    }

    public String getFileName(){
        return "Cards/" + card.toString() + " of " + suit.toString() + ".gif";
    }

    public String getSuitString(){
        return this.suit.toString();
    }

    public String toString(){
        return this.card.toString() + "of" + this.suit.toString();
    }
}




