package com.best.karem.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.best.karem.tictactoe.Board.Companion.Computer
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val boardcells = Array(3) { arrayOfNulls<ImageView>(3) }

    var board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadBoard()

        button_restart.setOnClickListener {

            // Create New Board
            board = Board()
            // set text to empty
            text_view_result.text = ""

            mapBoardToUi()

        }

    }

    private fun mapBoardToUi() {

        for (i in board.board.indices) {
            for (j in board.board.indices) {
                when (board.board[i][j]) {

                    Board.Player -> {
                        boardcells[i][j]?.setImageResource(R.drawable.cirlce)
                        boardcells[i][j]?.isEnabled = false
                    }
                    Board.Computer -> {
                        boardcells[i][j]?.setImageResource(R.drawable.x)
                        boardcells[i][j]?.isEnabled = false
                    }
                    else -> {
                        boardcells[i][j]?.setImageResource(0)
                        boardcells[i][j]?.isEnabled = true
                    }

                }
            }
        }
    }

    private fun loadBoard() {

        for (i in boardcells.indices) {
            for (j in boardcells.indices) {
                boardcells[i][j] = ImageView(this)

                // Apply some changes to grid layout
                boardcells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {

                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 250
                    height = 230
                    bottomMargin = 5
                    topMargin = 5
                    leftMargin = 5
                    rightMargin = 5
                }
                boardcells[i][j]?.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                boardcells[i][j]?.setOnClickListener(CellClickListener(i, j))
                layout_board.addView(boardcells[i][j])
            }
        }
    }

    inner class CellClickListener(private val i: Int, private val j: Int) : View.OnClickListener {
        override fun onClick(p0: View?) {

            if (!board.isGameOver) {


                val cell = Cell(i, j)
                board.placeMove(cell, Board.Player)

                // Get Random cell from available cells


                board.minimax(0, Board.Computer)
                board.minimax(0, Board.Computer)

                // ? means not null
                board.computersMove?.let {
                    board.placeMove(it, Board.Computer)
                }



                mapBoardToUi()

            }

            when {
                board.hasComputerWon() -> text_view_result.text = "Computer Won"
                board.hasPlayerWon() -> text_view_result.text = "Player Won"
                board.isGameOver -> text_view_result.text = "Game Tied"
            }

        }


    }

}
