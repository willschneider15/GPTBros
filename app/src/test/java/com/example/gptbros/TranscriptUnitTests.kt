package com.example.gptbros

import android.content.Context
import android.os.Build.VERSION_CODES.Q
import android.os.Environment
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gptbros.model.GptBrosRepository
import com.example.gptbros.ui.MainActivity
import com.example.gptbros.ui.home.HomeFragment
import com.example.gptbros.ui.home.HomeViewModel
import io.ktor.util.reflect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Q])
class TranscriptUnitTests {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val hvm = HomeViewModel()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        GptBrosRepository.initialize(context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    //Change these vars when testing audio files.
    val testRecording1 = "This is a test"
    val testFP1 = "sampledata/test1.wav"
    val testRecording2 = "Hi I am Anthony, this is a test of the app"
    val testFP2 = "sampledata/test2.wav"



    private suspend fun getTS(rec:String): String {
        val filePath : String = rec
        return hvm.transcribeRecording(context, filePath)
    }


    @Test
    fun testSmallTResponse() = runTest {
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            Assert.assertEquals(testRecording1, getTS(testFP1))
        }
    }

    @Test
    fun testMidTResponse() = runTest {
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            Assert.assertEquals(testRecording2, getTS(testFP2))
        }
    }
}