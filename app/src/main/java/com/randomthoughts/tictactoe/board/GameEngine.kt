package com.randomthoughts.tictactoe.board

/**
 * Created by senthil-zt121 on 21/04/18.
 */
import java.util.Random

class GameEngine() {
    private val RANDOM = Random()
    private var elts: CharArray = CharArray(9)
    private var currentPlayer: Char = ' '
    private var ended: Boolean = false

    init {
        newGame()
    }

    fun isEnded(): Boolean {
        return ended
    }

    fun play(x: Int, y: Int): Char {
        if (!ended && elts[3 * y + x] == ' ') {
            elts[3 * y + x] = currentPlayer
            changePlayer()
        }

        return checkEnd()
    }

    fun changePlayer() {
        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
    }

    fun getElt(x: Int, y: Int): Char {
        return elts[3 * y + x]
    }

    fun newGame() {
        for (i in elts.indices) {
            elts[i] = ' '
        }

        currentPlayer = 'X'
        ended = false
    }

    fun checkEnd(): Char {
        for (i in 0..2) {
            if (getElt(i, 0) != ' ' &&
                    getElt(i, 0) == getElt(i, 1) &&
                    getElt(i, 1) == getElt(i, 2)) {
                ended = true
                return getElt(i, 0)
            }

            if (getElt(0, i) != ' ' &&
                    getElt(0, i) == getElt(1, i) &&
                    getElt(1, i) == getElt(2, i)) {
                ended = true
                return getElt(0, i)
            }
        }

        if (getElt(0, 0) != ' ' &&
                getElt(0, 0) == getElt(1, 1) &&
                getElt(1, 1) == getElt(2, 2)) {
            ended = true
            return getElt(0, 0)
        }

        if (getElt(2, 0) != ' ' &&
                getElt(2, 0) == getElt(1, 1) &&
                getElt(1, 1) == getElt(0, 2)) {
            ended = true
            return getElt(2, 0)
        }

        return if ((0..8).any { elts[it] == ' ' }) ' ' else 'T'
    }

    fun computer(): Char {
        if (!ended) {
            var position: Int

            do {
                position = RANDOM.nextInt(9)
            } while (elts[position] != ' ')

            elts[position] = currentPlayer
            changePlayer()
        }

        return checkEnd()
    }
}