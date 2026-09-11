package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FilterHdr
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.OpenWith
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Bundesland
import com.example.model.CityLocation
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

/**
 * Hyper-Realistic Interactive Cartographic Map of Germany (Deutschlandkarte).
 * Features:
 * - High-precision true geographic borders of Germany, including real coastal contours and islands.
 * - Multi-layer Topography: Norddeutsches Tiefland, Mittelgebirge (Harz, Schwarzwald, Erzgebirge), and Alpine crests.
 * - Dynamic Rivers & Lakes: Rhein, Donau, Elbe, Weser, Main, Mosel, Bodensee, Chiemsee, Müritz.
 * - Authentic Marine Compass Rose (Windrose) & Dynamic Real-World Scale Bar (Maßstab in km).
 * - Smooth multi-touch pan & zoom with interactive Level-Of-Detail (LOD).
 * - Animated Sonar / Radar pulse for active city selections.
 */
@Composable
fun InteractiveGermanyMap(
    bundeslaender: List<Bundesland>,
    cities: List<CityLocation>,
    selectedCity: CityLocation?,
    selectedBundesland: Bundesland?,
    onCitySelected: (CityLocation) -> Unit,
    onBundeslandSelected: (Bundesland) -> Unit,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1.05f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    var showRivers by remember { mutableStateOf(true) }
    var showTopography by remember { mutableStateOf(true) }
    var showStateLabels by remember { mutableStateOf(true) }
    var showLandmarks by remember { mutableStateOf(true) }
    var showNavPad by remember { mutableStateOf(false) }

    // Pulse animation for selected city pin
    val infiniteTransition = rememberInfiniteTransition(label = "map_radar")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_radius"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_alpha"
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A1120), // Deep Baltic/Nordic midnight
                        Color(0xFF070B14)  // Sub-oceanic dark abyss
                    )
                )
            )
            .testTag("interactive_germany_map_container")
    ) {
        val containerWidthPx = constraints.maxWidth.toFloat()
        val containerHeightPx = constraints.maxHeight.toFloat()

        // Interactive Gesture Container: Smooth 1-finger pan drag, 2-finger pinch zoom, instant tap
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(cities, bundeslaender, scale, offsetX, offsetY) {
                    val touchSlop = viewConfiguration.touchSlop
                    awaitEachGesture {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        var isDrag = false
                        var totalPan = Offset.Zero
                        var tapCandidate = true

                        do {
                            val event = awaitPointerEvent()
                            val pointerCount = event.changes.size

                            if (pointerCount >= 2) {
                                // Multi-touch: smooth pinch-zoom and 2-finger pan
                                isDrag = true
                                tapCandidate = false
                                val zoomChange = event.calculateZoom()
                                val panChange = event.calculatePan()

                                val newScale = (scale * zoomChange).coerceIn(0.80f, 5.5f)
                                scale = newScale

                                val boundX = (size.width * newScale).coerceAtLeast(size.width * 0.9f)
                                val boundY = (size.height * newScale).coerceAtLeast(size.height * 0.9f)
                                offsetX = (offsetX + panChange.x).coerceIn(-boundX, boundX)
                                offsetY = (offsetY + panChange.y).coerceIn(-boundY, boundY)

                                event.changes.forEach { it.consume() }
                            } else if (pointerCount == 1) {
                                val change = event.changes[0]
                                if (change.pressed) {
                                    val pan = change.position - change.previousPosition
                                    totalPan += pan
                                    if (!isDrag && totalPan.getDistance() > touchSlop) {
                                        isDrag = true
                                        tapCandidate = false
                                    }
                                    if (isDrag) {
                                        val boundX = (size.width * scale).coerceAtLeast(size.width * 0.9f)
                                        val boundY = (size.height * scale).coerceAtLeast(size.height * 0.9f)
                                        offsetX = (offsetX + pan.x).coerceIn(-boundX, boundX)
                                        offsetY = (offsetY + pan.y).coerceIn(-boundY, boundY)
                                        change.consume()
                                    }
                                }
                            }
                        } while (event.changes.any { it.pressed })

                        if (tapCandidate && !isDrag) {
                            val tapOffset = down.position
                            val mapSize = minOf(size.width, size.height) * 0.92f
                            val mapStartX = (size.width - mapSize) / 2f + offsetX
                            val mapStartY = (size.height - mapSize) / 2f + offsetY

                            // Find closest city within tap tolerance
                            var nearestCity: CityLocation? = null
                            var minDistance = Float.MAX_VALUE
                            val hitTolerancePx = 48.dp.toPx()

                            cities.forEach { city ->
                                val isVisible = city.isMajor || scale >= 1.20f
                                if (isVisible) {
                                    val cityPxX = mapStartX + city.xNorm * mapSize * scale
                                    val cityPxY = mapStartY + city.yNorm * mapSize * scale

                                    val dist = hypot(tapOffset.x - cityPxX, tapOffset.y - cityPxY)
                                    if (dist < hitTolerancePx && dist < minDistance) {
                                        minDistance = dist
                                        nearestCity = city
                                    }
                                }
                            }

                            if (nearestCity != null) {
                                onCitySelected(nearestCity!!)
                            } else {
                                // Check nearest Bundesland center
                                var nearestState: Bundesland? = null
                                var minStateDist = Float.MAX_VALUE
                                bundeslaender.forEach { state ->
                                    val statePxX = mapStartX + state.centerNormX * mapSize * scale
                                    val statePxY = mapStartY + state.centerNormY * mapSize * scale
                                    val dist = hypot(tapOffset.x - statePxX, tapOffset.y - statePxY)
                                    if (dist < 64.dp.toPx() * scale && dist < minStateDist) {
                                        minStateDist = dist
                                        nearestState = state
                                    }
                                }
                                nearestState?.let { onBundeslandSelected(it) }
                            }
                        }
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val mapSize = minOf(size.width, size.height) * 0.92f
                val mapStartX = (size.width - mapSize) / 2f + offsetX
                val mapStartY = (size.height - mapSize) / 2f + offsetY

                // 1. Maritime ambient water glow & coastlines (Nordsee & Ostsee)
                drawWaterBodies(mapStartX, mapStartY, mapSize, scale)

                // 2. High-precision Germany Geographic Territory Outline & Realistic Shading
                drawRealisticGermanyTerritory(
                    mapStartX,
                    mapStartY,
                    mapSize,
                    scale,
                    selectedBundesland
                )

                // 3. Inland Lakes (Bodensee, Chiemsee, Müritz)
                drawInlandLakes(mapStartX, mapStartY, mapSize, scale)

                // 4. Detailed Hydrographic River Network (Flüsse)
                if (showRivers) {
                    drawDetailedRivers(mapStartX, mapStartY, mapSize, scale)
                }

                // 5. Realistic Topographic Relief (Alpen, Schwarzwald, Harz, Erzgebirge)
                if (showTopography) {
                    drawRealisticTopography(mapStartX, mapStartY, mapSize, scale)
                }

                // 6. State Boundaries & Capital Emblems
                if (showStateLabels) {
                    drawBundeslandDetails(
                        bundeslaender,
                        mapStartX,
                        mapStartY,
                        mapSize,
                        scale,
                        selectedBundesland
                    )
                }

                // 7. City Pins & Cultural Landmarks with Dynamic LOD
                drawCityPinsAndLandmarks(
                    cities = cities,
                    mapStartX = mapStartX,
                    mapStartY = mapStartY,
                    mapSize = mapSize,
                    scale = scale,
                    selectedCity = selectedCity,
                    pulseRadius = pulseRadius,
                    pulseAlpha = pulseAlpha,
                    showLandmarks = showLandmarks
                )

                // 8. Authentic Nautical Compass Rose (Windrose)
                drawCompassRose(
                    center = Offset(size.width - 56.dp.toPx(), 90.dp.toPx()),
                    radius = 28.dp.toPx()
                )

                // 9. Dynamic Real-World Scale Bar (Maßstab)
                drawScaleBar(
                    origin = Offset(20.dp.toPx(), size.height - 36.dp.toPx()),
                    scale = scale,
                    mapSize = mapSize
                )
            }
        }

        // Floating Map Controls Panel (Zoom +, Zoom -, Reset, Zoom Level Badge)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            // Zoom indicator badge
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.88f),
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 6.dp
            ) {
                Text(
                    text = "${(scale * 100).toInt()}%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            // Directional Pan Joypad (D-Pad for effortless arrow navigation)
            AnimatedVisibility(
                visible = showNavPad,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
                    shape = RoundedCornerShape(18.dp),
                    shadowElevation = 8.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        IconButton(
                            onClick = {
                                val boundY = (containerHeightPx * scale).coerceAtLeast(containerHeightPx * 0.9f)
                                offsetY = (offsetY + 120f).coerceIn(-boundY, boundY)
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "تحريك للشمال", tint = MaterialTheme.colorScheme.primary)
                        }
                        Row {
                            IconButton(
                                onClick = {
                                    val boundX = (containerWidthPx * scale).coerceAtLeast(containerWidthPx * 0.9f)
                                    offsetX = (offsetX + 120f).coerceIn(-boundX, boundX)
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "تحريك للغرب", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(
                                onClick = {
                                    scale = 1.05f
                                    offsetX = 0f
                                    offsetY = 0f
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.CenterFocusStrong, contentDescription = "توسيط", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(
                                onClick = {
                                    val boundX = (containerWidthPx * scale).coerceAtLeast(containerWidthPx * 0.9f)
                                    offsetX = (offsetX - 120f).coerceIn(-boundX, boundX)
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "تحريك للشرق", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                        IconButton(
                            onClick = {
                                val boundY = (containerHeightPx * scale).coerceAtLeast(containerHeightPx * 0.9f)
                                offsetY = (offsetY - 120f).coerceIn(-boundY, boundY)
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "تحريك للجنوب", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            // D-Pad Toggle Button
            SmallFloatingActionButton(
                onClick = { showNavPad = !showNavPad },
                containerColor = if (showNavPad) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                contentColor = if (showNavPad) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                modifier = Modifier.testTag("toggle_dpad_button")
            ) {
                Icon(Icons.Default.OpenWith, contentDescription = "لوحة أسهم التحريك")
            }

            SmallFloatingActionButton(
                onClick = { scale = (scale * 1.3f).coerceAtMost(5.5f) },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.testTag("zoom_in_button")
            ) {
                Icon(Icons.Default.Add, contentDescription = "تكبير الخريطة")
            }

            SmallFloatingActionButton(
                onClick = { scale = (scale / 1.3f).coerceAtLeast(0.80f) },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.testTag("zoom_out_button")
            ) {
                Icon(Icons.Default.Remove, contentDescription = "تصغير الخريطة")
            }

            SmallFloatingActionButton(
                onClick = {
                    scale = 1.05f
                    offsetX = 0f
                    offsetY = 0f
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.testTag("reset_map_button")
            ) {
                Icon(Icons.Default.CenterFocusStrong, contentDescription = "إعادة ضبط الخريطة")
            }
        }

        // Top Layer Toggles (Rivers, Mountains, States, Landmarks)
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 12.dp, start = 12.dp, end = 68.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = showRivers,
                onClick = { showRivers = !showRivers },
                label = { Text("الأنهار", fontSize = 11.sp) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Water,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF0284C7),
                    selectedLabelColor = Color.White
                )
            )

            FilterChip(
                selected = showTopography,
                onClick = { showTopography = !showTopography },
                label = { Text("التضاريس", fontSize = 11.sp) },
                leadingIcon = {
                    Icon(
                        Icons.Default.FilterHdr,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF15803D),
                    selectedLabelColor = Color.White
                )
            )

            FilterChip(
                selected = showStateLabels,
                onClick = { showStateLabels = !showStateLabels },
                label = { Text("الولايات", fontSize = 11.sp) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Layers,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                }
            )

            FilterChip(
                selected = showLandmarks,
                onClick = { showLandmarks = !showLandmarks },
                label = { Text("المعالم", fontSize = 11.sp) },
                leadingIcon = {
                    Icon(
                        Icons.Default.AccountBalance,
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFD97706),
                    selectedLabelColor = Color.White
                )
            )
        }

        // Quick Regional Navigation Chips (انتقال سريع لمناطق ألمانيا)
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 54.dp, start = 12.dp, end = 12.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(end = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "المناطق:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            FilterChip(
                selected = false,
                onClick = {
                    scale = 1.05f
                    offsetX = 0f
                    offsetY = 0f
                },
                label = { Text("كامل ألمانيا 🇩🇪", fontSize = 11.sp) }
            )

            FilterChip(
                selected = false,
                onClick = {
                    scale = 1.65f
                    offsetX = 0f
                    offsetY = 200f
                },
                label = { Text("الشمال والبحار ⚓", fontSize = 11.sp) }
            )

            FilterChip(
                selected = false,
                onClick = {
                    scale = 1.65f
                    offsetX = 0f
                    offsetY = -220f
                },
                label = { Text("الجنوب والألب ⛰️", fontSize = 11.sp) }
            )

            FilterChip(
                selected = false,
                onClick = {
                    scale = 1.65f
                    offsetX = 200f
                    offsetY = 20f
                },
                label = { Text("الغرب والراين 🏰", fontSize = 11.sp) }
            )

            FilterChip(
                selected = false,
                onClick = {
                    scale = 1.65f
                    offsetX = -180f
                    offsetY = 60f
                },
                label = { Text("الشرق والعاصمة 🐻", fontSize = 11.sp) }
            )
        }

        // Selected Bundesland Quick Banner
        AnimatedVisibility(
            visible = selectedBundesland != null && selectedCity == null,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 98.dp)
        ) {
            selectedBundesland?.let { state ->
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 8.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(state.coatOfArmsSymbol, fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = state.germanName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${state.arabicName} • العاصمة: ${state.capital} (${state.capitalAr})",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==================== REALISTIC CARTOGRAPHIC RENDER ENGINE ====================

private fun DrawScope.drawWaterBodies(
    startX: Float,
    startY: Float,
    mapSize: Float,
    scale: Float
) {
    // 1. North Sea (Nordsee) marine gradient in the Northwest
    val northSeaCenter = Offset(startX + 0.32f * mapSize * scale, startY + 0.05f * mapSize * scale)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0x3B0284C7), Color(0x150284C7), Color(0x000284C7)),
            center = northSeaCenter,
            radius = 160.dp.toPx() * scale
        ),
        radius = 160.dp.toPx() * scale,
        center = northSeaCenter
    )

    // 2. Baltic Sea (Ostsee) marine gradient in the Northeast
    val balticSeaCenter = Offset(startX + 0.72f * mapSize * scale, startY + 0.06f * mapSize * scale)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0x350369A1), Color(0x120369A1), Color(0x000369A1)),
            center = balticSeaCenter,
            radius = 170.dp.toPx() * scale
        ),
        radius = 170.dp.toPx() * scale,
        center = balticSeaCenter
    )

    // Decorative Sea Label: NORDSEE
    val seaPaint = android.graphics.Paint().apply {
        isAntiAlias = true
        textSize = (13.dp.toPx() * scale.coerceIn(0.9f, 1.4f))
        color = android.graphics.Color.argb(80, 56, 189, 248)
        isFakeBoldText = true
        letterSpacing = 0.25f
    }
    drawContext.canvas.nativeCanvas.drawText(
        "NORDSEE (بحر الشمال)",
        startX + 0.20f * mapSize * scale,
        startY + 0.06f * mapSize * scale,
        seaPaint
    )

    // Decorative Sea Label: OSTSEE
    drawContext.canvas.nativeCanvas.drawText(
        "OSTSEE (بحر البلطيق)",
        startX + 0.65f * mapSize * scale,
        startY + 0.05f * mapSize * scale,
        seaPaint
    )
}

/**
 * Draws Germany's realistic boundary polygon based on true geographic reference coordinates.
 */
private fun DrawScope.drawRealisticGermanyTerritory(
    startX: Float,
    startY: Float,
    mapSize: Float,
    scale: Float,
    selectedBundesland: Bundesland?
) {
    fun toScreen(nx: Float, ny: Float): Offset {
        return Offset(startX + nx * mapSize * scale, startY + ny * mapSize * scale)
    }

    // High-precision 40-point boundary of Germany
    val germanyPolygon = listOf(
        Pair(0.40f, 0.02f), // Sylt / List auf Sylt (Northernmost tip)
        Pair(0.44f, 0.04f), // Rømø / Danish border
        Pair(0.46f, 0.05f), // Flensburg Fjord
        Pair(0.50f, 0.07f), // Eckernförde Bay
        Pair(0.53f, 0.08f), // Kiel Fjord
        Pair(0.58f, 0.08f), // Fehmarn Sound
        Pair(0.56f, 0.13f), // Lübeck Bay (Travemünde)
        Pair(0.60f, 0.13f), // Wismar Bay
        Pair(0.64f, 0.12f), // Rostock / Warnemünde
        Pair(0.69f, 0.09f), // Fischland-Darß-Zingst
        Pair(0.73f, 0.11f), // Stralsund / Strelasund
        Pair(0.76f, 0.13f), // Greifswald Bodden
        Pair(0.83f, 0.13f), // Usedom / Polish border
        Pair(0.83f, 0.18f), // Stettiner Haff
        Pair(0.84f, 0.25f), // Schwedt an der Oder
        Pair(0.85f, 0.33f), // Frankfurt an der Oder
        Pair(0.84f, 0.40f), // Eisenhüttenstadt / Neisse
        Pair(0.88f, 0.47f), // Görlitz (Easternmost city)
        Pair(0.86f, 0.53f), // Zittau / Dreiländereck
        Pair(0.81f, 0.55f), // Sächsische Schweiz (Elbe Sandstone)
        Pair(0.73f, 0.59f), // Erzgebirge crest (Fichtelberg)
        Pair(0.67f, 0.63f), // Vogtland / Bavarian-Saxon-Czech junction
        Pair(0.73f, 0.72f), // Bayerischer Wald
        Pair(0.78f, 0.81f), // Passau / Inn-Danube junction
        Pair(0.74f, 0.87f), // Simbach am Inn
        Pair(0.72f, 0.91f), // Salzburg border
        Pair(0.71f, 0.95f), // Berchtesgaden & Watzmann (SE Horn)
        Pair(0.66f, 0.93f), // Chiemgau Alps
        Pair(0.59f, 0.93f), // Karwendel / Scharnitz
        Pair(0.57f, 0.94f), // Garmisch-Partenkirchen / Zugspitze (2962m)
        Pair(0.45f, 0.95f), // Oberstdorf (Southernmost point)
        Pair(0.35f, 0.92f), // Lindau / Bodensee
        Pair(0.31f, 0.92f), // Konstanz (Lake Constance)
        Pair(0.24f, 0.90f), // Basel / Lörrach (Rhine knee)
        Pair(0.22f, 0.84f), // Freiburg / Upper Rhine Graben
        Pair(0.21f, 0.75f), // Strasbourg border / Kehl
        Pair(0.23f, 0.69f), // Karlsruhe / Palatinate
        Pair(0.16f, 0.68f), // Saarbrücken / Saarland border
        Pair(0.13f, 0.62f), // Trier / Mosel valley
        Pair(0.11f, 0.56f), // Eifel / Luxembourg border
        Pair(0.10f, 0.49f), // Aachen / High Fens (Belgium border)
        Pair(0.15f, 0.40f), // Niederrhein (Kleve)
        Pair(0.20f, 0.33f), // Westphalia / Dutch border
        Pair(0.24f, 0.25f), // Emsland
        Pair(0.27f, 0.19f), // Dollart Bay / Emden
        Pair(0.30f, 0.18f), // Ostfriesland mainland coast
        Pair(0.33f, 0.19f), // Jadebusen (Wilhelmshaven)
        Pair(0.36f, 0.18f), // Weser estuary (Bremerhaven)
        Pair(0.38f, 0.14f), // Elbe estuary (Cuxhaven)
        Pair(0.41f, 0.11f), // Dithmarschen coast
        Pair(0.38f, 0.08f)  // Eiderstedt peninsula
    )

    val path = Path().apply {
        val first = toScreen(germanyPolygon[0].first, germanyPolygon[0].second)
        moveTo(first.x, first.y)
        for (i in 1 until germanyPolygon.size) {
            val pt = toScreen(germanyPolygon[i].first, germanyPolygon[i].second)
            lineTo(pt.x, pt.y)
        }
        close()
    }

    // Outer subtle gold/national ambient halo
    drawPath(
        path = path,
        color = Color(0x30F59E0B),
        style = Stroke(width = 16.dp.toPx() * scale)
    )

    // Fill Germany Map with realistic landmass elevation gradient:
    // Northern plain (moss/emerald) transitioning to central uplands and alpine south
    drawPath(
        path = path,
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF132E27), // North: Lush Coastal Lowland (Norddeutsches Tiefland)
                Color(0xFF1E293B), // Central: Deep Slate Uplands (Mittelgebirge)
                Color(0xFF282538), // Bavarian plateau
                Color(0xFF1F1D2B)  // Alps foothills
            ),
            startY = startY,
            endY = startY + mapSize * scale
        )
    )

    // Border line with crisp German Gold accent
    drawPath(
        path = path,
        color = Color(0xFFF59E0B),
        style = Stroke(
            width = 2.6.dp.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )

    // Draw Islands (Inseln)
    // 1. Rügen Island (with Jasmund chalk cliffs)
    val ruegenCenter = toScreen(0.73f, 0.07f)
    drawCircle(
        color = Color(0xFF132E27),
        radius = 8.dp.toPx() * scale,
        center = ruegenCenter
    )
    drawCircle(
        color = Color(0xFFF59E0B),
        radius = 8.dp.toPx() * scale,
        style = Stroke(width = 1.6.dp.toPx()),
        center = ruegenCenter
    )

    // 2. Fehmarn Island
    val fehmarnCenter = toScreen(0.58f, 0.06f)
    drawCircle(
        color = Color(0xFF132E27),
        radius = 5.dp.toPx() * scale,
        center = fehmarnCenter
    )
    drawCircle(
        color = Color(0xFFF59E0B),
        radius = 5.dp.toPx() * scale,
        style = Stroke(width = 1.4.dp.toPx()),
        center = fehmarnCenter
    )

    // 3. East Frisian Islands chain (Ostfriesische Inseln: Borkum, Norderney, Wangerooge)
    val frisianIslands = listOf(
        Pair(0.24f, 0.16f), // Borkum
        Pair(0.27f, 0.15f), // Juist
        Pair(0.29f, 0.15f), // Norderney
        Pair(0.31f, 0.15f), // Langeoog
        Pair(0.33f, 0.15f)  // Wangerooge
    )
    frisianIslands.forEach { isPt ->
        val ic = toScreen(isPt.first, isPt.second)
        drawOval(
            color = Color(0xFFF59E0B),
            topLeft = Offset(ic.x - 3.dp.toPx() * scale, ic.y - 1.5.dp.toPx() * scale),
            size = Size(6.dp.toPx() * scale, 3.dp.toPx() * scale)
        )
    }

    // 4. Helgoland (Offshore North Sea Rock)
    val helgoland = toScreen(0.32f, 0.10f)
    drawCircle(color = Color(0xFFEF4444), radius = 2.5.dp.toPx() * scale, center = helgoland)

    // Federal State divider lines (Subtle dashed cartographic boundaries)
    val stateDividers = listOf(
        // North / Central divider
        listOf(Pair(0.25f, 0.38f), Pair(0.45f, 0.36f), Pair(0.65f, 0.37f), Pair(0.85f, 0.36f)),
        // Central / South divider (Main river latitude)
        listOf(Pair(0.16f, 0.58f), Pair(0.35f, 0.58f), Pair(0.55f, 0.62f), Pair(0.75f, 0.62f)),
        // West / East historical boundary
        listOf(Pair(0.50f, 0.08f), Pair(0.52f, 0.36f), Pair(0.52f, 0.62f), Pair(0.52f, 0.93f)),
        // Bayern / Baden-Württemberg border
        listOf(Pair(0.42f, 0.65f), Pair(0.45f, 0.78f), Pair(0.46f, 0.90f))
    )

    stateDividers.forEach { divider ->
        val divPath = Path().apply {
            val p0 = toScreen(divider[0].first, divider[0].second)
            moveTo(p0.x, p0.y)
            for (j in 1 until divider.size) {
                val pj = toScreen(divider[j].first, divider[j].second)
                lineTo(pj.x, pj.y)
            }
        }
        drawPath(
            path = divPath,
            color = Color(0x35F59E0B),
            style = Stroke(
                width = 1.2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
            )
        )
    }
}

