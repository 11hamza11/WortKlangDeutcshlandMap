package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.GermanSpeechManager
import com.example.data.GermanLexiconRepository
import com.example.data.GermanyMapData
import com.example.model.Bundesland
import com.example.model.CityLocation
import com.example.model.WordDetail
import com.example.ui.components.CityDetailSheet
import com.example.ui.components.InteractiveGermanyMap
import com.example.ui.components.WordInspectorSheet
import com.example.ui.screens.CitiesListScreen
import com.example.ui.screens.GameScreen
import com.example.ui.screens.VocabularyScreen
import com.example.ui.theme.GermanGold
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private lateinit var speechManager: GermanSpeechManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        speechManager = GermanSpeechManager(applicationContext)

        setContent {
            MyApplicationTheme {
                MainAppContent(speechManager = speechManager)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechManager.shutdown()
    }
}

enum class NavigationTab(val titleAr: String, val titleDe: String) {
    MAP("الخريطة", "Karte"),
    CITIES("المدن", "Städte"),
    GAME("الألعاب", "Spiele"),
    DICTIONARY("المفردات", "Lexikon")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContent(speechManager: GermanSpeechManager) {
    var selectedTab by remember { mutableStateOf(NavigationTab.MAP) }

    // Map & City selections
    var selectedCity by remember { mutableStateOf<CityLocation?>(null) }
    var selectedBundesland by remember { mutableStateOf<Bundesland?>(null) }

    // Word inspector selection
    var inspectedWord by remember { mutableStateOf<WordDetail?>(null) }

    // Speech state
    val isSpeaking by speechManager.isSpeaking.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("main_scaffold"),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = GermanGold,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("DE", fontWeight = FontWeight.Black, fontSize = 11.sp, color = Color.Black)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Wort Klang Deutschland",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            )
                            Text(
                                text = "خريطة ألمانيا التفاعلية والصوتية الشاملة",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                },
                actions = {
                    // Quick pronunciation demo or stop if speaking
                    IconButton(
                        onClick = {
                            if (isSpeaking) {
                                speechManager.stop()
                            } else {
                                speechManager.speak("Herzlich willkommen in Deutschland!", 1.0f)
                            }
                        },
                        modifier = Modifier.testTag("global_audio_toggle_button")
                    ) {
                        Icon(
                            imageVector = if (isSpeaking) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = "التحكم الصوتي",
                            tint = if (isSpeaking) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.testTag("bottom_navigation_bar")
            ) {
                NavigationBarItem(
                    selected = selectedTab == NavigationTab.MAP,
                    onClick = { selectedTab = NavigationTab.MAP },
                    icon = { Icon(Icons.Default.Map, contentDescription = "خريطة ألمانيا") },
                    label = { Text("الخريطة", fontSize = 11.sp) },
                    modifier = Modifier.testTag("tab_map")
                )

                NavigationBarItem(
                    selected = selectedTab == NavigationTab.CITIES,
                    onClick = { selectedTab = NavigationTab.CITIES },
                    icon = { Icon(Icons.Default.LocationCity, contentDescription = "المدن والمناطق") },
                    label = { Text("المدن", fontSize = 11.sp) },
                    modifier = Modifier.testTag("tab_cities")
                )

                NavigationBarItem(
                    selected = selectedTab == NavigationTab.GAME,
                    onClick = { selectedTab = NavigationTab.GAME },
                    icon = { Icon(Icons.Default.Gamepad, contentDescription = "الألعاب والتحديات") },
                    label = { Text("الألعاب", fontSize = 11.sp) },
                    modifier = Modifier.testTag("tab_game")
                )

                NavigationBarItem(
                    selected = selectedTab == NavigationTab.DICTIONARY,
                    onClick = { selectedTab = NavigationTab.DICTIONARY },
                    icon = { Icon(Icons.Default.Book, contentDescription = "قاموس المفردات") },
                    label = { Text("المفردات", fontSize = 11.sp) },
                    modifier = Modifier.testTag("tab_dictionary")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                NavigationTab.MAP -> {
                    InteractiveGermanyMap(
                        bundeslaender = GermanyMapData.bundeslaender,
                        cities = GermanyMapData.cities,
                        selectedCity = selectedCity,
                        selectedBundesland = selectedBundesland,
                        onCitySelected = { city ->
                            selectedCity = city
                            speechManager.speak(city.germanName, 1.0f)
                        },
                        onBundeslandSelected = { state ->
                            selectedBundesland = state
                            speechManager.speak(state.germanName, 1.0f)
                        }
                    )
                }

                NavigationTab.CITIES -> {
                    CitiesListScreen(
                        cities = GermanyMapData.cities,
                        bundeslaender = GermanyMapData.bundeslaender,
                        isSpeaking = isSpeaking,
                        onToggleSpeech = {
                            if (isSpeaking) {
                                speechManager.stop()
                            } else {
                                speechManager.speak("Herzlich willkommen in Deutschland! Erkunde alle deutschen Städte, Regionen und Sehenswürdigkeiten.", 1.0f)
                            }
                        },
                        onCitySelected = { city ->
                            selectedCity = city
                            speechManager.speak(city.germanName, 1.0f)
                        },
                        onSpeakCity = { name, rate ->
                            speechManager.speak(name, rate)
                        }
                    )
                }

                NavigationTab.GAME -> {
                    GameScreen(
                        onSpeak = { text, rate ->
                            speechManager.speak(text, rate)
                        }
                    )
                }

                NavigationTab.DICTIONARY -> {
                    VocabularyScreen(
                        onWordSelected = { word ->
                            inspectedWord = word
                            speechManager.speak(word.displayWithArticle, 1.0f)
                        },
                        onSpeakWord = { text, rate ->
                            speechManager.speak(text, rate)
                        }
                    )
                }
            }

            // City Detail Bottom Sheet
            if (selectedCity != null) {
                CityDetailSheet(
                    city = selectedCity,
                    isSpeaking = isSpeaking,
                    onDismiss = {
                        speechManager.stop()
                        selectedCity = null
                    },
                    onSpeakCityName = { name, rate ->
                        speechManager.speak(name, rate)
                    },
                    onSpeakPassage = { passage ->
                        speechManager.speak(passage, 0.95f)
                    },
                    onStopSpeech = {
                        speechManager.stop()
                    },
                    onWordClick = { clickedWord ->
                        val wordDetail = GermanLexiconRepository.lookupWord(clickedWord)
                        inspectedWord = wordDetail
                        speechManager.speak(wordDetail.displayWithArticle, 1.0f)
                    }
                )
            }

            // Word Inspector Bottom Sheet
            if (inspectedWord != null) {
                WordInspectorSheet(
                    wordDetail = inspectedWord,
                    onDismiss = { inspectedWord = null },
                    onSpeak = { text, rate ->
                        speechManager.speak(text, rate)
                    },
                    onToggleFavorite = { word ->
                        GermanLexiconRepository.toggleFavorite(word.word)
                        inspectedWord = GermanLexiconRepository.lookupWord(word.word)
                    }
                )
            }
        }
    }
}
