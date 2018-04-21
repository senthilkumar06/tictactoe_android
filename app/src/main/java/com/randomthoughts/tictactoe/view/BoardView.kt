package com.randomthoughts.tictactoe.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.util.AttributeSet
import android.view.MotionEvent
import com.randomthoughts.tictactoe.activity.MainActivity
import com.randomthoughts.tictactoe.board.GameEngine


/**
 * Created by senthil-zt121 on 21/04/18.
 */

class BoardView: View {
    companion object {
        val ELT_STROKE_WIDTH = 15f
        val LINE_THICK = 5
        val ELT_MARGIN = 20
    }
    var w: Int = 0
    val h: Int = 0
    private val eltW: Int = 0
    private val eltH: Int = 0
    private var gridPaint: Paint = Paint()
    private var oPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var xPaint: Paint
    var gameEngine: GameEngine? = null
    var activity: MainActivity? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        oPaint.color = Color.RED
        oPaint.style = Paint.Style.STROKE
        oPaint.strokeWidth = ELT_STROKE_WIDTH
        xPaint = Paint(oPaint)
        xPaint.color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas) {
        drawGrid(canvas)
        drawBoard(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (gameEngine?.isEnded() != true && event.action == MotionEvent.ACTION_DOWN) {
            val x = (event.x / eltW).toInt()
            val y = (event.y / eltH).toInt()
            var win = gameEngine?.play(x, y)
            invalidate()
            win?.let {
                if (win != ' ') {
                    activity?.gameEnded(it)
                } else {
                    // computer plays ...
                    win = gameEngine?.computer()
                    invalidate()

                    if (win != ' ') {
                        activity?.gameEnded(it)
                    } else {}
                }
            }
        }

        return super.onTouchEvent(event)
    }

    private fun drawBoard(canvas: Canvas) {
        (0..2).forEach { i -> (0..2).forEach { j -> drawElt(canvas, gameEngine?.getElt(i, j) ?: Char.MIN_SURROGATE, i, j) } }
    }

    private fun drawGrid(canvas: Canvas) {
        for (i in 0..1) {
            // vertical lines
            val left = (eltW * (i + 1)).toFloat()
            val right = left + LINE_THICK
            val top = 0f
            val bottom = h.toFloat()

            canvas.drawRect(left, top, right, bottom, gridPaint)

            // horizontal lines
            val left2 = 0f
            val right2 = w.toFloat()
            val top2 = (eltH * (i + 1)).toFloat()
            val bottom2 = top2 + LINE_THICK

            canvas.drawRect(left2, top2, right2, bottom2, gridPaint)
        }
    }

    private fun drawElt(canvas: Canvas, c: Char, x: Int, y: Int) {
        if (c == 'O') {
            val cx = (eltW * x + eltW / 2).toFloat()
            val cy = (eltH * y + eltH / 2).toFloat()

            canvas.drawCircle(cx, cy, (Math.min(eltW, eltH)).toFloat() / 2 - ELT_MARGIN * 2, oPaint)

        } else if (c == 'X') {
            val startX = (eltW * x + ELT_MARGIN).toFloat()
            val startY = (eltH * y + ELT_MARGIN).toFloat()
            val endX = startX + eltW - ELT_MARGIN * 2
            val endY = startY + eltH - ELT_MARGIN

            canvas.drawLine(startX, startY, endX, endY, xPaint)

            val startX2 = (eltW * (x + 1) - ELT_MARGIN).toFloat()
            val startY2 = (eltH * y + ELT_MARGIN).toFloat()
            val endX2 = startX2 - eltW + ELT_MARGIN * 2
            val endY2 = startY2 + eltH - ELT_MARGIN

            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint)
        }
    }
}