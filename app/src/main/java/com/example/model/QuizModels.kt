package com.example.model

enum class GameCategory {
    WO_LIEGT_DAS,       // Map discovery / location guessing
    ARTIKEL_DUELL,      // der / die / das quiz
    HOER_TRAINING       // Audio pronunciation & comprehension
}

data class QuizQuestion(
    val id: String,
    val category: GameCategory,
    val promptDe: String,
    val promptAr: String,
    val subtitleDe: String? = null,
    val options: List<String>,
    val correctIndex: Int,
    val explanationDe: String,
    val explanationAr: String,
    val audioTarget: String? = null,
    val targetCityId: String? = null
)
