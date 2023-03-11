package com.example.gptbros

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val loginText: TextView = findViewById(R.id.register_text)

        loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registerButton: Button = findViewById(R.id.button_register)

        registerButton.setOnClickListener{
            performSignUp()
        }
    }


    private fun performSignUp() {
        val username = findViewById<EditText>(R.id.edit_text_username_register)
        val password = findViewById<EditText>(R.id.edit_text_password_register)

        // maybe check if text is empty

        val inputUsername = username.text.toString()
        val inputPassword = password.text.toString()

        auth.createUserWithEmailAndPassword(inputUsername, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
                    //Move on to next activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    //Alert User that signon has failed
                    Toast.makeText(baseContext, "Success",
                        Toast.LENGTH_SHORT).show()

                    addNewUser()

                } else {
                    //Alert User that signon has failed
                    Toast.makeText(baseContext, "Registration Failed",
                    Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(baseContext, "Error Occurred ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }
    }

    fun addNewUser(){

        val newUser = hashMapOf(
            "email" to auth.currentUser?.email.toString(),
            "username" to "temp_name",
            "Settings" to "idk",
            "userId" to 12345,
            "isPremium" to false,
        )

        // Add a new document with a generated ID
        db.collection("users/"+newUser.getValue("email"))
            .add(newUser)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }


}