package com.example.gptbros

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Repository {
    val db = Firebase.firestore

    // User Example
    val newUser = hashMapOf(
        "username" to "Anthony",
        "password" to "Bouie",
        "email" to "bouie.6@osu.edu",
        "Settings" to "idk",
        "userId" to 12345,
        "isPremium" to false,
        "audioSession" to hashMapOf(
            "sessionId" to 1,
            "audiofile" to "file",
            "transcription" to "trans_file",
            "summary" to "sum_file",
            "dateTimeStart" to "1/1/22-1:22",
            "dateTimeEnd" to "1/1/22-1:56",
        )
    )

    init {
        db.
    }

    fun addNewUser(){

        val addNewUser = hashMapOf(
            "username" to "Anthony",
            "password" to "Bouie",
            "email" to "bouie.6@osu.edu",
            "Settings" to "idk",
            "userId" to 12345,
            "isPremium" to false,
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(newUser)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    fun addSession(){
        val audioSession = hashMapOf(
        "sessionId" to 1,
        "audiofile" to "file",
        "transcription" to "trans_file",
        "summary" to "sum_file",
        "dateTimeStart" to "1/1/22-1:22",
        "dateTimeEnd" to "1/1/22-1:56",
        )

        // Add a new document with a generated ID
        db.collection("users/")
            .add(newUser)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

}