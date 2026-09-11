package com.example.model

import androidx.compose.ui.graphics.Color

data class Bundesland(
    val id: String,
    val germanName: String,
    val arabicName: String,
    val capital: String,
    val capitalAr: String,
    val coatOfArmsSymbol: String,
    val colorHex: Long,
    val centerNormX: Float,
    val centerNormY: Float,
    val descriptionDe: String,
    val descriptionAr: String,
    val population: String,
    val areaKm2: String
) {
    val color: Color get() = Color(colorHex)
}

data class CityLocation(
    val id: String,
    val germanName: String,
    val arabicName: String,
    val bundeslandId: String,
    val bundeslandName: String,
    val bundeslandNameAr: String,
    val phonetic: String,            // Scientific IPA phonetic representation
    val xNorm: Float,                // 0f (West/North) to 1f (East/South)
    val yNorm: Float,                // 0f (North) to 1f (South)
    val isMajor: Boolean = true,     // Visible at default zoom level
    val population: String,
    val famousLandmarksDe: List<String>,
    val famousLandmarksAr: List<String>,
    val germanPassage: String,       // Educational German narrative text
    val arabicPassage: String,       // Arabic translation underneath
    val funFactDe: String,
    val funFactAr: String,
    val audioSpeed: Float = 1.0f
)
