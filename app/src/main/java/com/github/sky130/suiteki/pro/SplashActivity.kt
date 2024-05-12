package com.github.sky130.suiteki.pro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 2000 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
           
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() 
        }, SPLASH_DISPLAY_LENGTH.toLong())
    //(⁠･⁠ω⁠･⁠)⁠つ⁠⊂⁠(⁠･⁠ω⁠･⁠)
    }
}
