package br.com.eaglecode.desafiowebservice.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.eaglecode.desafiowebservice.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)


        btnSave.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        tvSignin.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }
    }
}