/**
 * Draws major realistic inland lakes (Seen Deutschlands): Bodensee, Chiemsee, Müritz.
 */
private fun DrawScope.drawInlandLakes(
    startX: Float,
    startY: Float,
    mapSize: Float,
    scale: Float
) {
    fun toScreen(nx: Float, ny: Float): Offset {
        return Offset(startX + nx * mapSize * scale, startY + ny * mapSize * scale)
    }

    val lakeColor = Color(0xFF0284C7)

    // 1. Bodensee (Lake Constance) in the South
    val bodenseeCenter = toScreen(0.33f, 0.92f)
    drawOval(
        color = lakeColor,
        topLeft = Offset(bodenseeCenter.x - 12.dp.toPx() * scale, bodenseeCenter.y - 5.dp.toPx() * scale),
        size = Size(24.dp.toPx() * scale, 10.dp.toPx() * scale)
    )

    // 2. Chiemsee (Bavarian Sea) in SE Bavaria
    val chiemseeCenter = toScreen(0.67f, 0.88f)
    drawOval(
        color = lakeColor,
        topLeft = Offset(chiemseeCenter.x - 6.dp.toPx() * scale, chiemseeCenter.y - 4.dp.toPx() * scale),
        size = Size(12.dp.toPx() * scale, 8.dp.toPx() * scale)
    )

    // 3. Müritz (Largest lake inside Germany) in Mecklenburg
    val mueritzCenter = toScreen(0.68f, 0.22f)
    drawOval(
        color = lakeColor,
        topLeft = Offset(mueritzCenter.x - 6.dp.toPx() * scale, mueritzCenter.y - 5.dp.toPx() * scale),
        size = Size(12.dp.toPx() * scale, 10.dp.toPx() * scale)
    )
}

