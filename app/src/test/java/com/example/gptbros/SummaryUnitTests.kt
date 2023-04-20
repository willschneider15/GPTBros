package com.example.gptbros

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.example.gptbros.utils.SummaryAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SummaryUnitTests {

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

    val testResponse1 = "This is a test response."
    val testResponse2 = "Okay."
    private val openAI = OpenAI(BuildConfig.OPENAI_KEY)
    @OptIn(BetaOpenAI::class)
    fun chatRequest(testResponse: String) = ChatCompletionRequest(
        model = ModelId("gpt-3.5-turbo"),
        messages = listOf(
            ChatMessage(
                role = ChatRole.User,
                content = "Respond with this text exactly: " + testResponse
            )
        )
    )
    @OptIn(BetaOpenAI::class)
    suspend fun getSummmary(response:String): String {
        return SummaryAPI().makeSumItem(openAI.chatCompletion(chatRequest(response))).content
    }
    @Test
    fun testSmallGPTResponse() = runTest {
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            assertEquals(testResponse2, getSummmary(testResponse2))
        }
    }

    @Test
    fun testMidGPTResponse() = runTest {
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            assertEquals(testResponse1, getSummmary(testResponse1))
        }
    }

    @Test
    fun testEmpty() = runTest {
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            assertFalse(getSummmary("").isEmpty())
        }
    }

}