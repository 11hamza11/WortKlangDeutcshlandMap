package com.example.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class GermanSpeechManager(context: Context) : TextToSpeech.OnInitListener {

    private val tag = "GermanSpeechManager"
    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _spokenText = MutableStateFlow<String?>(null)
    val spokenText: StateFlow<String?> = _spokenText.asStateFlow()

    private val _speechRate = MutableStateFlow(1.0f)
    val speechRate: StateFlow<Float> = _speechRate.asStateFlow()

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.GERMANY)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback to generic German
                val fallbackResult = tts?.setLanguage(Locale.GERMAN)
                if (fallbackResult == TextToSpeech.LANG_MISSING_DATA || fallbackResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.w(tag, "German TTS language not supported or missing data on this device")
                } else {
                    _isReady.value = true
                }
            } else {
                _isReady.value = true
            }

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                    _spokenText.value = null
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                    _spokenText.value = null
                }
            })
        } else {
            Log.e(tag, "TTS Initialization failed with status: $status")
        }
    }

    fun setSpeed(rate: Float) {
        _speechRate.value = rate.coerceIn(0.5f, 2.0f)
        tts?.setSpeechRate(_speechRate.value)
    }

    /**
     * Pre-processes German text to guarantee flawless phonetic articulation:
     * - Expands standard German abbreviations so TTS pronounces them as full words
     * - Cleans punctuation artifacts
     */
    fun preprocessGermanPhonetics(raw: String): String {
        var processed = raw
            .replace(Regex("\\bz\\.B\\.", RegexOption.IGNORE_CASE), "zum Beispiel")
            .replace(Regex("\\bd\\.h\\.", RegexOption.IGNORE_CASE), "das heißt")
            .replace(Regex("\\busw\\.", RegexOption.IGNORE_CASE), "und so weiter")
            .replace(Regex("\\bbzw\\.", RegexOption.IGNORE_CASE), "beziehungsweise")
            .replace(Regex("\\bca\\.", RegexOption.IGNORE_CASE), "zirka")
            .replace(Regex("\\bu\\.a\\.", RegexOption.IGNORE_CASE), "unter anderem")
            .replace(Regex("\\bkm²\\b", RegexOption.IGNORE_CASE), "Quadratkilometer")
            .replace(Regex("\\bMio\\.", RegexOption.IGNORE_CASE), "Millionen")
            .replace(Regex("\\bMrd\\.", RegexOption.IGNORE_CASE), "Milliarden")
            .replace(Regex("\\bJh\\.", RegexOption.IGNORE_CASE), "Jahrhundert")
            .replace(Regex("\\bSt\\.", RegexOption.IGNORE_CASE), "Sankt")

        // Clean extraneous brackets and quotes
        processed = processed.replace(Regex("[\"„“«»()\\[\\]]"), " ")
        return processed.trim()
    }

    /**
     * Pronounce German word or passage scientifically and clearly.
     * Rate can be overridden (e.g., 0.7f for slow educational learning, 1.0f for natural).
     */
    fun speak(text: String, customRate: Float? = null) {
        if (text.isBlank()) return
        val targetRate = customRate ?: _speechRate.value
        val cleanedText = preprocessGermanPhonetics(text)

        tts?.setSpeechRate(targetRate)
        tts?.setPitch(1.0f) // Crystal clear standard pitch

        _spokenText.value = text
        val utteranceId = "wort_klang_${System.currentTimeMillis()}"
        tts?.speak(cleanedText, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
        _spokenText.value = null
    }

    fun shutdown() {
        release()
    }

    fun release() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        _isReady.value = false
        _isSpeaking.value = false
    }
}
