package com.example.gptbros

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.room.Room
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.GrantPermissionRule
import com.example.gptbros.database.GptBrosDatabase
import com.example.gptbros.model.*
import com.example.gptbros.ui.home.HomeFragment
import com.google.common.collect.Iterables.any
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)

class HomeFragmentTest {
    private lateinit var scenario: FragmentScenario<HomeFragment>
    private lateinit var gptBrosRepository: GptBrosRepository
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setup() {
        GptBrosRepository.initialize(context)
        gptBrosRepository = GptBrosRepository.get()
        gptBrosRepository.setDb(Room.inMemoryDatabaseBuilder(context, GptBrosDatabase::class.java).build())
        scenario = launchFragmentInContainer<HomeFragment>()
        scenario.moveToState(Lifecycle.State.STARTED)
    }



    @get:Rule
    val permissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
    )
    @Test
    fun testRecord() {
        Log.d("HomeFragmentTest", "test start")
        Espresso.onView(ViewMatchers.withId(R.id.recording_catagory)).perform(typeText("Ui test class"));
        Espresso.onView(ViewMatchers.withId(R.id.recording_name)).perform(typeText("Ui test class name"));
        Espresso.onView(ViewMatchers.withId(R.id.buttonRecord)).perform(click());
        Thread.sleep(500);
        Espresso.onView(ViewMatchers.withId(R.id.buttonRecord)).perform(click());
        Thread.sleep(1000);
        runBlocking {
            suspend {
                withTimeoutOrNull(1000) {
                    gptBrosRepository.getSessions().collect { value ->
                        if(value.size > 0){
                            assert(any(value){
                                it.className.equals("Ui test class")
                            })
                            return@collect
                        }
                    }
                }
            }.invoke()


        }
    }


}



