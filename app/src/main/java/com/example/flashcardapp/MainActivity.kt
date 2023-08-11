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
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        val addCard = findViewById<ImageView>(R.id.add_card)
        val editCard = findViewById<ImageView>(R.id.edit_card)

        val guessAnswer1 = findViewById<TextView>(R.id.answer1)
        val guessAnswer2 = findViewById<TextView>(R.id.answer2)
        val guessAnswer3 = findViewById<TextView>(R.id.answer3)


        flashcardQuestion.setOnClickListener{
            flashcardAnswer.visibility = View.VISIBLE
            flashcardQuestion.visibility = View.INVISIBLE
        }

        flashcardAnswer.setOnClickListener{
            flashcardAnswer.visibility = View.INVISIBLE
            flashcardQuestion.visibility = View.VISIBLE
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Snackbar.make(findViewById(R.id.flashcard_question),
                "Card successful created",
                Snackbar.LENGTH_SHORT)
                .show()

            val data: Intent? = result.data
            if (data != null) {

                val textQuestion = data.getStringExtra("question")
                val textAnswer = data.getStringExtra("answer")

                

                guessAnswer1.text = data.getStringExtra("wrongAnswer2")
                guessAnswer3.text = data.getStringExtra("wrongAnswer1")
                guessAnswer2.text = data.getStringExtra("answer")

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

            resultLauncher.launch(intent)

        }

        editCard.setOnClickListener{
            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra("questionTxt", flashcardQuestion.text);
            intent.putExtra("answerTxt", flashcardAnswer.text);
            intent.putExtra("wrongAnswer1", guessAnswer1.text);
            intent.putExtra("wrongAnswer2", guessAnswer3.text);
            resultLauncher.launch(intent)
        }

        guessAnswer1.setOnClickListener{

        }

        guessAnswer2.setOnClickListener{

        }

        guessAnswer3.setOnClickListener{

        }



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