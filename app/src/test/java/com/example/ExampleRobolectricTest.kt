package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.GermanLexiconRepository
import com.example.data.GermanyMapData
import com.example.model.GermanArticle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Wort Klang Deutschland Map", appName)
  }

  @Test
  fun `verify German articles and lexicon lookup`() {
    // Check Der (Maskulin)
    val dom = GermanLexiconRepository.lookupWord("Dom")
    assertEquals(GermanArticle.DER, dom.article)
    assertEquals("كاتدرائية كبرى", dom.arabicMeaning)
    assertEquals("der Dom", dom.displayWithArticle)

    // Check Die (Feminin)
    val stadt = GermanLexiconRepository.lookupWord("Stadt")
    assertEquals(GermanArticle.DIE, stadt.article)
    assertEquals("مدينة", stadt.arabicMeaning)
    assertEquals("die Städte", stadt.plural)

    // Check Das (Neutrum)
    val tor = GermanLexiconRepository.lookupWord("Tor")
    assertEquals(GermanArticle.DAS, tor.article)
    assertEquals("بوابة / هدف (في الرياضة)", tor.arabicMeaning)

    // Check Inflected form lookup: "Städte" -> identifies root "Stadt"
    val staedte = GermanLexiconRepository.lookupWord("Städte")
    assertNotNull(staedte)

    // Check Compound Noun rule: "Hauptstadt" ends with "Stadt" (die Stadt) -> article is DIE
    val hauptstadt = GermanLexiconRepository.lookupWord("Hauptstadt")
    assertEquals(GermanArticle.DIE, hauptstadt.article)

    // Check Past Participle: "gegründet" -> identifies verb "gründen"
    val gegruendet = GermanLexiconRepository.lookupWord("gegründet")
    assertNotNull(gegruendet)
  }

  @Test
  fun `verify German phonetics abbreviation preprocessing`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val speechManager = com.example.audio.GermanSpeechManager(context)
    val processed = speechManager.preprocessGermanPhonetics("Berlin hat ca. 3.8 Mio. Einwohner z.B. im 21. Jh.")
    assertTrue(processed.contains("zirka"))
    assertTrue(processed.contains("Millionen"))
    assertTrue(processed.contains("zum Beispiel"))
    assertTrue(processed.contains("Jahrhundert"))
  }

  @Test
  fun `verify Germany map data and 16 federal states`() {
    val states = GermanyMapData.bundeslaender
    assertEquals(16, states.size)

    val cities = GermanyMapData.cities
    assertTrue(cities.size >= 12)

    val berlin = cities.find { it.id == "berlin" }
    assertNotNull(berlin)
    assertEquals("Berlin", berlin?.germanName)
    assertTrue(berlin?.germanPassage?.isNotBlank() == true)
    assertTrue(berlin?.arabicPassage?.isNotBlank() == true)

    val muenchen = cities.find { it.id == "muenchen" }
    assertNotNull(muenchen)
    assertEquals("München", muenchen?.germanName)
  }

  @Test
  fun `verify interactive game questions`() {
    val questions = GermanyMapData.quizQuestions
    assertTrue(questions.isNotEmpty())

    questions.forEach { q ->
      assertTrue(q.promptDe.isNotBlank())
      assertTrue(q.promptAr.isNotBlank())
      assertTrue(q.options.isNotEmpty())
      assertTrue(q.correctIndex in q.options.indices)
    }
  }
}
