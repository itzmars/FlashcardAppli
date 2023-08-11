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
            val data = Intent() // create a new Intent, this is where we will put our data

            data.putExtra(
                "question",
                questionText.text.toString()

            ) // puts one string into the Intent, with the key as 'string1'

            data.putExtra(
                "answer",
                answerText.text.toString()
            )

            data.putExtra(
                "wrongAnswer1",
                wrongAns1.text.toString()
            )

            data.putExtra(
                "wrongAnswer2",
                wrongAns2.text.toString()
            )

            setResult(RESULT_OK, data) // set result code and bundle data for response
            if(questionText.text.toString() == "" || answerText.text.toString() == ""|| wrongAns1.text.toString() == ""|| wrongAns1.text.toString() == ""){
                Toast.makeText(applicationContext, "Must Both Enter Question And Answer", Toast.LENGTH_SHORT).show()
            }
            else{

                finish()


            }

        }
    }
}