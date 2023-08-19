package com.example.flashcardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val saveCard = findViewById<ImageView>(R.id.save_card)
        val cancelCard = findViewById<ImageView>(R.id.cancel_card)
        val questionText = findViewById<EditText>(R.id.edit_text_question)
        val answerText = findViewById<EditText>(R.id.editTextAnswer)
        val wrongAns1= findViewById<EditText>(R.id.edit_text_wrong_answer1)
        val wrongAns2= findViewById<EditText>(R.id.edit_text_wrong_answer2)


        questionText.setText(intent.getStringExtra("questionTxt"))
        answerText.setText(intent.getStringExtra("answerTxt"))
        wrongAns1.setText(intent.getStringExtra("wrongAnswer1"))
        wrongAns2.setText(intent.getStringExtra("wrongAnswer2"))


        cancelCard.setOnClickListener {
            finish()
        }


        saveCard.setOnClickListener {
            val data = Intent()

            val question = questionText.text.toString()
            val answer = answerText.text.toString()
            val wrongAnswer = wrongAns1.text.toString()
            val wrongAnswer2 = wrongAns2.text.toString()

            if (question.isNotEmpty() && answer.isNotEmpty() ) {

                data.putExtra("question", question)
                data.putExtra("answer", answer)
                data.putExtra("wrong1", wrongAnswer)
                data.putExtra("wrong2", wrongAnswer2)

                setResult(RESULT_OK, data) // set result code and bundle data for response
                finish() // closes the activity, pass data to parent
            }
            else {
                // show an error message in a Toast
                Toast.makeText(
                    applicationContext,
                    "Must enter both Question and Answers!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}