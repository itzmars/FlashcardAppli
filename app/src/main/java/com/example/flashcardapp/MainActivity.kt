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


    lateinit var flashcardDatabase: FlashcardDatabase
    private var allFlashcards = mutableListOf<Flashcard>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currentCardDisplayedIndex = 0



        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        val addCard = findViewById<ImageView>(R.id.add_card)
        val nextCard = findViewById<ImageView>(R.id.next_card)
//        val editCard = findViewById<ImageView>(R.id.edit_card)
//
//        val guessAnswer1 = findViewById<TextView>(R.id.answer1)
//        val guessAnswer2 = findViewById<TextView>(R.id.answer2)
//        val guessAnswer3 = findViewById<TextView>(R.id.answer3)

        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()
        
        

        if (allFlashcards.size > 0) {
            findViewById<TextView>(R.id.flashcard_question).text = allFlashcards[0].question
            findViewById<TextView>(R.id.flashcard_answer).text = allFlashcards[0].answer
        }


        flashcardQuestion.setOnClickListener{
            flashcardAnswer.visibility = View.VISIBLE
            flashcardQuestion.visibility = View.INVISIBLE
        }

        flashcardAnswer.setOnClickListener{
            flashcardAnswer.visibility = View.INVISIBLE
            flashcardQuestion.visibility = View.VISIBLE
        }

        nextCard.setOnClickListener{
            if (allFlashcards.size == 0) {
                // return here, so that the rest of the code in this onClickListener doesn't execute
                return@setOnClickListener
            }

            currentCardDisplayedIndex++

            if(currentCardDisplayedIndex >= allFlashcards.size) {
                Snackbar.make(
                    findViewById<TextView>(R.id.flashcard_question), // This should be the TextView for displaying your flashcard question
                    "You've reached the end of the cards, going back to start.",
                    Snackbar.LENGTH_SHORT)
                    .show()
                currentCardDisplayedIndex = 0
            }

            // set the question and answer TextViews with data from the database
            allFlashcards = flashcardDatabase.getAllCards().toMutableList()
            val (question, answer) = allFlashcards[currentCardDisplayedIndex]

            findViewById<TextView>(R.id.flashcard_answer).text = answer
            findViewById<TextView>(R.id.flashcard_question).text = question

        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            val data: Intent? = result.data
            val extras = data?.extras

            if (extras != null) {

                Snackbar.make(findViewById(R.id.flashcard_question),
                    "Card successful created",
                    Snackbar.LENGTH_SHORT)
                    .show()

                val question = extras.getString("question")
                val answer = extras.getString("answer")


                Log.i("MainActivity", "question: $question")
                Log.i("MainActivity", "answer: $answer")


                flashcardQuestion.text = question
                flashcardAnswer.text = answer

                // Save newly created flashcard to database
                if (question != null && answer != null) {
                    flashcardDatabase.insertCard(Flashcard(question, answer))
                    // Update set of flashcards to include new card
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                } else {
                    Log.e("TAG", "Missing question or answer to input into database. Question is $question and answer is $answer")
                }

            }
            else {
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }

        }

        addCard.setOnClickListener {


            val intent = Intent(this, AddCardActivity::class.java)

            resultLauncher.launch(intent)

        }

//        editCard.setOnClickListener{
//            val intent = Intent(this, AddCardActivity::class.java)
//            intent.putExtra("questionTxt", flashcardQuestion.text);
//            intent.putExtra("answerTxt", flashcardAnswer.text);
//            intent.putExtra("wrongAnswer1", guessAnswer1.text);
//            intent.putExtra("wrongAnswer2", guessAnswer3.text);
//            resultLauncher.launch(intent)
//        }

//        guessAnswer1.setOnClickListener{
//
//        }
//
//        guessAnswer2.setOnClickListener{
//
//        }
//
//        guessAnswer3.setOnClickListener{
//
//        }



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