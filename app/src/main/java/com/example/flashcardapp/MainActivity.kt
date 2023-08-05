package com.example.flashcardapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    private var isShowingAnswers = true
    @RequiresApi(Build.VERSION_CODES.M)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer1 = findViewById<TextView>(R.id.answer1)
        val flashcardAnswer2 = findViewById<TextView>(R.id.answer2)
        val flashcardAnswer3 = findViewById<TextView>(R.id.answer3)
        val toggleChoiceView =  findViewById<ImageView>(R.id.eye_off_lined)



//        flashcardQuestion.setOnClickListener{
//            flashcardAnswer.visibility = View.VISIBLE
//            flashcardQuestion.visibility = View.INVISIBLE
//        }
//
//        flashcardAnswer.setOnClickListener{
//            flashcardAnswer.visibility = View.INVISIBLE
//            flashcardQuestion.visibility = View.VISIBLE
//        }

        flashcardAnswer1.setOnClickListener{
            flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.red, null))
            flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.green, null))
        }

        flashcardAnswer2.setOnClickListener{
            flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.red, null))
            flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.green, null))
        }

        flashcardAnswer3.setOnClickListener{
            flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.green, null))
        }

        toggleChoiceView.setOnClickListener{
            isShowingAnswers = !isShowingAnswers

            if(!isShowingAnswers) {
                toggleChoiceView.setImageResource(R.drawable.eye_lined)
                hideAnswers()
            }
            else{
                toggleChoiceView.setImageResource(R.drawable.eye_off)
                showAnswers()
            }
        }




    }

    private fun showAnswers() {
        findViewById<TextView>(R.id.answer1).visibility = View.VISIBLE
        findViewById<TextView>(R.id.answer2).visibility = View.VISIBLE
        findViewById<TextView>(R.id.answer3).visibility = View.VISIBLE
    }

    private fun hideAnswers() {
        findViewById<TextView>(R.id.answer1).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.answer2).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.answer3).visibility = View.INVISIBLE
    }


}