package com.example.gptbros
import android.content.ContentValues.TAG
import android.util.Log
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gptbros.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_account, R.id.navigation_folder))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        Log.d("MainActivity","onCreate was called");
    }

    override fun onStart() {
        super.onStart();
        Log.d("MainActivity","OnCreate was called");
    }

    override fun onResume() {
        super.onResume();
        Log.d("MainActivity","onResume was called");
    }

    override fun onPause() {
        super.onPause();
        Log.d("MainActivity","onPause was called");
    }

    override fun onStop() {
        super.onStop();
        Log.d("MainActivity","onStop was called");
    }

    override fun onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy was called");
    }
}