/**
 * Draws Germany's iconic rivers and major tributaries (Die Flüsse Deutschlands).
 */
private fun DrawScope.drawDetailedRivers(
    startX: Float,
    startY: Float,
    mapSize: Float,
    scale: Float
) {
    fun toScreen(nx: Float, ny: Float): Offset {
        return Offset(startX + nx * mapSize * scale, startY + ny * mapSize * scale)
    }

    val riverColor = Color(0xFF38BDF8)
    val riverTributaryColor = Color(0xFF0EA5E9).copy(alpha = 0.8f)

    // 1. DER RHEIN (الراين) - Length: 1,233 km (Longest river in Germany)
    val rheinPath = Path().apply {
        val p1 = toScreen(0.33f, 0.92f) // Bodensee
        moveTo(p1.x, p1.y)
        val p2 = toScreen(0.24f, 0.90f) // Basel (Rhine knee)
        val p3 = toScreen(0.22f, 0.75f) // Strasbourg / Karlsruhe
        val p4 = toScreen(0.28f, 0.61f) // Mainz
        val p5 = toScreen(0.20f, 0.54f) // Koblenz (Deutsches Eck)
        val p6 = toScreen(0.18f, 0.50f) // Köln (Cologne)
        val p7 = toScreen(0.17f, 0.46f) // Düsseldorf
        val p8 = toScreen(0.14f, 0.38f) // Niederrhein / North Sea mouth
        cubicTo(p2.x, p2.y, p3.x, p3.y, p4.x, p4.y)
        cubicTo(p4.x, p4.y, p5.x, p5.y, p6.x, p6.y)
        cubicTo(p6.x, p6.y, p7.x, p7.y, p8.x, p8.y)
    }
    drawPath(path = rheinPath, color = riverColor, style = Stroke(width = 2.8.dp.toPx(), cap = StrokeCap.Round))

    // 2. DIE MOSEL (موزيل) - Flowing into Rhein at Koblenz
    val moselPath = Path().apply {
        val p1 = toScreen(0.14f, 0.63f) // Trier
        moveTo(p1.x, p1.y)
        val p2 = toScreen(0.17f, 0.58f) // Bernkastel-Kues
        val p3 = toScreen(0.20f, 0.54f) // Koblenz (Meets Rhein)
        cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
    }
    drawPath(path = moselPath, color = riverTributaryColor, style = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round))

    // 3. DER MAIN (ماين) - Flowing through Frankfurt into Rhein at Mainz
    val mainPath = Path().apply {
        val p1 = toScreen(0.62f, 0.65f) // Bayreuth / Bamberg
        moveTo(p1.x, p1.y)
        val p2 = toScreen(0.48f, 0.63f) // Würzburg
        val p3 = toScreen(0.32f, 0.58f) // Frankfurt am Main
        val p4 = toScreen(0.28f, 0.61f) // Meets Rhein at Mainz
        cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
        lineTo(p4.x, p4.y)
    }
    drawPath(path = mainPath, color = riverTributaryColor, style = Stroke(width = 2.0.dp.toPx(), cap = StrokeCap.Round))

    // 4. DER NECKAR (نيكار) - Through Stuttgart & Heidelberg into Rhein
    val neckarPath = Path().apply {
        val p1 = toScreen(0.35f, 0.81f) // Rottweil / Tübingen
        moveTo(p1.x, p1.y)
        val p2 = toScreen(0.36f, 0.75f) // Stuttgart
        val p3 = toScreen(0.34f, 0.66f) // Heidelberg
        val p4 = toScreen(0.26f, 0.67f) // Meets Rhein at Mannheim
        cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
        lineTo(p4.x, p4.y)
    }
    drawPath(path = neckarPath, color = riverTributaryColor, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))

    // 5. DIE DONAU (الدانوب) - South Germany into Austria
    val donauPath = Path().apply {
        val p1 = toScreen(0.30f, 0.82f) // Donaueschingen (Source in Schwarzwald)
        moveTo(p1.x, p1.y)
        val p2 = toScreen(0.44f, 0.80f) // Ulm
        val p3 = toScreen(0.52f, 0.78f) // Ingolstadt
        val p4 = toScreen(0.66f, 0.75f) // Regensburg
        val p5 = toScreen(0.78f, 0.81f) // Passau (Three-Rivers-City: Donau, Inn, Ilz)
        cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
        cubicTo(p3.x, p3.y, p4.x, p4.y, p5.x, p5.y)
    }
    drawPath(path = donauPath, color = riverColor, style = Stroke(width = 2.6.dp.toPx(), cap = StrokeCap.Round))

    // 6. DIE ISAR (إيزار) - Flowing through Munich into Donau
    val isarPath = Path().apply {
        val p1 = toScreen(0.58f, 0.93f) // Alps (Karwendel)
        moveTo(p1.x, p1.y)
        val p2 = toScreen(0.62f, 0.85f) // München
        val p3 = toScreen(0.68f, 0.76f) // Meets Donau at Deggendorf
        cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
    }
    drawPath(path = isarPath, color = riverTributaryColor, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))

    // 7. DIE ELBE (إلبه) - From Czech border through Dresden, Magdeburg, Hamburg to Cuxhaven
    val elbePath = Path().apply {
        val p1 = toScreen(0.82f, 0.56f) // Sächsische Schweiz
        moveTo(p1.x, p1.y)
        val p2 = toScreen(0.81f, 0.54f) // Dresden
        val p3 = toScreen(0.62f, 0.44f) // Magdeburg
        val p4 = toScreen(0.47f, 0.18f) // Hamburg
        val p5 = toScreen(0.38f, 0.14f) // Cuxhaven / North Sea
        cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
        cubicTo(p3.x, p3.y, p4.x, p4.y, p5.x, p5.y)
    }
    drawPath(path = elbePath, color = riverColor, style = Stroke(width = 2.8.dp.toPx(), cap = StrokeCap.Round))

    // 8. DIE SPREE (شبريه) - Flowing through Berlin
    val spreePath = Path().apply {
        val p1 = toScreen(0.85f, 0.46f) // Lusatia
        moveTo(p1.x, p1.y)
        val p2 = toScreen(0.80f, 0.38f) // Spreewald
        val p3 = toScreen(0.76f, 0.33f) // Berlin
        val p4 = toScreen(0.72f, 0.34f) // Spandau (Meets Havel)
        cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
        lineTo(p4.x, p4.y)
    }
    drawPath(path = spreePath, color = riverTributaryColor, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))

    // 9. DIE WESER (فيزر) - Bremen to North Sea
    val weserPath = Path().apply {
        val p1 = toScreen(0.44f, 0.48f) // Hann. Münden (Werra + Fulda)
        moveTo(p1.x, p1.y)
        val p2 = toScreen(0.38f, 0.35f) // Minden
        val p3 = toScreen(0.34f, 0.24f) // Bremen
        val p4 = toScreen(0.36f, 0.18f) // Bremerhaven
        cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
        lineTo(p4.x, p4.y)
    }
    drawPath(path = weserPath, color = riverColor, style = Stroke(width = 2.2.dp.toPx(), cap = StrokeCap.Round))
}

