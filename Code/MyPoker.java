import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;

public class MyPoker extends JFrame{
    JPanel dealerPanel;
    JPanel playerPanel;
    JPanel communityPanel;
    JPanel ButtonPanel;
    JPanel consolePanel;

    JButton deal;
    JButton call;
    JButton raise;
    JButton fold;
    JButton cheater;
    JTextField raiseAmountField;

    int dealerChip = 10000;
    int playerChip = 10000;
    int chipPool = 0;

    JLabel chipPoolLabel;
    JLabel dealerChipLabel;
    JLabel playerChipLabel;

    boolean playerWin;
    boolean dealerWin;

    PokerHand dealerCardsInHand;
    PokerHand playerCardsInHand;

    JLabel[] dealerCards;
    JLabel[] playerCards;
    JLabel[] communityCards;

    boolean isDealerTurn = false;

    Deck deck;

    JLabel consoleLabel;

    ImageIcon background = new ImageIcon("Cards/b.gif");

    private static int playRound = 0;

    public MyPoker() throws SQLException {
        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));

        DatabaseSys.createDatabase();
        DatabaseSys.createTable();
        DatabaseSys.clearDatabase();

        CreateDealerPanel();
        CreateCommunityPanel();
        CreatePlayerPanel();
        CreateButtonPanel();
        CreateConsolePanel();

        this.add(dealerPanel);
        this.add(communityPanel);
        this.add(ButtonPanel);
        this.add(playerPanel);
        this.add(consolePanel);


        this.setTitle("My Poker");
        this.setResizable(false);

    }


    public void CreateDealerPanel() {

        dealerPanel = new JPanel();
        dealerPanel.setLayout(null); // Remove layout manager
        dealerPanel.setPreferredSize(new Dimension(500, 150));
        dealerPanel.setBorder(BorderFactory.createTitledBorder(""));

        int cardWidth = background.getIconWidth();
        int cardHeight = background.getIconHeight();

        // DealerCardPanel: 2 cards wide
        JPanel dealerCardPanel = new JPanel();
        dealerCardPanel.setLayout(null); // Remove layout manager
        dealerCardPanel.setBackground(Color.GRAY);
        dealerCardPanel.setBounds(150, 32, 2 * cardWidth+2, cardHeight); // Manually set bounds
        dealerCardPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        // Create and add dealer cards
        dealerCards = new JLabel[2];
        for (int i = 0; i < dealerCards.length; i++) {
            dealerCards[i] = new JLabel();
            dealerCards[i].setBounds(i * cardWidth, 0, cardWidth+2, cardHeight); // Position each card
            //dealerCards[i].setIcon(background);
            dealerCards[i].setHorizontalAlignment(JLabel.CENTER);
            dealerCardPanel.add(dealerCards[i]);
        }

        // Label for Dealer Cards
        JLabel dealerCardLabel = new JLabel("Dealer Cards");
        dealerCardLabel.setFont(new Font("Serif", Font.BOLD, 18));
        dealerCardLabel.setBounds(10, 10, 130, 30);

        JLabel showingChipLabel = new JLabel("Dealer's Chips:");
        showingChipLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        showingChipLabel.setBounds(dealerCardPanel.getBounds().x + 2*cardWidth + 20,
                                        dealerCardPanel.getBounds().y,100,15);

        dealerChipLabel = new JLabel("$ " + dealerChip);
        dealerChipLabel.setFont(new Font("Serif", Font.BOLD, 20));
        dealerChipLabel.setBounds(dealerCardPanel.getBounds().x + 2*cardWidth + 20,
                                    dealerCardPanel.getBounds().y + 20,100,20);

        // Add components to dealerPanel
        dealerPanel.add(dealerCardLabel);
        dealerPanel.add(dealerCardPanel);
        dealerPanel.add(showingChipLabel);
        dealerPanel.add(dealerChipLabel);

    }


    public void CreateCommunityPanel() {
        communityPanel = new JPanel();
        communityPanel.setLayout(null); // Remove layout manager
        communityPanel.setPreferredSize(new Dimension(500, 200));
        communityPanel.setBorder(BorderFactory.createTitledBorder(""));

        int cardWidth = background.getIconWidth();
        int cardHeight = background.getIconHeight();

        // CommunityCardPanel: 5 cards wide
        JPanel communityCardPanel = new JPanel();
        communityCardPanel.setLayout(null); // Remove layout manager
        communityCardPanel.setBackground(Color.GRAY);
        communityCardPanel.setBounds(100, 55, 5 * cardWidth + 2, cardHeight); // Manually set bounds
        communityCardPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        // Create and add community cards
        communityCards = new JLabel[5];
        for (int i = 0; i < communityCards.length; i++) {
            communityCards[i] = new JLabel();
            communityCards[i].setBounds(i * cardWidth, 0, cardWidth+2, cardHeight); // Position each card
            //communityCards[i].setIcon(background);
            communityCards[i].setHorizontalAlignment(JLabel.CENTER);
            communityCardPanel.add(communityCards[i]);
        }

        // Label for Community Cards
        JLabel communityCardLabel = new JLabel("Community Cards");
        communityCardLabel.setFont(new Font("Serif", Font.BOLD, 18));
        communityCardLabel.setBounds(10, 10, 180, 30);

        JLabel showingChipPoolLabel = new JLabel("Chip Pool:");
        showingChipPoolLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        showingChipPoolLabel.setBounds(100,100,100,150);

        chipPoolLabel = new JLabel("$ "+ chipPool);
        chipPoolLabel.setHorizontalAlignment(JLabel.LEFT);
        chipPoolLabel.setFont(new Font("Serif", Font.BOLD, 20));
        chipPoolLabel.setBounds(200,100,200,150);

        // Add components to communityPanel
        communityPanel.add(communityCardLabel);
        communityPanel.add(communityCardPanel);
        communityPanel.add(showingChipPoolLabel);
        communityPanel.add(chipPoolLabel);

        // Debugging
//        System.out.println("CommunityCardPanel bounds: " + communityCardPanel.getBounds());
    }


    public void CreatePlayerPanel(){

        playerPanel = new JPanel();
        playerPanel.setLayout(null);
        playerPanel.setPreferredSize(new Dimension(500, 150));
        playerPanel.setBorder(BorderFactory.createTitledBorder(""));

        int cardWidth = background.getIconWidth();
        int cardHeight = background.getIconHeight();

        JPanel playerCardPanel = new JPanel();
        playerCardPanel.setLayout(null);
        playerCardPanel.setBackground(Color.GRAY);
        playerCardPanel.setBounds(150, 32, 2 * cardWidth+2, cardHeight);
        playerCardPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        playerCards = new JLabel[2];
        for (int i = 0; i < playerCards.length; i++) {
            playerCards[i] = new JLabel();
            playerCards[i].setBounds(i * cardWidth, 0, cardWidth+2, cardHeight);
            //playerCards[i].setIcon(background);
            playerCards[i].setHorizontalAlignment(JLabel.CENTER);
            playerCardPanel.add(playerCards[i]);
        }

        JLabel playerCardLabel = new JLabel("Player's Cards");
        playerCardLabel.setFont(new Font("Serif", Font.BOLD, 18));
        playerCardLabel.setBounds(10, 10, 120, 30);

        JLabel showingChipLabel = new JLabel("Player's Chips:");
        showingChipLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        showingChipLabel.setBounds(playerCardPanel.getBounds().x + 2*cardWidth + 20
                                    ,playerCardPanel.getBounds().y,100,15);

        playerChipLabel = new JLabel("$ " + playerChip);
        playerChipLabel.setFont(new Font("Serif", Font.BOLD, 20));
        playerChipLabel.setBounds(playerCardPanel.getBounds().x + 2*cardWidth + 20,
                                        playerCardPanel.getBounds().y + 20,100,20);

        cheater = new JButton();
        cheater.setText("Cheater");
        cheater.setHorizontalAlignment(JLabel.CENTER);
        cheater.setBounds(10,50,80,20);
        cheater.addActionListener(e->openCheaterPanel());

        playerPanel.add(playerCardLabel);
        playerPanel.add(playerCardPanel);
        playerPanel.add(showingChipLabel);
        playerPanel.add(playerChipLabel);
        playerPanel.add(cheater);
//        System.out.println("PlayerCardPanel bounds: " + playerCardPanel.getBounds());
    }


    public void openCheaterPanel() {

        JDialog CheaterPanel = new JDialog((JFrame) null, "Cheater Panel", false);
        CheaterPanel.setSize(400, 300);
        CheaterPanel.setLayout(new BorderLayout());

        JLabel statusLabel = new JLabel("Monitoring Winning Probability...", SwingConstants.CENTER);
        CheaterPanel.add(statusLabel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        CheaterPanel.add(exitButton, BorderLayout.SOUTH);

        // Background task
        Thread monitoringThread = new Thread(()->{
            boolean dealHandled = false;
            boolean callHandled = false;
            try{
                while(!Thread.currentThread().isInterrupted()){

                    if(!deal.isEnabled() && !dealHandled){
                        dealHandled = true;
                        Cheater cheater = new Cheater(playerCardsInHand,deck);
                        double prob = cheater.calculateWinningProb(10000);
                        SwingUtilities.invokeLater(() ->
                                statusLabel.setText(String.format("Winning Probability: %.3f%%", prob)));

                    }
                    if(!deal.isEnabled()) {
                        dealHandled = false;
                    }

                    if(call.getModel().isPressed() && !callHandled){
                        callHandled = true;
                        Cheater cheater = new Cheater(playerCardsInHand,deck);
                        double prob = cheater.calculateWinningProb(10000);
                        SwingUtilities.invokeLater(() ->
                                statusLabel.setText(String.format("Winning Probability: %.3f%%", prob)));

                    }
                    if(!call.getModel().isPressed()) {
                        callHandled = false;
                        statusLabel.setText("Monitoring Winning Probability...");
                    }

                    Thread.sleep(1000);
                }
                }catch(InterruptedException e){
                e.printStackTrace();
            }
        });

        monitoringThread.start();

        exitButton.addActionListener(e->{
            monitoringThread.interrupt();
            CheaterPanel.dispose();
        });
        CheaterPanel.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        CheaterPanel.setVisible(true);
    }


    public void CreateConsolePanel() {
        consolePanel = new JPanel();
        consolePanel.setLayout(null);
        consolePanel.setPreferredSize(new Dimension(500, 30));
        consolePanel.setBorder(BorderFactory.createTitledBorder(""));

        consoleLabel = new JLabel();
        consoleLabel.setText("- Welcome to Texas Hold'em!!! -");
        consoleLabel.setHorizontalAlignment(JLabel.CENTER);
        consoleLabel.setBounds(0,0,500, 30);
        consolePanel.add(consoleLabel);
    }


    public void CreateButtonPanel() {

        ButtonPanel = new JPanel();
        ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.X_AXIS));
        ButtonPanel.setBorder(BorderFactory.createTitledBorder(""));
        ButtonPanel.setPreferredSize(new Dimension(500, 50));

        // Buttons
        deal = new JButton("Deal");
        deal.setEnabled(true);
        deal.setMaximumSize(new Dimension(500, 50)); // Fix button height
        deal.setActionCommand("deal");
        deal.addActionListener(new ButtonListener());

        call = new JButton("Call");
        call.setEnabled(false);
        call.setMaximumSize(new Dimension(500, 50));
        call.setActionCommand("call");
        call.addActionListener(new ButtonListener());

        fold = new JButton("Fold");
        fold.setEnabled(false);
        fold.setMaximumSize(new Dimension(500, 50));
        fold.setActionCommand("fold");
        fold.addActionListener(new ButtonListener());

        raise = new JButton("Raise");
        raise.setEnabled(false);
        raise.setMaximumSize(new Dimension(500, 50));
        raise.setActionCommand("raise");
        raise.addActionListener(new ButtonListener());
        raiseAmountField = new JTextField("-Enter Raise Amount-");
        raiseAmountField.setHorizontalAlignment(JTextField.CENTER);
        raiseAmountField.setEditable(false);
        raiseAmountField.setMaximumSize(new Dimension(500, 50));

        // Add components
        ButtonPanel.add(deal);
        ButtonPanel.add(call);
        ButtonPanel.add(fold);
        ButtonPanel.add(raise);
        ButtonPanel.add(raiseAmountField);

    }

    private boolean isNewGame = false;
    private int callRound = 0;
    int raiseAmount;

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
//            System.out.println("ActionPerformed triggered: " + e.getActionCommand()); // Debug
            String event = e.getActionCommand();
            switch(event) {
                case "deal":
                    DealButton();
                    break;
                case "call":
                    CallButton();
                     break;
                case "fold":
                    FoldButton();

                    setConsoleLabelText("You Choose to Fold, Lose the game");
                    dealerChip += chipPool;
                    chipPool = 0;
                    dealerChipLabel.setText("$ "+dealerChip);
                    chipPoolLabel.setText("$ "+chipPool);
                    playerChipLabel.setText("$ "+playerChip);

//                    System.out.println("dealCards: " + dealerCardsInHand.getHandCards());
//                    System.out.println("PlayerCards: " + playerCardsInHand.getHandCards());

                    break;
                case "raise":
                    RaiseButton();
                    break;
            }
        }
    }


    public void DealButton(){

        if (isNewGame) {
//                deck = new Deck();
//                deck.shuffle();
            isNewGame = false;
            call.setText("Call");
            setConsoleLabelText("- Welcome to Texas Hold'em!!! -");
            for (int i = 0; i < playerCards.length; i++) {
                playerCards[i].setIcon(null);
                dealerCards[i].setIcon(null);
            }
            for(int i = 0; i < communityCards.length; i++) {
                communityCards[i].setIcon(null);
            }
            playerCardsInHand.clearHandCards();
            dealerCardsInHand.clearHandCards();
            callRound = 0;
        }

        deck = new Deck();
        deck.shuffle();

        fold.setEnabled(true);
        raise.setEnabled(true);
        call.setEnabled(true);
        deal.setEnabled(false);
        raiseAmountField.setText(" ");
        raiseAmountField.setEditable(true);

        dealerCardsInHand = new PokerHand();
        playerCardsInHand = new PokerHand();

        playRound++;
        DatabaseSys.insertData(playRound,playerChip,dealerChip);
        DatabaseSys.showAllData();


        for(int i = 0; i<2; i++){
            dealerCardsInHand.addCard(deck.dealCard());
            playerCardsInHand.addCard(deck.dealCard());
            dealerCards[i].setIcon(background);
            ImageIcon icon = new ImageIcon(playerCardsInHand.getCard(i).getFileName());
            playerCards[i].setIcon(icon);

        }

        if (dealerChip > 10 && playerChip >10){
            dealerChip = dealerChip - 10;
            playerChip = playerChip - 10;
            chipPool = chipPool + 20;

            chipPoolLabel.setText("$ "+chipPool);
            playerChipLabel.setText("$ "+playerChip);
            dealerChipLabel.setText("$ "+dealerChip);

//            if(isDealerTurn){
//                dealerGaming();
//                isDealerTurn = false;
//            }

        } else if (dealerChip < 10) {
            JOptionPane.showMessageDialog(null,"Dealer have no Chip left, Lose the game",
                    "No Chips", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(null,"Click to Have A New Game",
                    "No Chips", JOptionPane.INFORMATION_MESSAGE);
            try{
                String[] args = {};
                MyPoker.main(args);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Player have no Chip left, Lose the game",
                    "No Chips", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(null,"Click to Have A New Game",
                    "No Chips", JOptionPane.INFORMATION_MESSAGE);
            try{
                String[] args = {};
                MyPoker.main(args);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

//        System.out.println("Dealer Cards in Hand: " + dealerCardsInHand.getHandCards());

    }


    public void CallButton(){
        callRound++;
        isDealerTurn = true;

        switch(callRound){
            case 1:

                if(isDealerTurn) dealerGaming();

                isNewGame = false;
                for(int i = 0; i<3; i++){
                    Card dealCard = deck.dealCard();
                    dealerCardsInHand.addCard(dealCard);
                    playerCardsInHand.addCard(dealCard);
                    ImageIcon icon = new ImageIcon(dealCard.getFileName());
                    communityCards[i].setIcon(icon);
                }

                break;

            case 2:
                Card dealCar2 = deck.dealCard();
                dealerCardsInHand.addCard(dealCar2);
                playerCardsInHand.addCard(dealCar2);
                ImageIcon icon = new ImageIcon(dealCar2.getFileName());
                communityCards[3].setIcon(icon);

                if(isDealerTurn) dealerGaming();

                break;

            case 3:
                Card dealCard3 = deck.dealCard();
                dealerCardsInHand.addCard(dealCard3);
                playerCardsInHand.addCard(dealCard3);
                ImageIcon icon3 = new ImageIcon(dealCard3.getFileName());
                communityCards[4].setIcon(icon3);

                for (int i = 0; i < 2; i++) {
                    ImageIcon dealerCard = new ImageIcon(dealerCardsInHand.getCard(i).getFileName());
                    dealerCards[i].setIcon(dealerCard);
//                    System.out.println(i+"th Dealer Cards in Hand: " + dealerCardsInHand.getCard(i));
                }

                winingStatusCheck();

                if(playerWin){
                    setConsoleLabelText("Player Wins the Game, Get $ " + chipPool);
                    playerChip += chipPool;
                    chipPool = 0;
                    playerChipLabel.setText("$ "+playerChip);
                    dealerChipLabel.setText("$ "+dealerChip);
                    chipPoolLabel.setText("$ "+chipPool);
                }
                else if (dealerWin){
                    setConsoleLabelText("Dealer Wins the Game, Get $ " + chipPool);
                    dealerChip += chipPool;
                    chipPool = 0;
                    playerChipLabel.setText("$ "+playerChip);
                    dealerChipLabel.setText("$ "+dealerChip);
                    chipPoolLabel.setText("$ "+chipPool);
                }
                else {
                    setConsoleLabelText("The Game is Tied, Split the Chip Pool");
                    dealerChip = dealerChip + chipPool/2;
                    playerChip = playerChip + chipPool/2;
                    chipPool = 0;
                    playerChipLabel.setText("$ "+playerChip);
                    dealerChipLabel.setText("$ "+dealerChip);
                    chipPoolLabel.setText("$ "+chipPool);
                }


                call.setEnabled(false);
                fold.setEnabled(false);
                raise.setEnabled(false);
                raiseAmountField.setText("-Enter Raise Amount-");
                raiseAmountField.setEditable(false);
                deal.setEnabled(true);
                isNewGame = true;

        }

//        System.out.println("dealCards: " + dealerCardsInHand.getHandCards());
//        System.out.println("PlayerCards: " + playerCardsInHand.getHandCards());
    }


    public void FoldButton(){

        dealerCardsInHand.clearHandCards();
        playerCardsInHand.clearHandCards();

        for(int i = 0; i<playerCards.length; i++){
            playerCards[i].setIcon(null);
            dealerCards[i].setIcon(null);
        }

        for(int i = 0; i<communityCards.length; i++){
            communityCards[i].setIcon(null);
        }

        isNewGame = true;
        fold.setEnabled(false);
        raise.setEnabled(false);
        raiseAmountField.setText("-Enter Raise Amount-");
        raiseAmountField.setEditable(false);
        call.setEnabled(false);
        deal.setEnabled(true);
        callRound = 0;


    }


    public void RaiseButton(){

        try{
            raiseAmount = Integer.parseInt(raiseAmountField.getText().trim());
            playerChip -= raiseAmount;
            chipPool += raiseAmount;
            chipPoolLabel.setText("$ "+chipPool);
            playerChipLabel.setText("$ "+playerChip);

            dealerGaming();
        }catch (NumberFormatException e){
            raiseAmountField.setText("");
            JOptionPane.showMessageDialog(null,
                    "Please enter a valid raise amount",
                    "Invaild Entrance",
                    JOptionPane.WARNING_MESSAGE);
        }

    }


    public void winingStatusCheck(){

        int dealerCredit = dealerCardsInHand.getCardsCredit();
        int playerCredit = playerCardsInHand.getCardsCredit();

        playerWin = false;
        dealerWin = false;

        if (dealerCredit > playerCredit)
            dealerWin = true;
            //setConsoleLabelText("The Dealer wins");
        else if (playerCredit > dealerCredit)
            playerWin = true;
            //setConsoleLabelText("The Player wins");
        else{
            int playerLargerValue = Math.max(playerCardsInHand.handCards.get(0).getCardValue(),playerCardsInHand.handCards.get(1).getCardValue());
            int dealerLargerValue = Math.max(dealerCardsInHand.handCards.get(0).getCardValue(),dealerCardsInHand.handCards.get(1).getCardValue());

            if(playerLargerValue > dealerLargerValue)
                playerWin = true;
                //setConsoleLabelText("The Player wins");
            else if (dealerLargerValue > playerLargerValue)
                dealerWin = true;
                //setConsoleLabelText("The Dealer wins");
            else{
                playerLargerValue = Math.min(playerCardsInHand.handCards.get(0).getCardValue(),playerCardsInHand.handCards.get(1).getCardValue());
                dealerLargerValue = Math.min(playerCardsInHand.handCards.get(0).getCardValue(),dealerCardsInHand.handCards.get(1).getCardValue());

                if(playerLargerValue > dealerLargerValue)
                    playerWin = true;
                    //setConsoleLabelText("The Player wins");
                else if(dealerLargerValue > playerLargerValue)
                    dealerWin = true;
                    //setConsoleLabelText("The Dealer wins");
//                else
//                    setConsoleLabelText("The games is tied");
            }
        }

    }


    public void setConsoleLabelText(String text) {
        consoleLabel.setText(text);
    }


    static class BubblePanel extends JPanel{
        private String content;

        public BubblePanel(String content){
            this.content = content;
            setOpaque(false);
        }

        public void setContent(String content){
            this.content = content;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(Color.WHITE);
            g2d.fill(new RoundRectangle2D.Double(20, 10, 130, 40, 20, 20));

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            g2d.draw(new RoundRectangle2D.Double(20, 10, 130, 40, 20, 20));

            int[] xPoints = {20, 0, 20}; // Tail pointing to the left
            int[] yPoints = {20, 23, 40};
            g2d.fillPolygon(xPoints, yPoints, 3);

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString(content, 30, 35);

        }
    }


    private Timer activeTimer = null; // Track the active timer
    private BubblePanel bubblePanel = null;

    public void setBubblePanel(String line){

        if (activeTimer != null && activeTimer.isRunning()) {
            activeTimer.stop(); // Stop the previous timer
            dealerPanel.remove(bubblePanel); // Remove the previous bubble if any
        }

        bubblePanel = new BubblePanel(line);
        bubblePanel.setBounds(310,80,150,60);
        dealerPanel.add(bubblePanel);
        dealerPanel.revalidate();
        dealerPanel.repaint();

        activeTimer = new Timer(1500, _ -> {
            dealerPanel.remove(bubblePanel);
            dealerPanel.revalidate();
            dealerPanel.repaint();
        });

        activeTimer.setRepeats(false); // Ensure the timer only runs once
        activeTimer.start();
    }


    public void dealerGaming(){

//        System.out.println("Entering dealerGaming");

        Cheater computerCheater = new Cheater(dealerCardsInHand,deck);
        double winningProb = computerCheater.calculateWinningProb(10000);
//        System.out.println("Dealer Winning Prob" + winningProb);
        if (winningProb < 0.08*100) {

            setBubblePanel("Fold");

            FoldButton();
//            JOptionPane.showMessageDialog(null,"The Dealer Choose to Fold, You win","Dealer Folded",JOptionPane.INFORMATION_MESSAGE);
            setConsoleLabelText("The Dealer Choose to Fold, Lose the game");
            playerChip += chipPool;
            chipPool = 0;
            dealerChipLabel.setText("$ "+dealerChip);
            chipPoolLabel.setText("$ "+chipPool);
            playerChipLabel.setText("$ "+playerChip);

//            System.out.println("dealCards: " + dealerCardsInHand.getHandCards());
//            System.out.println("PlayerCards: " + playerCardsInHand.getHandCards());

        } else if (winningProb < 0.4*100) {

            setBubblePanel("Check");

//            System.out.println("Check or Call"); // Plays safe but doesn't fold.
            //Generate A Check Bubble

        } else if (winningProb < 0.6*100) {

            int raiseAmount = (int) (Math.random()*(100-30)) + 30;

            setBubblePanel("Raise $ " + raiseAmount );

            dealerChip -= raiseAmount;
            chipPool += raiseAmount;
            chipPoolLabel.setText("$ "+chipPool);
            dealerChipLabel.setText("$ "+dealerChip);
            call.setText("Check");

        } else if (winningProb < 0.8*100) {

            int raiseAmount = (int) (Math.random()*(300-100)) + 100;

            setBubblePanel("Raise $ " + raiseAmount );
            dealerChip -= raiseAmount;
            chipPool += raiseAmount;
            chipPoolLabel.setText("$ "+chipPool);
            dealerChipLabel.setText("$ "+dealerChip);
            call.setText("Check");

        } else {

            int raiseAmount = (int) (Math.random()*(1000-300)) + 300;
            setBubblePanel("Raise $ " + raiseAmount );

            dealerChip -= raiseAmount;
            chipPool += raiseAmount;
            chipPoolLabel.setText("$ "+chipPool);
            dealerChipLabel.setText("$ "+dealerChip);
            call.setText("Check");

        }

    }


    class DatabaseSys{
        private static final String db_url = "jdbc:sqlite:ChipEveryRound.db";

        public static void createDatabase() throws SQLException {
            try (Connection connection = DriverManager.getConnection(db_url)){
                if(connection != null){
                    System.out.println("Database created");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public static void createTable() throws SQLException {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS game_stats (" +
                    "playerRound INTEGER NOT NULL, " +
                    "playerLeftChip INTEGER NOT NULL, " +
                    "computerLeftChip INTEGER NOT NULL " +
                    ");";

            try (Connection connection = DriverManager.getConnection(db_url)){
                Statement statement = connection.createStatement();
                statement.execute(createTableSQL);
//                System.out.println("Table created");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        public static void insertData(int playerRound, int playerLeftChip, int computerLeftChip) {
            String insertSQL = "INSERT INTO game_stats (playerRound, playerLeftChip, computerLeftChip) VALUES (?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(db_url)) {
                conn.setAutoCommit(true);

                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    pstmt.setInt(1, playerRound);
                    pstmt.setInt(2, playerLeftChip);
                    pstmt.setInt(3, computerLeftChip);

                    pstmt.executeUpdate();  // Execute the insert
                }
            } catch (SQLException e) {
                e.printStackTrace();
//                System.out.println("Error inserting data: " + e.getMessage());
            }
        }


        public static void showAllData() {
            String selectSQL = "SELECT * FROM game_stats";

            try (Connection conn = DriverManager.getConnection(db_url);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectSQL)) {

                System.out.println("\n--- Game Stats ---");
                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    int playerRound = rs.getInt("playerRound");
                    int playerLeftChip = rs.getInt("playerLeftChip");
                    int computerLeftChip = rs.getInt("computerLeftChip");

                    System.out.printf("Round: %d, Player Chips: %d, Computer Chips: %d\n",
                            playerRound, playerLeftChip, computerLeftChip);
                }

                if (!hasData) {
                    System.out.println("No data found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error retrieving data: " + e.getMessage());
            }
        }


        public static void clearDatabase() {
            String deleteSQL = "DELETE FROM game_stats";

            try (Connection conn = DriverManager.getConnection(db_url);
                 Statement stmt = conn.createStatement()) {

                int rowsAffected = stmt.executeUpdate(deleteSQL);
//                System.out.println("All data cleared! Rows deleted: " + rowsAffected);
            } catch (SQLException e) {
//                System.out.println("Error clearing database: " + e.getMessage());
            }
        }

    }


    public static void main(String[] args) throws SQLException {
        MyPoker myPoker = new MyPoker();
        myPoker.pack();
        myPoker.setLocationRelativeTo(null);
        myPoker.setVisible(true);
        myPoker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
