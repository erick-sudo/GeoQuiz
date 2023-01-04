package com.kcire.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
//import android.view.View
//import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels

//Import view binding feature
import com.kcire.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("QuizViewModel","Got a QuizViewModel: $quizViewModel")

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex+1) % questionBank.size
            updateQuestion()
        }

        binding.previousButton.setOnClickListener {
            if(currentIndex>0) {
                currentIndex = (currentIndex-1) % questionBank.size
            }
            updateQuestion()
        }

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_LONG
        ).show()
    }

    private val updateQuestion: () -> Unit = {
        binding.questionTextView.setText(questionBank[currentIndex].textResId)
    }
}