/**
 * Draws realistic topography relief: High Alps with snow peaks, Schwarzwald, Harz, Erzgebirge.
 */
private fun DrawScope.drawRealisticTopography(
    startX: Float,
    startY: Float,
    mapSize: Float,
    scale: Float
) {
    fun toScreen(nx: Float, ny: Float): Offset {
        return Offset(startX + nx * mapSize * scale, startY + ny * mapSize * scale)
    }

    // 1. High Bavarian Alps Crest (سلسلة جبال الألب البافارية الشاهقة)
    val alpsPeaks = listOf(
        Triple(0.46f, 0.95f, "Nebelhorn"),
        Triple(0.52f, 0.94f, "Allgäu"),
        Triple(0.57f, 0.94f, "Zugspitze 2962m"), // Highest peak in Germany
        Triple(0.63f, 0.93f, "Wendelstein"),
        Triple(0.68f, 0.93f, "Chiemgau"),
        Triple(0.71f, 0.95f, "Watzmann 2713m")   // Iconic double peak
    )

    alpsPeaks.forEach { peak ->
        val center = toScreen(peak.first, peak.second)
        val peakSize = if (peak.third.startsWith("Zugspitze")) 12.dp.toPx() * scale.coerceAtMost(1.8f) else 9.dp.toPx() * scale.coerceAtMost(1.8f)

        // Snow-capped triangular mountain
        val mountainPath = Path().apply {
            moveTo(center.x, center.y - peakSize)
            lineTo(center.x - peakSize * 0.9f, center.y + peakSize * 0.5f)
            lineTo(center.x + peakSize * 0.9f, center.y + peakSize * 0.5f)
            close()
        }

        // Shaded side of peak
        drawPath(
            path = mountainPath,
            brush = Brush.linearGradient(
                colors = listOf(Color.White, Color(0xFF94A3B8), Color(0xFF475569)),
                start = Offset(center.x, center.y - peakSize),
                end = Offset(center.x + peakSize, center.y + peakSize)
            )
        )

        // Peak Label if zoomed in
        if (scale >= 1.35f) {
            val peakPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = 18f
                color = android.graphics.Color.WHITE
                setShadowLayer(4f, 0f, 1f, android.graphics.Color.BLACK)
            }
            drawContext.canvas.nativeCanvas.drawText(
                "▲ ${peak.third}",
                center.x - 20f,
                center.y - peakSize - 4f,
                peakPaint
            )
        }
    }

    // 2. Der Schwarzwald (الغابة السوداء - الفيلدبيرغ 1493م)
    val blackForestCenter = toScreen(0.26f, 0.82f)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0x5515803D), Color(0x2215803D), Color(0x0015803D)),
            center = blackForestCenter,
            radius = 32.dp.toPx() * scale
        ),
        radius = 32.dp.toPx() * scale,
        center = blackForestCenter
    )
    if (scale >= 1.25f) {
        val topoPaint = android.graphics.Paint().apply {
            isAntiAlias = true
            textSize = 20f
            color = android.graphics.Color.rgb(134, 239, 172)
            setShadowLayer(3f, 0f, 1f, android.graphics.Color.BLACK)
        }
        drawContext.canvas.nativeCanvas.drawText(
            "🌲 Schwarzwald (Feldberg 1493m)",
            blackForestCenter.x - 40f,
            blackForestCenter.y,
            topoPaint
        )
    }

    // 3. Der Harz (جبال هارز - قمة بروكين 1141م)
    val harzCenter = toScreen(0.52f, 0.44f)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0x45166534), Color(0x15166534), Color(0x00166534)),
            center = harzCenter,
            radius = 24.dp.toPx() * scale
        ),
        radius = 24.dp.toPx() * scale,
        center = harzCenter
    )
    if (scale >= 1.3f) {
        val topoPaint = android.graphics.Paint().apply {
            isAntiAlias = true
            textSize = 19f
            color = android.graphics.Color.rgb(134, 239, 172)
            setShadowLayer(3f, 0f, 1f, android.graphics.Color.BLACK)
        }
        drawContext.canvas.nativeCanvas.drawText(
            "▲ Harz (Brocken 1141m)",
            harzCenter.x - 30f,
            harzCenter.y,
            topoPaint
        )
    }

    // 4. Erzgebirge (جبال الخام على حدود التشيك)
    val erzgebirgeCenter = toScreen(0.74f, 0.58f)
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(Color(0x40166534), Color(0x00166534)),
            center = erzgebirgeCenter,
            radius = 22.dp.toPx() * scale
        ),
        radius = 22.dp.toPx() * scale,
        center = erzgebirgeCenter
    )
}

