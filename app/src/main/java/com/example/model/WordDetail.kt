package com.example.model

/**
 * Detailed linguistic information for any German word tapped by the user.
 */
data class WordDetail(
    val word: String,                 // e.g. "Hauptstadt", "Dom", "Fluss", "Schloss"
    val root: String = word,          // Lemma / base form
    val article: GermanArticle = GermanArticle.NONE,
    val partOfSpeech: String,         // "Nomen" (اسم), "Verb" (فعل), "Adjektiv" (صفة), "Präposition", etc.
    val arabicMeaning: String,        // Accurate Arabic translation
    val plural: String? = null,       // e.g. "die Hauptstädte"
    val phonetic: String? = null,     // Scientific IPA phonetic pronunciation, e.g. "[ˈhaʊ̯ptˌʃtat]"
    val grammarNote: String? = null,  // Explanatory note (e.g., compound noun, irregular plural)
    val exampleSentenceDe: String? = null,
    val exampleSentenceAr: String? = null,
    val isFavorite: Boolean = false
) {
    val displayWithArticle: String
        get() = if (article != GermanArticle.NONE) "${article.articleStr} $word" else word
}
