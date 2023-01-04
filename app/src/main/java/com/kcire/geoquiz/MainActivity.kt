package com.kcire.geoquiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
//import android.view.View
//import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout

//Import view binding feature
import com.kcire.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("QuizViewModel","Got a QuizViewModel: $quizViewModel")

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNextQuestion()
            updateQuestion()
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToNextQuestion(1)
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener {
            //Start cheat activity
            val intent = Intent(this, CheatActivity::class.java)
            startActivity(intent)
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
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Snackbar.make(
            binding.root,
            messageResId,
            Snackbar.LENGTH_SHORT
        ).also {
            it.setTextColor(this.getColor(R.color.snackbar_bg))
            it.setBackgroundTint(Color.DKGRAY)
        }.show()

//        Toast.makeText(
//            this,
//            messageResId,
//            Toast.LENGTH_SHORT
//        ).show()
    }

    private val updateQuestion: () -> Unit = {
        binding.questionTextView.setText(quizViewModel.currentQuestionText)
    }
}