/**
 * Draws state badges and capitals.
 */
private fun DrawScope.drawBundeslandDetails(
    bundeslaender: List<Bundesland>,
    mapStartX: Float,
    mapStartY: Float,
    mapSize: Float,
    scale: Float,
    selectedBundesland: Bundesland?
) {
    bundeslaender.forEach { state ->
        val x = mapStartX + state.centerNormX * mapSize * scale
        val y = mapStartY + state.centerNormY * mapSize * scale
        val isSelected = selectedBundesland?.id == state.id

        if (isSelected) {
            drawCircle(
                color = state.color.copy(alpha = 0.25f),
                radius = 36.dp.toPx() * scale,
                center = Offset(x, y)
            )
            drawCircle(
                color = state.color,
                radius = 36.dp.toPx() * scale,
                style = Stroke(width = 2.2.dp.toPx()),
                center = Offset(x, y)
            )
        }

        // Draw State Coat of Arms Symbol
        val symbolPaint = android.graphics.Paint().apply {
            isAntiAlias = true
            textSize = if (isSelected) 34f else 24f
            textAlign = android.graphics.Paint.Align.CENTER
        }
        drawContext.canvas.nativeCanvas.drawText(
            state.coatOfArmsSymbol,
            x,
            y,
            symbolPaint
        )
    }
}

