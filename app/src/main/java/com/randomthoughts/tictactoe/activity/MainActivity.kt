package com.randomthoughts.tictactoe.activity

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.randomthoughts.tictactoe.R
import com.randomthoughts.tictactoe.board.GameEngine
import com.randomthoughts.tictactoe.view.BoardView


/**
 * Created by senthil-zt121 on 21/04/18.
 */

class MainActivity: AppCompatActivity() {
    private var boardView: BoardView? = null
    private var gameEngine: GameEngine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boardview)
        boardView = findViewById(R.id.board)
        gameEngine = GameEngine()
        boardView?.gameEngine = gameEngine
        boardView?.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_new_game) {
            newGame()
        }

        return super.onOptionsItemSelected(item)
    }

    fun gameEnded(player: Char) {
        val msg = if (player == 'T') "Game Ended. Tie" else "GameEnded. Player $player win"

        AlertDialog.Builder(this).setTitle("Tic Tac Toe").setMessage(msg).setOnDismissListener({ newGame() }).show()
    }

    private fun newGame() {
        gameEngine?.newGame()
        boardView?.invalidate()
    }
}