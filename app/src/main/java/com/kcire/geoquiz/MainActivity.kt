package com.kcire.geoquiz

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
//import android.view.View
//import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout

//Import view binding feature
import com.kcire.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        //Handle the result
        if(result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

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
            val intent = CheatActivity.newIntent(this@MainActivity, quizViewModel.currentQuestionAnswer)
            //startActivity(intent)
            cheatLauncher.launch(intent)
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

        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgement_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
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