/**
 * Draws interactive city pins, landmark icons, and active radar pulse effects.
 */
private fun DrawScope.drawCityPinsAndLandmarks(
    cities: List<CityLocation>,
    mapStartX: Float,
    mapStartY: Float,
    mapSize: Float,
    scale: Float,
    selectedCity: CityLocation?,
    pulseRadius: Float,
    pulseAlpha: Float,
    showLandmarks: Boolean
) {
    cities.forEach { city ->
        val isVisible = city.isMajor || scale >= 1.25f
        if (!isVisible) return@forEach

        val x = mapStartX + city.xNorm * mapSize * scale
        val y = mapStartY + city.yNorm * mapSize * scale
        val isSelected = selectedCity?.id == city.id
        val isCapital = city.id == "berlin"

        // Active Sonar / Radar pulse on selected city
        if (isSelected) {
            drawCircle(
                color = Color(0xFFF59E0B).copy(alpha = pulseAlpha),
                radius = (14.dp.toPx() + pulseRadius.dp.toPx()) * scale.coerceAtMost(1.6f),
                style = Stroke(width = 2.dp.toPx()),
                center = Offset(x, y)
            )
            drawCircle(
                color = Color(0xFFF59E0B).copy(alpha = 0.4f),
                radius = 16.dp.toPx() * scale.coerceAtMost(1.5f),
                center = Offset(x, y)
            )
        }

        // Pin Design: Berlin gets Capital Gold Star ⭐
        if (isCapital) {
            // Radiant double ring for Federal Capital
            drawCircle(
                color = Color(0xFFDC2626),
                radius = 10.dp.toPx(),
                center = Offset(x, y)
            )
            drawCircle(
                color = Color(0xFFF59E0B),
                radius = 8.dp.toPx(),
                center = Offset(x, y)
            )
            drawCircle(
                color = Color.White,
                radius = 4.dp.toPx(),
                center = Offset(x, y)
            )
        } else {
            val pinColor = if (city.isMajor) Color(0xFFF59E0B) else Color(0xFF38BDF8)
            drawCircle(
                color = Color(0xFF0F172A),
                radius = if (city.isMajor) 7.5.dp.toPx() else 5.5.dp.toPx(),
                center = Offset(x, y)
            )
            drawCircle(
                color = pinColor,
                radius = if (city.isMajor) 6.dp.toPx() else 4.5.dp.toPx(),
                center = Offset(x, y)
            )
            drawCircle(
                color = Color.White,
                radius = if (city.isMajor) 2.5.dp.toPx() else 2.0.dp.toPx(),
                center = Offset(x, y)
            )
        }

        // Native canvas text for crystal-clear labels
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            textSize = if (isCapital) 36f else if (city.isMajor) 30f else 24f
            typeface = android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                if (isCapital || city.isMajor) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL
            )
            color = if (isCapital) android.graphics.Color.rgb(254, 240, 138) else android.graphics.Color.WHITE
            setShadowLayer(6f, 0f, 2f, android.graphics.Color.BLACK)
        }

        val prefix = if (isCapital) "★ " else ""
        drawContext.canvas.nativeCanvas.drawText(
            "$prefix${city.germanName}",
            x + 9.dp.toPx(),
            y + 4.dp.toPx(),
            paint
        )

        // Draw Arabic name if zoomed in or selected
        if (isSelected || scale >= 1.55f) {
            val paintAr = android.graphics.Paint().apply {
                isAntiAlias = true
                textSize = 22f
                color = android.graphics.Color.rgb(245, 158, 11) // Gold
                setShadowLayer(4f, 0f, 1f, android.graphics.Color.BLACK)
            }
            drawContext.canvas.nativeCanvas.drawText(
                city.arabicName.split("-").first().trim(),
                x + 9.dp.toPx(),
                y + 17.dp.toPx(),
                paintAr
            )
        }

        // Draw Cultural Landmark Badge when enabled
        if (showLandmarks && (scale >= 1.6f || isSelected)) {
            val landmarkIcon = when (city.id) {
                "berlin" -> "🏛️ Brandenburger Tor"
                "koeln" -> "⛪ Kölner Dom"
                "muenchen" -> "🏰 Frauenkirche"
                "hamburg" -> "🚢 Elbphilharmonie"
                "heidelberg" -> "🏰 Schloss Heidelberg"
                "bremen" -> "🐴 Stadtmusikanten"
                "dresden" -> "🎭 Semperoper"
                "leipzig" -> "🎵 Thomaskirche"
                "potsdam" -> "👑 Schloss Sanssouci"
                "aachen" -> "👑 Aachener Dom"
                "trier" -> "🏛️ Porta Nigra"
                "weimar" -> "📜 Goethe-Haus"
                else -> null
            }
            if (landmarkIcon != null) {
                val lmPaint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    textSize = 19f
                    color = android.graphics.Color.rgb(253, 224, 71)
                    setShadowLayer(3f, 0f, 1f, android.graphics.Color.BLACK)
                }
                drawContext.canvas.nativeCanvas.drawText(
                    landmarkIcon,
                    x + 9.dp.toPx(),
                    y + 29.dp.toPx(),
                    lmPaint
                )
            }
        }
    }
}

