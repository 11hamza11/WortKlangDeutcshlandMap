package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GermanLexiconRepository
import com.example.model.GermanArticle
import com.example.ui.theme.ArticleDasGreen
import com.example.ui.theme.ArticleDerBlue
import com.example.ui.theme.ArticleDieRed

/**
 * Renders German text where every single German word is clickable!
 * When clicked, triggers word analysis, pronunciation, and article revelation.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClickableGermanText(
    text: String,
    modifier: Modifier = Modifier,
    selectedWord: String? = null,
    onWordClick: (String) -> Unit
) {
    // Tokenize into words and punctuation
    val tokens = remember(text) {
        // Splits by whitespace while preserving individual words
        text.split("\\s+".toRegex()).filter { it.isNotBlank() }
    }

    FlowRow(
        modifier = modifier,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp)
    ) {
        tokens.forEach { token ->
            val cleanWord = token.replace(Regex("[^\\p{L}\\-äöüßÄÖÜ]"), "")
            val isSelected = selectedWord != null && cleanWord.equals(selectedWord, ignoreCase = true)
            val wordDetail = remember(cleanWord) {
                if (cleanWord.isNotBlank()) GermanLexiconRepository.lookupWord(cleanWord) else null
            }

            val articleColor = when (wordDetail?.article) {
                GermanArticle.DER -> ArticleDerBlue
                GermanArticle.DIE -> ArticleDieRed
                GermanArticle.DAS -> ArticleDasGreen
                else -> MaterialTheme.colorScheme.onSurface
            }

            val interactionSource = remember { MutableInteractionSource() }

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else if (wordDetail?.article != null && wordDetail.article != GermanArticle.NONE) {
                    articleColor.copy(alpha = 0.08f)
                } else {
                    Color.Transparent
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        if (cleanWord.isNotBlank()) {
                            onWordClick(cleanWord)
                        }
                    }
                    .testTag("clickable_word_${cleanWord}")
            ) {
                Text(
                    text = token,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                        fontWeight = if (wordDetail?.article != null && wordDetail.article != GermanArticle.NONE) {
                            FontWeight.SemiBold
                        } else {
                            FontWeight.Normal
                        }
                    ),
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else if (wordDetail?.article != null && wordDetail.article != GermanArticle.NONE) {
                        articleColor
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 2.dp)
                )
            }
        }
    }
}
