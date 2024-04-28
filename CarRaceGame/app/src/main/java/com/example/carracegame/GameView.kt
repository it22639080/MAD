package com.example.carracegame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(var c : Context,var gameTask:GameTask):View(c){
    // Paint object for drawing on canvas
    private var myPaint: Paint?=null
    // Game variables

    private var speed =1
    private var time=0
    private var score =0
    private var myRabbitPosition =0

    private val otherRabbit =ArrayList<HashMap<String,Any>>()

    var viewWidth=0
    var viewHeight=0
   init{
       myPaint = Paint()
   }
    // Draw the game elements on the canvas
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth=this.measuredWidth
        viewHeight=this.measuredHeight
        if(time % 700<10+speed ){
            val map = HashMap<String,Any>()
            map["lane"]=(0..2).random()
            map["startTime"]=time
            otherRabbit.add(map)
        }
        // Update time and calculate speed
        time= time +10+speed
        val rabbitWidth = viewWidth/5
        val rabbitHeight = rabbitWidth+10
        myPaint!!.style = Paint.Style.FILL

        val d= resources.getDrawable(R.drawable.rabit,null)
        d.setBounds(
            myRabbitPosition * viewWidth / 3 + viewWidth/ 15 +25 ,
            viewHeight-2 - rabbitHeight,
            myRabbitPosition * viewWidth / 3 +viewWidth /15 +rabbitWidth - 25 ,
            viewHeight - 2
        )
        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore = 0
        for(i in otherRabbit.indices){
            try{
                val rabbit = otherRabbit[i]["lane"] as Int * viewWidth/3 +viewWidth/ 15
                var bomb = time - otherRabbit[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.bomb,null)

                d2.setBounds(
                    rabbit + 25, bomb-rabbitHeight,rabbit + rabbitWidth - 25, bomb
                )
                d2.draw(canvas!!)
                if(otherRabbit[i]["lane"] as Int == myRabbitPosition){
                    if(bomb > viewHeight - 2 -rabbitHeight
                        && bomb < viewHeight-2){
                       gameTask.closeGame(score)
                    }
                }
                if(bomb > viewHeight + rabbitHeight)
                {
                    otherRabbit.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score/8)
                    if(score > highScore){
                        highScore = score
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
        // Draw score and speed on canvas
        myPaint!!.color =Color.WHITE
        myPaint!!.textSize =40f
        canvas.drawText("Score: $score",80f,80f,myPaint!!)
        canvas.drawText("Speed: $speed",380f,80f,myPaint!!)
        invalidate()
    }
    // Reset the game state
    fun resetGame() {
        // Reset game state
        score = 0
        time = 0
        speed = 1
        myRabbitPosition = 0
        otherRabbit.clear()
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->{
                val x1 = event.x
                if(x1 <viewWidth/2){
                    if(myRabbitPosition>0){
                        myRabbitPosition--
                    }
                }
                if(x1 >viewWidth/2){
                    if(myRabbitPosition<2){
                        myRabbitPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP->{

            }

        }
        return true
    }
}