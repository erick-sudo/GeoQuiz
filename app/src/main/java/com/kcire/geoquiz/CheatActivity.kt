package com.kcire.geoquiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kcire.geoquiz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}