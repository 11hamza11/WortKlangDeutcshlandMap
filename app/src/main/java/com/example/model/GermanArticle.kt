package com.example.model

import androidx.compose.ui.graphics.Color

/**
 * German Grammatical Articles with distinct semantic colors.
 * Blue for Masculine (der), Red for Feminine (die), Green for Neuter (das), Gold for Plural (die).
 */
enum class GermanArticle(
    val articleStr: String,
    val genderDe: String,
    val genderAr: String,
    val colorHex: Long,
    val badgeBgColorHex: Long
) {
    DER(
        articleStr = "der",
        genderDe = "Maskulin (مذكر)",
        genderAr = "مذكر (Maskulin)",
        colorHex = 0xFF2563EB, // Royal Blue
        badgeBgColorHex = 0xFFDBEAFE
    ),
    DIE(
        articleStr = "die",
        genderDe = "Feminin (مؤنث)",
        genderAr = "مؤنث (Feminin)",
        colorHex = 0xFFE11D48, // Crimson Magenta
        badgeBgColorHex = 0xFFFFE4E6
    ),
    DAS(
        articleStr = "das",
        genderDe = "Neutrum (محايد)",
        genderAr = "محايد (Neutrum)",
        colorHex = 0xFF059669, // Forest Green
        badgeBgColorHex = 0xFFD1FAE5
    ),
    PLURAL(
        articleStr = "die (Pl.)",
        genderDe = "Plural (جمع)",
        genderAr = "صيغة الجمع (Plural)",
        colorHex = 0xFFD97706, // Amber Gold
        badgeBgColorHex = 0xFFFEF3C7
    ),
    NONE(
        articleStr = "—",
        genderDe = "Kein Artikel",
        genderAr = "بدون أداة تعريف (فعل / صفة / حرف)",
        colorHex = 0xFF6B7280, // Slate Gray
        badgeBgColorHex = 0xFFF3F4F6
    );

    val composeColor: Color get() = Color(colorHex)
    val composeBadgeBg: Color get() = Color(badgeBgColorHex)
}