/**
 * Draws an authentic 8-point nautical compass rose (Windrose) in German cartographic styling.
 */
private fun DrawScope.drawCompassRose(center: Offset, radius: Float) {
    // Outer compass ring
    drawCircle(
        color = Color(0x60F59E0B),
        radius = radius,
        style = Stroke(width = 1.5.dp.toPx()),
        center = center
    )
    drawCircle(
        color = Color(0x30F59E0B),
        radius = radius * 0.7f,
        style = Stroke(width = 1.0.dp.toPx()),
        center = center
    )

    // 8 Cardinal Points (N, NO, O, SO, S, SW, W, NW)
    for (i in 0 until 8) {
        val angleRad = (i * 45.0) * (PI / 180.0)
        val len = if (i % 2 == 0) radius * 0.88f else radius * 0.55f
        val px = center.x + (len * sin(angleRad)).toFloat()
        val py = center.y - (len * cos(angleRad)).toFloat()

        val isNorth = (i == 0)
        val ptColor = if (isNorth) Color(0xFFEF4444) else Color(0xFFF59E0B)

        drawLine(
            color = ptColor,
            start = center,
            end = Offset(px, py),
            strokeWidth = if (isNorth) 2.5.dp.toPx() else 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }

    // Compass Center Pivot
    drawCircle(color = Color(0xFFF59E0B), radius = 3.dp.toPx(), center = center)

    // "N" Label (North)
    val paintN = android.graphics.Paint().apply {
        isAntiAlias = true
        textSize = 22f
        isFakeBoldText = true
        color = android.graphics.Color.rgb(239, 68, 68) // Red for North
        textAlign = android.graphics.Paint.Align.CENTER
        setShadowLayer(4f, 0f, 1f, android.graphics.Color.BLACK)
    }
    drawContext.canvas.nativeCanvas.drawText("N", center.x, center.y - radius - 4.dp.toPx(), paintN)

    // "O" Label (Ost - East in German)
    val paintO = android.graphics.Paint().apply {
        isAntiAlias = true
        textSize = 18f
        color = android.graphics.Color.rgb(245, 158, 11)
        textAlign = android.graphics.Paint.Align.LEFT
    }
    drawContext.canvas.nativeCanvas.drawText("O", center.x + radius + 3.dp.toPx(), center.y + 6f, paintO)
}

/**
 * Draws a dynamic real-world scale bar (Maßstab: 0 - 100 km - 200 km) that updates dynamically with zoom.
 */
private fun DrawScope.drawScaleBar(origin: Offset, scale: Float, mapSize: Float) {
    // In our normalized coordinate system, 1.0 mapSize represents ~870 km (North to South of Germany)
    // 100 km corresponds to: (100.0 / 870.0) * mapSize * scale
    val km100Px = (100f / 870f) * mapSize * scale
    val barHeight = 4.dp.toPx()

    // Alternating segment 1: 0 to 50 km (Gold)
    drawRect(
        color = Color(0xFFF59E0B),
        topLeft = origin,
        size = Size(km100Px * 0.5f, barHeight)
    )

    // Alternating segment 2: 50 to 100 km (White)
    drawRect(
        color = Color.White,
        topLeft = Offset(origin.x + km100Px * 0.5f, origin.y),
        size = Size(km100Px * 0.5f, barHeight)
    )

    // Border around the whole bar
    drawRect(
        color = Color.Black,
        topLeft = origin,
        size = Size(km100Px, barHeight),
        style = Stroke(width = 1.dp.toPx())
    )

    val scalePaint = android.graphics.Paint().apply {
        isAntiAlias = true
        textSize = 18f
        color = android.graphics.Color.WHITE
        setShadowLayer(3f, 0f, 1f, android.graphics.Color.BLACK)
    }

    drawContext.canvas.nativeCanvas.drawText("0", origin.x - 3f, origin.y - 4f, scalePaint)
    drawContext.canvas.nativeCanvas.drawText("50", origin.x + km100Px * 0.5f - 10f, origin.y - 4f, scalePaint)
    drawContext.canvas.nativeCanvas.drawText("100 km", origin.x + km100Px - 15f, origin.y - 4f, scalePaint)
}
