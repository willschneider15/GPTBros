package com.example.gptbros

import android.os.Environment
import com.example.gptbros.ui.home.HomeFragment
import com.example.gptbros.ui.home.HomeViewModel
import io.ktor.util.reflect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TranscriptUnitTests {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    val testRecording1 = "This is a test"
    val testFP1 = "test1.wav"
    val testRecording2 = "Hi I am Anthony, this is a test of the app"
    val testFP2 = "test2.wav"


    private suspend fun getTS(rec:String): String {
        var hvm = HomeViewModel()
        var context = HomeFragment().context
        val filePath : String = Environment.getExternalStorageDirectory().absolutePath + "/"+ rec
        return context?.let { hvm.transcribeRecording(it, filePath) }!!
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