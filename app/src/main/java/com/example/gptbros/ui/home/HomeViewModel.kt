package com.example.gptbros.ui.home


import android.content.ContentValues
import android.content.ContentValues.TAG

import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.aallam.openai.api.BetaOpenAI
import com.example.gptbros.model.api.SummaryItem
import com.example.gptbros.model.api.TranscriptItem
import com.example.gptbros.utils.SummaryAPI
import com.example.gptbros.utils.TranscriptAPI
import com.google.cloud.speech.v1.SpeechClient
import com.google.protobuf.ByteString
import kotlinx.coroutines.launch
import java.io.File

import com.example.gptbros.model.*
import com.example.gptbros.utils.AudioManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID


@OptIn(BetaOpenAI::class)
class HomeViewModel : ViewModel() {

    lateinit var speechClient: SpeechClient
    val summaryAPI = SummaryAPI()
    private val transcriptAPI = TranscriptAPI()

    private val gptBrosRepository = GptBrosRepository.get()
    private val _text = MutableLiveData<String>().apply {
        value = "Begin the recording at the start of class"
    }
    private lateinit var sessionUUID : UUID
    val text: LiveData<String> = _text
    val isRecording = false

    init {
    }


    fun callAPIs(file: File){
        viewModelScope.launch {
            try {
//                val transcriptItem: TranscriptItem = transcriptAPI.fetchTranscript(ByteString.copyFrom(file.readBytes()), speechClient)
//                val transcript = transcriptItem.content // Use later for display
                val transcript = "what's a mini bass now so you going through all this again from the idea of linear regression I mean we have a linear neuron we want to look at this from a more General perspective so suppose I don't have a linear activation talk before she returned it doesn't activate update\n" +
                        " any ideas\n" +
                        " doesn't impact the wealthy\n" +
                        " yes\n" +
                        " couple condos\n" +
                        " no no\n" +
                        " do you know where I'm updating my legs based on going the opposite direction of McGrady\n" +
                        " however the gradient will change right by the rivet is and do chain rule\n" +
                        " because it applies if you still have this problem of you know machine learning of his words the chain rule come in come in handy so now\n" +
                        " 44 Pacific way I have to incorporate the chain rule so I have my function why again if I look at this error\n" +
                        " this is domantas why we're now why is wood a 5y transpose at to get them using vectors notation for 4 y\n" +
                        " why is the spiesman full of minus X derivative of this error respect the W is first to the root of this error respective why do that then why do respect to be was it the input there then if it's rude of me with respect to W derivative\n" +
                        " if I were to do that I'll get this Express in here\n" +
                        " so the Parsons derivative W is -8 - y x\n" +
                        " so this here is different here - D1 is why is the first derivative of my are affected by this is Preston. Why is this why and I just didn't know that partial derivative of y has five promise me you haven't taken a derivative yet we don't know how to put that day is this yet-to-be some potential respected at Pacific W and this case is XIII event\n" +
                        " listen to text Ivan the three parts of the river that is multiplied together to get my chain rule\n" +
                        " this demon is why I didn't know that Lord b e\n" +
                        " so I have been saying error for capital e but are really if my clock function or loss function I want to go to clarify that he is my sort of error for specific input lowercase e s Define demonic wife as soon as lowercase D\n" +
                        " and then once I do this essentially if I'm looking at the stochastic approach to this my way update just becomes wi event + 1 equals W Ivan Pineda Delta van, so\n" +
                        " this is known as the Delta rule in this case here where is Tennessee I just fine as my are lower case even x 55 of the event\n" +
                        " Intuit\n" +
                        " next class we get to multiplayer Francesca on fully why we to find it until 2 will become clear but why we why do we need to find out this weekend to come see you on Thursday\n" +
                        " but since he now belt is just my lowercase e x a derivative of my activation phone\n" +
                        "so just look at this activation phone so one common activation function is logistics is 1/1 plus the exponential of negative maybe were a constant value and if you would have known that was one of the popular activation phone activation function for various problems\n" +
                        " soap activation function as a substitute migrated for this problem with this event buy Prime on Ducks event but for the activation function by partial derivative takes on a specific form of derivative of his activation function for a sigmoid is this a x p activation time to 21 - the activation phone\n" +
                        " so you can tell me you can derive that the ceiling but this is something that's no now for this liquid activation function\n" +
                        " so if I were to plot this part of the river live\n" +
                        " I'll get this here\n" +
                        " so this is this is the partial derivative derivative of my activation phone to respect your be almost Galaxy like curve as a function of E\n" +
                        " what does this mean about my update\n" +
                        " based on his activation phone\n" +
                        " what is this mean how does a how does magic eraser potentially using it activation phone updates\n" +
                        " Daniel airsofter near like\n" +
                        " sort of the middle near the origin they're going to be updated the most because that's where the greeting is dismissed and for the way there\n" +
                        " not really up to it right this is my fight from it be what does this mean\n" +
                        " I need to update my way I'm getting an error when I minimize a certain cost function this gradient is based on a partial derivative\n" +
                        " but the town's with this activation function is that only sort of smaller values of V gave me for the a higher value has bestowed of goes out in the morning I get a positive just derivative is closer and closer to 0\n" +
                        " so I speak is so either more negative or positive\n" +
                        " since it has become zero fizzy means my wings aren't really updating\n" +
                        " so that's a problem and this is one reason why\n" +
                        " the fuel that moved away from this activate\n" +
                        " because of the issue of the vanishing gradient\n" +
                        " eventually your gradient\n" +
                        " go toward zero\n" +
                        " and you're not up to your weight but you do have it's off your phone so\n" +
                        " description of of the of that this is a Prelude\n" +
                        " I'm wondering like the biggest choose this sort of like at random cuz this just seems like the more productive like this just seems like.\n" +
                        " Like why would you want to like why when you're far away from your desired whatever whatever your is desired then why would you want to update your weight class or is that it wasn't just like they didn't know and I didn't\n" +
                        " I'm on my way so I have an error I'm not done yet so my desired is not matching when I actually get out how far away that is I'm not sure I just know that I haven't turned off and on\n" +
                        " this is saying essentially my update go is going to need to restrict my activities as potential activate the potential am attitude needs to be small in order to really update\n" +
                        " hope you know ideally restricted guy of your weight so your activate some potential get smaller\n" +
                        " okay that death is just a basic idea in practice really busy this goes to zero\n" +
                        " forget to bake whole 20 so you remember in the part of this idea is that is related to come kick his probability you can try to use your approach specifically why they chose\n" +
                        "now we talked about this lead to things for updating way to interpret its updating wait for linear regression or linear Square algorithm\n" +
                        " and if you look at these two expressions\n" +
                        " do it exactly the same\n" +
                        " mathematically\n" +
                        " Brian Super Sentai Learn To Rule The more we talked about with the setron learn a rule in the last two small we need to increase the desired output\n" +
                        " but they different in fact that one again we're assuming a bipolar we're assuming nothing about Sofia output is this some real value negative or positive with with learning for assuming a linear activation fee again we're assuming a linear neuron\n" +
                        " to this point we can even Ali in their eye with braids in as well\n" +
                        " so also with different perception on learning different classification LMS is for estimation of regression so they're similar in form of the same in mathematical form but there are the problem itself\n" +
                        "does the mother remarks about LMS of range vent but they're not online so they can be more expensive\n" +
                        " what does play classes like\n" +
                        " I don't know maybe maybe they are everyone stand up and that's just to wake everyone up\n" +
                        " any questions about this\n" +
                        " the home at least you understand the basic idea of LMS branches in what it does and also through them intuitively what it does feel like where does where does like backpropagation come in\n" +
                        " backpropagation is based on grades of Cent that's how I want to make sure you understand that and back propagation is needed for deep neural network multi-layer Professor on that how you learned to wait\n" +
                        " so doesn't like they could play so pretty and you're looking at it from multiple layers we talked about a single neuron defensively linear aggressive one layer so how do you update the weights with that single neuron the battering just backpropagation the multiple neurons multiple layers in each layer that way back propagation\n" +
                        "oh that's the difference and what we're saying cuz this is based on your the grated of my are\n" +
                        " the Mississippi pay my change in weight\n" +
                        " listen to visit my great-aunt was that Vector of my are the top one is for the delta or changing my weight\n" +
                        " knobler\n" +
                        " Delta to the Greek but this is the fee for that and then the top one you think you're changing weight\n" +
                        " David so why is based on what the implies sort of in this case is degrading again is based on what we do we derive is point in the direction of Max increase going opposite direction of Max-D perceptron you're not even the baseball great about spending a weekend away\n" +
                        " so it's not about optimizing its some directions is that my way is too big I need to make it smaller let me make it smaller\n" +
                        " so when can I said do we see both of these that I didn't talk about is this learning rate Ada\n" +
                        " I mentioned before we even talk about perceptrons impact you in terms of whether our attachment and thought was unrepairable how long it would take you to find that freaking optimal point for your weights\n" +
                        " now I want to spend the next to the 15 minutes to get to where we can get you up in the MLS\n" +
                        " so are learning radiator is a hyper perimeter but this is the one you have to pick up value for I prefer a more you had to pick up value based off of what other than what they have found or you can just pick it and see what happens until the adjusted as needed for Steph going to say that here it converges are impact how long it take us to reach that minimum value what is the defining optimal weight\n" +
                        " soap and one case if my learning rate is too small right what does that mean we'll just learn to write a value between between 0 100 am I going to apply so how much how much of my migraine is a vector how much are how am I going to scale is braiding and how much that I'm going to use to update my way around number in this case is in Ada is\n" +
                        " buy one in that scenario small portion of the gradient in that scenario so if my my learning rate is too small a small portion of my grade in then my convergence is going to take longer cuz I mean them only update them away slightly but I'm either going to need more iteration to finally get to go to my office\n" +
                        " conversely\n" +
                        " if my learning rate is too large I may never converts this is more about sort of now grain to send it\n" +
                        " if I'm using my learning rate is too large I mean never convert why is that because essentially I'm just going to bounce around\n" +
                        " on one side of the courtroom. I'm not there I'm really not going to go down tour guide opposite directions to my gradient from here if you are I move too far over and back and forth and never conversed with flu\n" +
                        " so I mean Denise's have intelligent way of selecting this learning rate I can't look at anything too small to big I'll never converter this is where we need to look at it waiting for the adjust to learning rate through Gray's it's in process\n" +
                        " so many ways to do this today, but the basic idea is that I don't use a fixed value for my learning rate of change over eat that pop\n" +
                        " so if I look at some of the different ideas of cost on some defense we like each one of these lines represent the laws at a particular a little racing over all of my data plan\n" +
                        " so if I fought the law after going through to the East and erase some of these curves here are learning rate is too large never converted to a minimum value for my lost my lost phone to a box\n" +
                        " am I going to read too small is it going to take me to learn longer to get down to a minimum value of learning curves\n" +
                        " if it might be a little deeper than that to a minimum value\n" +
                        " one way of doing this is known as stochastic approximation\n" +
                        " in the basic idea that I start with a large learning rate was not too large so that I can switch\n" +
                        " update my my waist by a lot in this really really should have decreased dismally I'm as either ate over the problem in this case my learn ring is a function of my duration Define s c / in some positive for amateur because Mall\n" +
                        " so I'm starting off of a big learning right but then I'd rather leave for the make this smaller\n" +
                        " Daniel so by iteration do you mean an Epoch one input but I couldn't adjust to a pocketbook\n" +
                        " so the basic idea is that I'm starting Mars I don't want to overshoot that minimum so as I enter eight more I meant that steps smaller and smaller until you know how to convert\n" +
                        " another approach to this I think it's even simpler than an update my box of my my problem I can send my learning rate to something to the relatively big 0.1 and then maybe at a certain number I just reduced it to something completely small 001 this is known as data set that talk to my small\n" +
                        " you can visit I prefer amateur things to do this when it goes all decisions that you will have to make as a design engineer\n" +
                        " tomato\n" +
                        " well, part of it is is your sister I can go to the pros this one over the other one I think part of this one is that\n" +
                        " sort of you should have you may have done some work in the field and soon as you know this is these are reasonable value smell when me know when you may want to fill in this case it is less to keep track off and turn the story to have a scalar you want to tour director\n" +
                        " I would say this one out for me aren't Pros unless it was someone else done this in the in the area close to this one this is\n" +
                        " not as informative if you even if you start with something like this\n" +
                        " the third approach is known as in Converse ID again you throw in some large evaluating your gradually decreasing it a few different parameters in this case here for my learning right again it's is a function of my number I have a tan. Perimeter and I have a towel a perimeter up to both positive values in this scenario that you have to decide upon a tear in town still\n" +
                        " compare the towel when in a small compared to Powell the learning rate is essentially\n" +
                        " based on 89th 480 went in his room large compared to towel the learning rate is morphle Catholic in nature\n" +
                        " believe it or not in town or two different type of parameters and then comparing the basic versus the third spin a convert approach"
                Log.d(ContentValues.TAG, "Transcript API response received, Transcript Confidence:")
                try {
                    val summary: SummaryItem = summaryAPI.fetchSummary(transcript)
                    Log.d(TAG, "API response received, Summary text: ${summary.content}")
                } catch (ex: Exception) {
                    Log.e(TAG, "Failed to fetch summary api response", ex)
                }
            } catch (ex: Exception) {
                Log.e(ContentValues.TAG, "Failed to fetch transcript api response", ex)
            }

        }

    }

