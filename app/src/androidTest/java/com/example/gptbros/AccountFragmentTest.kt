package com.example.gptbros

import android.content.Context
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gptbros.database.GptBrosDatabase
import com.example.gptbros.model.*
import com.example.gptbros.ui.account.AccountFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AccountFragmentTest {
    private lateinit var scenario: FragmentScenario<AccountFragment>
    private lateinit var gptBrosRepository: GptBrosRepository
    private val sessionId : UUID = UUID.randomUUID()
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val session = Session(sessionId, Date(), Stage.RECORDING, "Loading...", "Loading...")
    private val recording = Recording(UUID.randomUUID(), sessionId, Status.NOT_STARTED)
    private val summary = Summary(UUID.randomUUID(), sessionId, Status.NOT_STARTED, "Transcription is still recording")
    private val transcription = Transcription(UUID.randomUUID(), sessionId, Status.IN_PROGRESS, "Loading...")

    @Before
    fun setup() {
        GptBrosRepository.initialize(context)
        gptBrosRepository = GptBrosRepository.get()
        gptBrosRepository.setDb(Room.inMemoryDatabaseBuilder(context, GptBrosDatabase::class.java).build())
        gptBrosRepository.insertSessionAndChildren(session, recording, transcription, summary)
        scenario = launchFragmentInContainer<AccountFragment>(
            fragmentArgs = bundleOf("sessionId" to sessionId)
        )
        scenario.moveToState(Lifecycle.State.STARTED)
    }


    @Test fun testInitialState() {
        Log.d("AccountFragmentTest", "test start")
        onView(withId(R.id.text_dashboard)).check(matches(withText("Transcription is still recording")))
    }

    @Test fun testUpdateState() {
        Log.d("AccountFragmentTest", "test start")
        onView(withId(R.id.text_dashboard)).check(matches(withText("Transcription is still recording")))
        gptBrosRepository.updateSummary(summary.copy(content = "This is the complete summary!"))
        Thread.sleep(500); //Sleep to give a chance for the write to go through
        onView(withId(R.id.text_dashboard)).check(matches(withText("This is the complete summary!")))
    }


}