package com.example.gptbros

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val registerText: TextView = findViewById(R.id.register_now_text)

        registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.button_login)

        loginButton.setOnClickListener{
            login()
        }
    }

    private fun login() {
        val username = findViewById<EditText>(R.id.edit_text_username_login)
        val password = findViewById<EditText>(R.id.edit_text_password_login)

        val inputUsername = username.text.toString()
        val inputPassword = password.text.toString()

        auth.signInWithEmailAndPassword(inputUsername, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    //Move on to next activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    //Alert User that signon has failed
                    Toast.makeText(baseContext, "Success",
                        Toast.LENGTH_SHORT).show()

                } else {
                    //Alert User that signon has failed
                    Toast.makeText(baseContext, "Login Failed",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(baseContext, "Error Occurred ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}