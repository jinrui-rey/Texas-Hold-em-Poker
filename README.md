# Texas-Hold-em-Poker

1. **Project Description**:
   This is a Java-based Texas Hold'em Poker Game where you play against the computer. The game features a button to monitor your winning probability (the "Cheater" button), and after each round, the player's and computer's remaining chips are stored in an SQLite database for potential future analysis or work.

   
2. **Features**:
   
     - Play as the player and compete against the computer.
     - **Cheater Button**: Displays the current winning probability.
     - After each round, the chip count for both player and computer is saved to a database.

3. **Requirements**:
   
  * **Java** (JDK 8 or above)
  * **javax.swing**: For GUI components.
  * **java.awt**: For additional GUI components.
  * **java.sql**: For SQLite database operations.
    
4. **Clone**:
   ```
   git clone https://github.com/jinrui-rey/texas-holdem-poker.git
   cd texas-holdem-poker
   ```
   
5. **Usage**:
   * **Deal Button:** the dealer shuffles and distributes two private cards to each player to start the game.
   * **Call Button:** a player matches the current bet to stay in the hand.
   * **Check Button:** a player passes the action without betting, provided no one has bet yet in the round.
   * **Raise Button:**  a player discards their hand and forfeits any chance of winning the pot.
   * **Fold Button:** a player increases the current bet, forcing others to call or fold.
   * **cheater Button** open a window to monitor the winning proability
6. **Database**: The database have three columns **playRound**, **playerLeftChip** and **computerLeftChip**, and the **playRound** is the prime key.

## Execute Without an IDE:

The following instructions tell users how to execute the Java file without needing an IDE:

1. **Compile the Java Code**:
    ```bash
    javac -cp ".;path_to_sqlite_jdbc.jar" MyPoker.java
    ```

2. **Run the Program**:
    ```bash
    java -cp ".;path_to_sqlite_jdbc.jar" MyPoker
    ```

Replace `path_to_sqlite_jdbc.jar` with the actual path to the SQLite JDBC driver file. On Linux/macOS, make sure to replace `;` with `:` in the classpath separator.
