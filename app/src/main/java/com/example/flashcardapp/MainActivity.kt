package com.example.flashcardapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        val addCard = findViewById<ImageView>(R.id.add_card)



        flashcardQuestion.setOnClickListener{
            flashcardAnswer.visibility = View.VISIBLE
            flashcardQuestion.visibility = View.INVISIBLE
        }

        flashcardAnswer.setOnClickListener{
            flashcardAnswer.visibility = View.INVISIBLE
            flashcardQuestion.visibility = View.VISIBLE
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            // This code is executed in StartingActivity after we come back from EndingActivity

            // This extracts any data that was passed back from EndingActivity
            val data: Intent? = result.data
            if (data != null) { // Check that we have data returned
                val textQuestion = data.getStringExtra("question") // 'string1' needs to match the key we used when we put the string in the Intent
                val textAnswer = data.getStringExtra("answer")

                flashcardQuestion.text = textQuestion
                flashcardAnswer.text = textAnswer

                Log.i("MainActivity", "question: $textQuestion")
                Log.i("MainActivity", "answer: $textAnswer")
            }
            else {
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }

        }

        addCard.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            // Launch EndingActivity with the resultLauncher so we can execute more code
            // once we come back here from EndingActivity
            resultLauncher.launch(intent)
        }


//
//        flashcardAnswer1.setOnClickListener{
//            flashcardAnswer1.setBackgroundColor(getResources().getColor(R.color.red, null))
//            flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.green, null))
//        }
//
//        flashcardAnswer2.setOnClickListener{
//            flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.red, null))
//            flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.green, null))
//        }
//
//        flashcardAnswer3.setOnClickListener{
//            flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.green, null))
//        }
//
//        toggleChoiceView.setOnClickListener{
//            isShowingAnswers = !isShowingAnswers
//
//            if(!isShowingAnswers) {
//                toggleChoiceView.setImageResource(R.drawable.eye_lined)
//                hideAnswers()
//            }
//            else{
//                toggleChoiceView.setImageResource(R.drawable.eye_off)
//                showAnswers()
//            }
//        }
//



    }

//    private fun showAnswers() {
//        findViewById<TextView>(R.id.answer1).visibility = View.VISIBLE
//        findViewById<TextView>(R.id.answer2).visibility = View.VISIBLE
//        findViewById<TextView>(R.id.answer3).visibility = View.VISIBLE
//    }
//
//    private fun hideAnswers() {
//        findViewById<TextView>(R.id.answer1).visibility = View.INVISIBLE
//        findViewById<TextView>(R.id.answer2).visibility = View.INVISIBLE
//        findViewById<TextView>(R.id.answer3).visibility = View.INVISIBLE
//    }


}