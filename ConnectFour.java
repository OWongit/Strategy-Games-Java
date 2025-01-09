// Owen Wilhere
// 01/24/2023
// CSE 123
// TA: Jay Dharmadhikari
// This class is ConnectFour. It implements the AbstractStrategyGame
// interface and functions as the classic connect four table top game.

import java.util.*;

public class ConnectFour implements AbstractStrategyGame {

    private char[][] board;
    private boolean isYTurn;

    // Constructs the class ConnectFour and creates the gameboard
    public ConnectFour() {
        this.board = new char[][]
        {{'-', '-', '-', '-', '-', '-', '-'},
         {'-', '-', '-', '-', '-', '-', '-'},
         {'-', '-', '-', '-', '-', '-', '-'},
         {'-', '-', '-', '-', '-', '-', '-'},
         {'-', '-', '-', '-', '-', '-', '-'},
         {'-', '-', '-', '-', '-', '-', '-'}};
         isYTurn = true;
    }


    // This method returns a String with the instructions of the game.
    public String instructions(){
        String result = "Player 1 is Y and goes first. Choose where to drop your token by entering a \n";
        result += "column number 0 - 6. As the game is played, player's tokens are stacked in \n";
        result += "the slots until a player gets four tokens in a row, vertically or horizontally. \n";
        result += "Players can either place a token or remove one of their bottom tokens. Once \n";
        result += "their token is removed all above tokens will drop down 1 spot. If every slot \n"; 
        result += "becomes completely full before there is a winner, the game is tied.";
        return result;
    }

    // This method returns a String with the status of the board.
    public String toString(){
        String result = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                result += board[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }

    // This method checks to see if the game is over and returns a boolean 
    // value of true if the game is over.
    public boolean isGameOver(){
        return getWinner() >= 0;
    }

    // This method gets the winner of the game and returns an integer.
    // Returns 1 if player 1(Y) has won.
    // Returns 2 if player 2(R) has won.
    // Returns -1 if the game isn't over.
    // Returns 0 if the game is tied.
    public int getWinner(){
        // check horizantal
        if(checkRow() == 'Y'){
            return 1;
        }
        if(checkRow() == 'R'){
            return 2;
        }
        // check vertical
        if(checkColumn() == 'Y'){
            return 1;
        }
        if(checkColumn() == 'R'){
            return 2;
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == '-') {
                    return -1;
                }
            }
        }

        return 0;
    }

    // This method finds out who's turn it is and returns an integer.
    // Returns 1 if it is player 1's(Y) turn.
    // Returns 2 if it is player 2's(R) turn.
    // Returns -1 if the game isn't over yet.
    public int getNextPlayer(){
        if (isGameOver()) {
            return -1;
        }
        if(isYTurn){
            return 1;
        }
        return 2;
    }

    // This method makes a move. It prompts the user if they want to place or
    // remove a token, and which column they would like to do this in. Throws
    // an IllegalArgumentException if user inputs something other than 'P' or 'R'
    // when asked to place or remove a token.
    // Paramaters: Scanner input - the scanner used to interact with the user.
    // No returns.
    public void makeMove(Scanner input){
        char currPlayer = 'N';
        if(isYTurn){
            currPlayer = 'Y';
        }else{
            currPlayer = 'R';
        }
        System.out.print("Column? (0 - 6) ");
        int col = input.nextInt();
        System.out.print("Place or Remove(P or R)? ");
        String placeRemove = input.next();
        if(placeRemove.equalsIgnoreCase("P")){
            place(col, currPlayer);
        } else if(placeRemove.equalsIgnoreCase("R")){
            remove(col, currPlayer);
        } else {
            throw new IllegalArgumentException("Invalid Input, not 'P' or 'R'");
        }
        isYTurn = !isYTurn;
    }

    // This method checks each row for four of the same tokens in a row
    // and returns the character of the winner('Y' or 'R').
    // It returns 'N' if there is no winner.
    private char checkRow(){
        char winner = 'N';
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++){
                if(board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && 
                   board[i][j] == board[i][j + 3] && board[i][j] != '-'){
                    winner = board[i][j];
                }
            }
        }
        return winner;
    }

    // This method checks each column for four of the same tokens in a row
    // and returns the character of the winner('Y' or 'R').
    // It returns 'N' if there is no winner.
    private char checkColumn(){
        char winner = 'N';
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++){
                if(board[j][i] == board[j + 1][i] && board[j][i] == board[j + 2][i] && 
                   board[j][i] == board[j + 3][i] && board[j][i] != '-'){
                    winner = board[j][i];
                }
            }
        }
        return winner;
    }

    // This method places a token. Throws an IllegalArgumentException if the given column
    // is not within the bounds of board or if the column is already full.
    // Parameters: int col - the column that the token is placed in
    //             char currPlayer - the token of the current player
    // No returns.
    private void place(int col, char currPlayer){
        if(col < 0 || col > 6){
            throw new IllegalArgumentException("Invalid Input, column not within bounds");
        }
        if(board[0][col] != '-'){
            throw new IllegalArgumentException("Invalid Input, column is full");
        }
        int i = 0;
        while(board[i][col] == '-' && i < 5){
            i++;
        }
        if(board[i][col] != '-'){
            i--;
        }
        board[i][col] = currPlayer;
    }

    // This method removes a token. Throws an IllegalArgumentException if the given column
    // is not within the bounds of board or if there is no token of the given player.
    // Parameters: int col - the column that the token is removed from
    //             char currPlayer - the token of the current player
    // No returns.
    private void remove(int col, char currPlayer){
        if(col < 0 || col > 6){
            throw new IllegalArgumentException("Invalid Input, column not within bounds");
        }
        if(board[5][col] != currPlayer){
            throw new IllegalArgumentException("Invalid Input, no token to be removed");
        }
        for(int i = 5; i > 0; i--){
            board[i][col] = board[i-1][col];
        }
        board[0][col] = '-';
    }
}
