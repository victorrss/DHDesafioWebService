package br.com.eaglecode.desafiowebservice.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.eaglecode.desafiowebservice.R
import kotlinx.android.synthetic.main.activity_login.*

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        btnSave.setOnClickListener { onBackPressed() }
    }
}