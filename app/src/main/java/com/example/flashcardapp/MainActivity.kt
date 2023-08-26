package com.example.flashcardapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)

    lateinit var flashcardDatabase: FlashcardDatabase
    private lateinit var cardToEdit: Flashcard
    private var allFlashcards = mutableListOf<Flashcard>()
    private var currentCardDisplayedIndex = 0
    private var countDownTimer: CountDownTimer? = null
    var isShowingAnswer = false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        val addCard = findViewById<ImageView>(R.id.add_card)
        val nextCard = findViewById<ImageView>(R.id.next_card)
        val deleteCard = findViewById<ImageView>(R.id.delete_card)
        val editCard = findViewById<ImageView>(R.id.edit_card)

        // Calculate the translation values
        val fromXDelta = 0f // Start at the current position (no translation)
        val toXDelta = -100f // Translate 100 pixels to the left (adjust as needed)
        val fromYDelta = 0f // No vertical translation
        val toYDelta = 0f // No vertical translation


        val guessAnswer1 = findViewById<TextView>(R.id.answer1)
        val guessAnswer2 = findViewById<TextView>(R.id.answer2)
        val guessAnswer3 = findViewById<TextView>(R.id.answer3)




        flashcardDatabase = FlashcardDatabase(this)
        flashcardDatabase.initFirstCard()
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()




        countDownTimer = object : CountDownTimer(16000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                findViewById<TextView>(R.id.timer).text = "" + millisUntilFinished / 1000
            }

            override fun onFinish() {
            }

        }

        startTimer()


        if (allFlashcards.size > 0) {
            flashcardQuestion.text = allFlashcards[0].question
            flashcardAnswer.text = allFlashcards[0].answer
            guessAnswer1.text = allFlashcards[0].wrongAnswer1
            guessAnswer2.text = allFlashcards[0].answer
            guessAnswer3.text = allFlashcards[0].wrongAnswer2

        }


        flashcardQuestion.setOnClickListener{
            findViewById<View>(R.id.flashcard_question).cameraDistance = 25000F
            findViewById<View>(R.id.flashcard_answer).cameraDistance = 25000F

            flashcardQuestion.animate()
                .rotationY(90F)
                .setDuration(200)
                .withEndAction(
                    Runnable {
                        flashcardQuestion.visibility = View.INVISIBLE
                        findViewById<View>(R.id.flashcard_answer).visibility = View.VISIBLE
                        // second quarter turn
                        findViewById<View>(R.id.flashcard_answer).rotationY = -90f
                        findViewById<View>(R.id.flashcard_answer).animate()
                            .rotationY(0f)
                            .setDuration(200)
                            .start()
                    }
                ).start()

        }

        flashcardAnswer.setOnClickListener{

            findViewById<View>(R.id.flashcard_answer).cameraDistance = 25000F
            findViewById<View>(R.id.flashcard_question).cameraDistance = 25000F

            flashcardAnswer.animate()
                .rotationY(90F)
                .setDuration(200)
                .withEndAction(
                    Runnable {
                        flashcardAnswer.visibility = View.INVISIBLE
                        findViewById<View>(R.id.flashcard_question).visibility = View.VISIBLE
                        // second quarter turn
                        findViewById<View>(R.id.flashcard_question).rotationY = -90f
                        findViewById<View>(R.id.flashcard_question).animate()
                            .rotationY(0f)
                            .setDuration(200)
                            .start()
                    }
                ).start()

        }


        nextCard.setOnClickListener{

            if (allFlashcards.size == 0) {
                // return here, so that the rest of the code in this onClickListener doesn't execute
                return@setOnClickListener
            }

            startTimer()

            fun getRandomNumber(minNumber: Int, maxNumber: Int): Int {
                return (minNumber..maxNumber).random() // generated random from 0 to 10 included
            }

            currentCardDisplayedIndex = getRandomNumber(0, allFlashcards.size - 1)

            val currentCardOutAnimation = TranslateAnimation(0f, -flashcardQuestion.width.toFloat(), 0f, 0f)
            currentCardOutAnimation.duration = 500

                    currentCardOutAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        // this method is called when the animation first starts
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        // this method is called when the animation is finished playing
                        showNextCard()
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                        // we don't need to worry about this method
                    }
                })
            flashcardQuestion.startAnimation(currentCardOutAnimation)



            // set the question and answer TextViews with data from the database
            allFlashcards = flashcardDatabase.getAllCards().toMutableList()
            val (question, answer, wrongAns1, wrongAns2) = allFlashcards[currentCardDisplayedIndex]




            flashcardQuestion.text = question
            flashcardAnswer.text = answer
            guessAnswer1.text = wrongAns1
            guessAnswer2.text = answer
            guessAnswer3.text = wrongAns2
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
                val wrongAns1 = extras.getString("wrong1")
                val wrongAns2 = extras.getString("wrong2")


                Log.i("MainActivity", "question: $question")
                Log.i("MainActivity", "answer: $answer")


                flashcardQuestion.text = question
                flashcardAnswer.text = answer
                guessAnswer1.text = wrongAns1
                guessAnswer2.text = answer
                guessAnswer3.text = wrongAns2


                // Save newly created flashcard to database
                if (question != null && answer != null) {
                    flashcardDatabase.insertCard(Flashcard(question, answer, wrongAns1, wrongAns2))
                    // Update set of flashcards to include new card
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                }
                else {
                    Log.e("TAG", "Missing question or answer to input into database. Question is $question and answer is $answer")
                }
            }
            else {
                Log.i("MainActivity", "Returned null data from AddCardActivity")
            }
        }


        val editResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data

                if (data != null) { // Check that we have data returned
                    // grab the data passed from AddCardActivity
                    // set the TextViews to show the EDITED question and answer

                    val question = data.extras!!.getString("question")
                    val answer = data.extras!!.getString("answer")

                    val wrongAns1 = data.extras!!.getString("wrong1")
                    val wrongAns2 = data.extras!!.getString("wrong2")

                    flashcardQuestion.text = question
                    flashcardAnswer.text = answer
                    guessAnswer1.text = wrongAns1
                    guessAnswer2.text = answer
                    guessAnswer3.text = wrongAns2

                    if (question != null && answer != null) {

                        cardToEdit.question = question
                        cardToEdit.answer = answer

                        flashcardDatabase.updateCard(cardToEdit)

                        allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                    }
                    else {
                        Log.e(
                            "TAG",
                            "Missing question or answer to input into database. Question is $question and answer is $answer"
                        )
                    }
                }
                else {
                    Log.i("MainActivity", "Returned null data from AddCardActivity")
                }
            }
        }


       deleteCard.setOnClickListener{
           val flashcardQuestionToDelete = flashcardQuestion.text.toString()
           flashcardDatabase.deleteCard(flashcardQuestionToDelete)

           if (currentCardDisplayedIndex > 0){
               currentCardDisplayedIndex--
           }
           else{
               currentCardDisplayedIndex = 0
           }


           allFlashcards = flashcardDatabase.getAllCards().toMutableList()

           if (allFlashcards.size <= 0){
               findViewById<TextView>(R.id.flashcard_question).text = "Add a flashcard"
           }
           else{
               val (question, answer) = allFlashcards[currentCardDisplayedIndex]

               flashcardQuestion.text = question
               flashcardAnswer.text = answer

           }
       }

        addCard.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }


        editCard.setOnClickListener{
            for(flashcard in allFlashcards){
                if(flashcard.question == flashcardQuestion.text){
                    cardToEdit = Flashcard(flashcard.question, flashcard.answer,flashcard.wrongAnswer1, flashcard.wrongAnswer2, flashcard.uuid )
                }
            }

            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra("questionTxt", flashcardQuestion.text);
            intent.putExtra("answerTxt", guessAnswer2.text);
            intent.putExtra("wrongAnswer1", flashcardAnswer.text);
            intent.putExtra("wrongAnswer2", guessAnswer3.text);
            editResultLauncher.launch(intent)
        }

        guessAnswer1.setOnClickListener{
            findViewById<View>(R.id.answer1).setBackgroundColor(resources.getColor(R.color.red, null))
            findViewById<View>(R.id.answer2).setBackgroundColor(resources.getColor(R.color.green, null))
        }

        guessAnswer2.setOnClickListener{
            findViewById<View>(R.id.answer2).setBackgroundColor(resources.getColor(R.color.green, null))
        }

        guessAnswer3.setOnClickListener{
            findViewById<View>(R.id.answer3).setBackgroundColor(resources.getColor(R.color.red, null))
            findViewById<View>(R.id.answer2).setBackgroundColor(resources.getColor(R.color.green, null))
        }



    }

    private fun showNextCard() {
        val currentCardIndex = currentCardDisplayedIndex
        val nextCardIndex = (currentCardIndex + 1) % allFlashcards.size
        val nextCard = allFlashcards[nextCardIndex]

        findViewById<TextView>(R.id.flashcard_question).text = nextCard.question
        findViewById<TextView>(R.id.flashcard_answer).text = nextCard.answer
        findViewById<TextView>(R.id.answer1).text = nextCard.wrongAnswer1
        findViewById<TextView>(R.id.answer2).text = nextCard.answer
        findViewById<TextView>(R.id.answer3).text = nextCard.wrongAnswer2




        findViewById<TextView>(R.id.flashcard_answer).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.flashcard_question).visibility = View.VISIBLE
        isShowingAnswer = false

        currentCardDisplayedIndex = nextCardIndex


    }

    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer?.start()
    }


}