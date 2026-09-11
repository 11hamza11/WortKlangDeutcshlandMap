package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GermanLexiconRepository
import com.example.model.GermanArticle
import com.example.model.WordDetail
import com.example.ui.theme.ArticleDasGreen
import com.example.ui.theme.ArticleDerBlue
import com.example.ui.theme.ArticleDieRed

@Composable
fun VocabularyScreen(
    onWordSelected: (WordDetail) -> Unit,
    onSpeakWord: (String, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf<GermanArticle?>(null) }
    var onlyFavorites by remember { mutableStateOf(false) }

    val allWords = remember { GermanLexiconRepository.getAllWords() }

    val filteredWords = remember(searchQuery, selectedFilter, onlyFavorites) {
        allWords.filter { word ->
            val matchesQuery = searchQuery.isBlank() ||
                    word.word.contains(searchQuery, ignoreCase = true) ||
                    word.arabicMeaning.contains(searchQuery, ignoreCase = true) ||
                    (word.plural != null && word.plural.contains(searchQuery, ignoreCase = true))

            val matchesArticle = selectedFilter == null || word.article == selectedFilter
            val matchesFav = !onlyFavorites || word.isFavorite

            matchesQuery && matchesArticle && matchesFav
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .testTag("vocabulary_screen")
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Search Text Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_vocab_input"),
            placeholder = { Text("ابحث في القاموس الألماني-العربي...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "مسح")
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Filter Chips (Articles: der / die / das / Alle)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedFilter == null && !onlyFavorites,
                    onClick = {
                        selectedFilter = null
                        onlyFavorites = false
                    },
                    label = { Text("الكل (Alle)", fontSize = 12.sp) }
                )
            }
            item {
                FilterChip(
                    selected = selectedFilter == GermanArticle.DER,
                    onClick = {
                        selectedFilter = if (selectedFilter == GermanArticle.DER) null else GermanArticle.DER
                        onlyFavorites = false
                    },
                    label = { Text("der (المذكر)", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ArticleDerBlue,
                        selectedLabelColor = Color.White
                    )
                )
            }
            item {
                FilterChip(
                    selected = selectedFilter == GermanArticle.DIE,
                    onClick = {
                        selectedFilter = if (selectedFilter == GermanArticle.DIE) null else GermanArticle.DIE
                        onlyFavorites = false
                    },
                    label = { Text("die (المؤنث)", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ArticleDieRed,
                        selectedLabelColor = Color.White
                    )
                )
            }
            item {
                FilterChip(
                    selected = selectedFilter == GermanArticle.DAS,
                    onClick = {
                        selectedFilter = if (selectedFilter == GermanArticle.DAS) null else GermanArticle.DAS
                        onlyFavorites = false
                    },
                    label = { Text("das (المحايد)", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ArticleDasGreen,
                        selectedLabelColor = Color.White
                    )
                )
            }
            item {
                FilterChip(
                    selected = onlyFavorites,
                    onClick = { onlyFavorites = !onlyFavorites },
                    label = { Text("المحفوظات ⭐", fontSize = 12.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "المفردات المتاحة (${filteredWords.size} كلمة):",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        // Vocabulary List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredWords, key = { it.word }) { wordDetail ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onWordSelected(wordDetail) }
                        .testTag("vocab_card_${wordDetail.word}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (wordDetail.article != GermanArticle.NONE) {
                                    Surface(
                                        color = wordDetail.article.composeColor,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = wordDetail.article.articleStr,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                Text(
                                    text = wordDetail.word,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Text(
                                text = wordDetail.arabicMeaning,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 2.dp)
                            )

                            if (!wordDetail.plural.isNullOrBlank()) {
                                Text(
                                    text = "الجمع: ${wordDetail.plural}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                            if (!wordDetail.phonetic.isNullOrBlank()) {
                                Text(
                                    text = "النطق: ${wordDetail.phonetic}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp
                                    ),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Pronounce Button
                        IconButton(
                            onClick = { onSpeakWord(wordDetail.displayWithArticle, 1.0f) },
                            modifier = Modifier
                                .size(44.dp)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), CircleShape)
                                .testTag("speak_vocab_${wordDetail.word}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "نطق الكلمة",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