    fun onRecordUpdateDb(recordingName : String, recordingCategory : String) {
        val session : Session = Session(UUID.randomUUID(), Date(), Stage.RECORDING, recordingCategory, recordingName)
        sessionUUID = session.sessionId
        val recording: Recording = Recording(UUID.randomUUID(), session.sessionId, Status.IN_PROGRESS)
        val transcription : Transcription = Transcription(UUID.randomUUID(), session.sessionId, Status.NOT_STARTED, "Recording not transcribed yet")
        val summary : Summary = Summary(UUID.randomUUID(), session.sessionId, Status.NOT_STARTED, "Summary not transcribed yet")
        gptBrosRepository.insertSessionAndChildren(session, recording, transcription, summary)
    }

    fun onStopRecordUpdateDb() {
        viewModelScope.launch {
            val session : Session = gptBrosRepository.getSession(sessionUUID)
            val recording: Recording = gptBrosRepository.getRecording(sessionUUID)
            gptBrosRepository.updateSession(session.copy(stage = Stage.TRANSCRIBING))
            gptBrosRepository.updateRecording(recording.copy(status = Status.FINISHED))
        }
    }

    fun summarizeRecording() {
        //Look into using something other than global scope as this is bad practice for api calls
        //Also look into transactions so process destruction doesn't corrupt db
        GlobalScope.launch {
            val transcription : Transcription = gptBrosRepository.getTranscription(sessionUUID)
            val session : Session = gptBrosRepository.getSession(sessionUUID)
            val summary : Summary = gptBrosRepository.getSummary(sessionUUID)
            gptBrosRepository.updateTranscription(transcription.copy(status = Status.IN_PROGRESS))

            val filePath : String = Environment.getExternalStorageDirectory().absolutePath +
                     "/"+ session.label + ".acc"

            val transcriptionRes  = transcribeRecording(filePath)

            //put result in transcribe.copy(content=)
            gptBrosRepository.updateTranscription(transcription.copy(content = transcriptionRes, status = Status.FINISHED))
            gptBrosRepository.updateSession(session.copy(stage = Stage.SUMMARIZING))

            //put result in summary.copy
            val summaryRes = summarizeTranscription(transcriptionRes)
            gptBrosRepository.updateSummary(summary.copy(content = summaryRes, status = Status.FINISHED))
        }
    }
    suspend fun transcribeRecording(filePath : String) : String {
        return "test"
    }

    suspend fun summarizeTranscription(transcription : String) : String {
        return "test"
    }

}