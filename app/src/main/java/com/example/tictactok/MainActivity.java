
package com.example.tictactok;


import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // Players
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private int playerOneTotalScore, playerTwoTotalScore, roundCount;
    // Buttons
    private Button [] buttons = new Button[9];
    private Button resetGame;

    private boolean activePlayer;

     int [] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
     int [][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);
        resetGame = (Button) findViewById(R.id.resetGame);

        for(int i = 0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resID);
            buttons[i].setOnClickListener(this);
        }
        resetGame.setOnClickListener(this);
        roundCount = 0;
        playerOneTotalScore = 0;
        playerTwoTotalScore = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View view) {
        //reset game
        if(view.getId() == R.id.resetGame){
            resetGame();
        }
        if(!((Button) view).getText().toString().equals("")){
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId()); //btn_0
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length())); //0
        if(activePlayer){
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#FF0000"));
            gameState[gameStatePointer] = 0;
        }else{
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#0000FF"));
            gameState[gameStatePointer] = 1;
        }
        roundCount++;
        if(checkWinner()){
            if(activePlayer){ //player 1 won
                playerOneTotalScore++;
                updatePlayerScore();
                resetBoard();
            }else{
                playerTwoTotalScore++;
                updatePlayerScore();
                resetBoard();
            }
        }else if(roundCount == 9){
            resetBoard();
        }else{
            activePlayer = !activePlayer;
        }
        if(activePlayer){
            playerStatus.setText("Player One Turn");
        }else{
            playerStatus.setText("Player Two Turn");

        }

    }

    private void resetGame() {
        playerOneTotalScore = 0;
        playerTwoTotalScore = 0;
        updatePlayerScore();
        resetBoard();
    }

    private void resetBoard() {
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setText("");
        }
        roundCount = 0;
        activePlayer = true;
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }
    }

    private void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneTotalScore));
        playerTwoScore.setText(Integer.toString(playerTwoTotalScore));
    }

    private boolean checkWinner() {
        boolean winnerResult = false;
        for(int [] winPosition : winPositions){
            if(gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }


}