package dev.panwar.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import dev.panwar.texttospeechdemo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null // Variable for TextToSpeech
    //Todo 3: create a binding variable
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//Todo 4: inflate layout
        binding = ActivityMainBinding.inflate(layoutInflater)
//Todo 5: bind with the activtity
        setContentView(binding?.root)

        // Initialize the Text To Speech
        tts = TextToSpeech(this, this)

        binding?.btnSpeak?.setOnClickListener {

            if (binding?.etEnteredText?.text!!.isEmpty()) {
                Toast.makeText(this@MainActivity, "Enter a text to speak.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                speakOut(binding?.etEnteredText?.text.toString())
            }
        }
    }

    /**
     * This the TextToSpeech override function
     * this is auto generated
     * Called to signal the completion of the TextToSpeech engine initialization. it is auto called method
     */
    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    /**
     * Here is Destroy function we will stop and shutdown the tts which is initialized above.
     */
    public override fun onDestroy() {

        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }

        super.onDestroy()
    }

    /**
     * Function is used to speak the text what we pass to it.
     * QUEUE_FLUSH IN CASE OF LONG SENTENCE, IF WE HIT SPEAK BUTTON AGAIN...THEN IT FLUSHES THE QUEUE AND START SPEAKING AGAIN FROM START
     */
    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}