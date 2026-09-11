package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GermanyMapData
import com.example.model.GameCategory
import com.example.model.QuizQuestion
import com.example.ui.theme.ArticleDasGreen
import com.example.ui.theme.ArticleDerBlue
import com.example.ui.theme.ArticleDieRed
import com.example.ui.theme.GermanGold

@Composable
fun GameScreen(
    onSpeak: (String, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(GameCategory.ARTIKEL_DUELL) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var streak by remember { mutableIntStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }

    val filteredQuestions = remember(selectedCategory) {
        GermanyMapData.quizQuestions.filter { it.category == selectedCategory }
    }

    val currentQuestion = filteredQuestions.getOrNull(currentQuestionIndex)

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("game_screen")
    ) {
        // Top Header & Stats Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = GermanGold.copy(alpha = 0.2f),
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = GermanGold,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "نقاطك: $score",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "متتالية الإجابات: $streak 🔥",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                FilledTonalButton(
                    onClick = {
                        score = 0
                        streak = 0
                        currentQuestionIndex = 0
                        selectedAnswerIndex = null
                        isAnswerSubmitted = false
                    },
                    modifier = Modifier.testTag("reset_game_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "إعادة اللعبة",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("إعادة", fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Category Selector Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryChip(
                title = "تحدي الأداة",
                subtitle = "der/die/das",
                icon = Icons.Default.Psychology,
                isSelected = selectedCategory == GameCategory.ARTIKEL_DUELL,
                onClick = {
                    selectedCategory = GameCategory.ARTIKEL_DUELL
                    currentQuestionIndex = 0
                    selectedAnswerIndex = null
                    isAnswerSubmitted = false
                },
                modifier = Modifier.weight(1f)
            )

            CategoryChip(
                title = "أين تقع؟",
                subtitle = "Wo liegt das?",
                icon = Icons.Default.LocationOn,
                isSelected = selectedCategory == GameCategory.WO_LIEGT_DAS,
                onClick = {
                    selectedCategory = GameCategory.WO_LIEGT_DAS
                    currentQuestionIndex = 0
                    selectedAnswerIndex = null
                    isAnswerSubmitted = false
                },
                modifier = Modifier.weight(1f)
            )

            CategoryChip(
                title = "تدريب الاستماع",
                subtitle = "Hören",
                icon = Icons.Default.Hearing,
                isSelected = selectedCategory == GameCategory.HOER_TRAINING,
                onClick = {
                    selectedCategory = GameCategory.HOER_TRAINING
                    currentQuestionIndex = 0
                    selectedAnswerIndex = null
                    isAnswerSubmitted = false
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Current Question Card
        if (currentQuestion != null) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Question Progress Indicator
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "السؤال ${currentQuestionIndex + 1} من ${filteredQuestions.size}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (currentQuestion.audioTarget != null) {
                            IconButton(
                                onClick = { onSpeak(currentQuestion.audioTarget, 1.0f) },
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("game_speak_prompt_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "استماع",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // German Prompt
                    Text(
                        text = currentQuestion.promptDe,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Arabic Prompt
                    Text(
                        text = currentQuestion.promptAr,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.Center
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    // Subtitle Big Display
                    if (!currentQuestion.subtitleDe.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = currentQuestion.subtitleDe,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp
                                ),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // OPTIONS BUTTONS
                    if (selectedCategory == GameCategory.ARTIKEL_DUELL) {
                        // Special Big 3 Article Buttons (DER - DIE - DAS)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val articles = listOf(
                                Triple("der", ArticleDerBlue, 0),
                                Triple("die", ArticleDieRed, 1),
                                Triple("das", ArticleDasGreen, 2)
                            )

                            articles.forEach { (art, color, idx) ->
                                val isSelected = selectedAnswerIndex == idx
                                val isCorrect = currentQuestion.correctIndex == idx

                                val btnColor = if (isAnswerSubmitted) {
                                    if (isCorrect) color else if (isSelected) MaterialTheme.colorScheme.error else color.copy(alpha = 0.3f)
                                } else {
                                    color
                                }

                                Button(
                                    onClick = {
                                        if (!isAnswerSubmitted) {
                                            selectedAnswerIndex = idx
                                            isAnswerSubmitted = true
                                            if (idx == currentQuestion.correctIndex) {
                                                score += 10 + streak * 2
                                                streak++
                                            } else {
                                                streak = 0
                                            }
                                            currentQuestion.audioTarget?.let { onSpeak(it, 1.0f) }
                                        }
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = btnColor),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(60.dp)
                                        .testTag("article_button_${art}")
                                ) {
                                    Text(
                                        text = art,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    } else {
                        // Standard multiple choices
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            currentQuestion.options.forEachIndexed { index, option ->
                                val isSelected = selectedAnswerIndex == index
                                val isCorrect = currentQuestion.correctIndex == index

                                val cardColor = if (isAnswerSubmitted) {
                                    if (isCorrect) ArticleDasGreen.copy(alpha = 0.25f)
                                    else if (isSelected) MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
                                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                                } else if (isSelected) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                }

                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = cardColor,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .clickable(enabled = !isAnswerSubmitted) {
                                            selectedAnswerIndex = index
                                            isAnswerSubmitted = true
                                            if (index == currentQuestion.correctIndex) {
                                                score += 10 + streak * 2
                                                streak++
                                            } else {
                                                streak = 0
                                            }
                                            currentQuestion.audioTarget?.let { onSpeak(it, 1.0f) }
                                        }
                                        .testTag("quiz_option_${index}")
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = option,
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.SemiBold
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        if (isAnswerSubmitted && isCorrect) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "صحيح",
                                                tint = ArticleDasGreen
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Explanation & Next Button
                    AnimatedVisibility(visible = isAnswerSubmitted) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = if (selectedAnswerIndex == currentQuestion.correctIndex) {
                                    ArticleDasGreen.copy(alpha = 0.15f)
                                } else {
                                    MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = if (selectedAnswerIndex == currentQuestion.correctIndex) {
                                            "🎉 إجابة صحيحة وممتازة! (+${10 + (streak - 1) * 2} نقطة)"
                                        } else {
                                            "❌ إجابة غير دقيقة! لاحظ القاعدة الصحيحة:"
                                        },
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = if (selectedAnswerIndex == currentQuestion.correctIndex) ArticleDasGreen else MaterialTheme.colorScheme.error
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = currentQuestion.explanationDe,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = currentQuestion.explanationAr,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    if (currentQuestionIndex + 1 < filteredQuestions.size) {
                                        currentQuestionIndex++
                                        selectedAnswerIndex = null
                                        isAnswerSubmitted = false
                                    } else {
                                        // End of questions, loop back with bonus
                                        currentQuestionIndex = 0
                                        selectedAnswerIndex = null
                                        isAnswerSubmitted = false
                                    }
                                },
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("next_question_button")
                            ) {
                                Text(
                                    text = if (currentQuestionIndex + 1 < filteredQuestions.size) "السؤال التالي ⬅" else "إعادة جولة جديدة 🔄",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryChip(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        shadowElevation = if (isSelected) 4.dp else 1.dp,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
