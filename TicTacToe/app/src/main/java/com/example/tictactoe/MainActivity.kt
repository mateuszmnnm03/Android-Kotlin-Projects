package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var XTurn = true
    private var moveCount = 0
    private lateinit var buttons : Array<Array<Button>>
    private lateinit var plX : TextView
    private lateinit var plO : TextView
    private lateinit var resetBtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = arrayOf(
            arrayOf(findViewById(R.id.b1), findViewById(R.id.b2), findViewById(R.id.b3)),
            arrayOf(findViewById(R.id.b4), findViewById(R.id.b5), findViewById(R.id.b6)),
            arrayOf(findViewById(R.id.b7), findViewById(R.id.b8), findViewById(R.id.b9))
        )

        for (row in buttons){
            for (button in row) {
                button.setOnClickListener(this)
            }
        }

        plX = findViewById(R.id.playerx)
        plO = findViewById(R.id.playero)
        resetBtn = findViewById(R.id.reset)

        resetBtn.setOnClickListener {
            resetBoard()
        }
    }

    override fun onClick(v: View?) {

        val button = v as Button
        if (button.text.isNotEmpty()) return

        if (XTurn) {
            button.text = "X"
        }
        else {
            button.text = "O"
        }

        if (checkWin() == 1){
            Toast.makeText(this, "Player X won!", Toast.LENGTH_LONG).show()
            return
        }

        else if (checkWin() == 2){
            Toast.makeText(this, "Player O won!", Toast.LENGTH_LONG).show()
            return
        }

        moveCount += 1
        XTurn = !XTurn

        if (moveCount == 9){
            Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show()
            return
        }

        updateTurn()
    }

    private fun updateTurn() {
        if (!XTurn) {
            plO.setBackgroundResource(R.drawable.player_bg)
            plX.setBackgroundResource(android.R.color.transparent)
        }

        else {
            plO.setBackgroundResource(android.R.color.transparent)
            plX.setBackgroundResource(R.drawable.player_bg)
        }
    }

    private fun checkWin(): Int // zwraca 0 jesli bez wygranej, 1 jesli X, 2 jesli O
    {
        val board = Array(3) {r -> Array(3) {c -> buttons[r][c].text.toString()} }

        if ((board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0].isNotEmpty()) || (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2].isNotEmpty())){
            if (board[1][1] == "X") {
                return 1
            }
            else {
                return 2
            }
        }

        for (i in 0..2) {
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i].isNotEmpty()) {
                if (board[0][i] == "X") {
                    return 1
                }
                else {
                    return 2
                }
            }
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0].isNotEmpty()) {
                if (board[i][0] == "X") {
                    return 1
                }
                else {
                    return 2
                }
            }
        }
        return 0
    }

    private fun resetBoard() {
        for (row in buttons) {
            for (button in row) {
                button.text = ""
            }
        }

        moveCount = 0
        XTurn = true
        updateTurn()
    }
}