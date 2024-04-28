package com.example.carracegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(),GameTask {
    lateinit var rootLayout : LinearLayout
    lateinit var startBtn : Button
    lateinit var mGameView : GameView
    lateinit var score: TextView
    lateinit var buttun:Button
    lateinit var btn:Button
    lateinit var highScoreTextView: TextView
    var highScore = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        buttun=findViewById(R.id.button)
        btn=findViewById(R.id.btn)
        highScoreTextView = findViewById(R.id.highScore)
        // Initialize the GameView
        mGameView=GameView(this,this)

        startBtn.setOnClickListener {

            // Set background image for the game view
            mGameView.setBackgroundResource(R.drawable.bg1)
            rootLayout.addView(mGameView)
            // Hide UI elements
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
            mGameView.resetGame()
            buttun.visibility=View.GONE
            btn.visibility=View.GONE
            highScoreTextView.visibility=View.GONE
            score.text = "Score: 0"
        }
    }

    override fun closeGame(mScore: Int) {
        // Update high score if needed
        if (mScore > highScore) {
            highScore = mScore
            highScoreTextView.text = "High Score: $highScore"
        }
        // Show UI elements
        score.text="Score :$mScore"
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        highScoreTextView.visibility=View.VISIBLE
    }
}