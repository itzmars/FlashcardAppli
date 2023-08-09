package com.example.flashcardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val saveCard = findViewById<ImageView>(R.id.save_card)
        val cancelCard = findViewById<ImageView>(R.id.cancel_card)




        cancelCard.setOnClickListener {
            finish()
        }


        saveCard.setOnClickListener {
            val data = Intent() // create a new Intent, this is where we will put our data
            val question_text = findViewById<EditText>(R.id.editTextQuestion)
            val answer_text = findViewById<EditText>(R.id.editTextAnswer)

            data.putExtra(
                "question",
                question_text.text.toString()

            ) // puts one string into the Intent, with the key as 'string1'

            data.putExtra(
                "answer",
                answer_text.text.toString()
            ) // puts another string into the Intent, with the key as 'string2

            setResult(RESULT_OK, data) // set result code and bundle data for response

            finish() // closes this activity and pass data to the original activity that launched this activity
        }
    }
}