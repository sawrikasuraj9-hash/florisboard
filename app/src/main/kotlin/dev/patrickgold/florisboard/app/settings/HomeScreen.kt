
/*
 * Copyright (C) 2021-2025 The FlorisBoard Contributors
 *
 * Licensed under the Apache License, Version 2.0.
 */

package dev.patrickgold.florisboard.app.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.RepeatMode
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Gesture
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.patrickgold.florisboard.R
import dev.patrickgold.florisboard.app.LocalNavController
import dev.patrickgold.florisboard.app.Routes
import dev.patrickgold.florisboard.lib.compose.FlorisScreen
import dev.patrickgold.florisboard.lib.util.InputMethodUtils
import org.florisboard.lib.compose.stringRes
import kotlinx.coroutines.delay

private val BackgroundTop = Color(0xFF10071F)
private val BackgroundBottom = Color(0xFF07050D)
private val CardDark = Color(0xFF171025)
private val NeonPurple = Color(0xFFB56CFF)
private val NeonPink = Color(0xFFFF70D6)
private val MainText = Color(0xFFF8F2FF)
private val MutedText = Color(0xFFB6A9C9)
private val CardBorder = Color(0xFF38234F)

@Composable
fun HomeScreen() = FlorisScreen {
    title = stringRes(R.string.settings__home__title)
    navigationIconVisible = false
    previewFieldVisible = false

    val navController = LocalNavController.current
    val context = LocalContext.current

    content {
        val isEnabled by InputMethodUtils
            .observeIsFlorisboardEnabled(foregroundOnly = true)
        val isSelected by InputMethodUtils
            .observeIsFlorisboardSelected(foregroundOnly = true)

        var showContent by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(100)
            showContent = true
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            BackgroundTop,
                            Color(0xFF0C0816),
                            BackgroundBottom
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(tween(700)) +
                    slideInVertically(
                        initialOffsetY = { -it / 8 },
                        animationSpec = tween(
                            700,
                            easing = FastOutSlowInEasing
                        )
                    )
            ) {
                SoftBoardHeader()
            }

            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(tween(700, delayMillis = 120)) +
                    slideInVertically(
                        initialOffsetY = { it / 8 },
                        animationSpec = tween(
                            700,
                            delayMillis = 120,
                            easing = FastOutSlowInEasing
                        )
                    )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    if (!isEnabled) {
                        SoftBoardBanner(
                            title = "Enable SoftBoard",
                            subtitle = "Turn on your keyboard",
                            onClick = {
                                InputMethodUtils
                                    .showImeEnablerActivity(context)
                            }
                        )
                    } else if (!isSelected) {
                        SoftBoardBanner(
                            title = "Choose SoftBoard",
                            subtitle = "Set it as your keyboard",
                            onClick = {
                                InputMethodUtils.showImePicker(context)
                            }
                        )
                    }

                    Column {
                        Text(
                            text = "MAKE IT YOURS",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = NeonPurple,
                            letterSpacing = 2.sp
                        )
                        Spacer(Modifier.size(4.dp))
                        Text(
                            text = "Personalize your keyboard",
                            fontSize = 12.sp,
                            color = MutedText
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        SoftBoardTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Language,
                            title = "Languages",
                            subtitle = "Languages & layouts",
                            onClick = {
                                navController.navigate(
                                    Routes.Settings.Localization
                                )
                            }
                        )
                        SoftBoardTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Outlined.Palette,
                            title = "Appearance",
                            subtitle = "Themes & colors",
                            onClick = {
                                navController.navigate(
                                    Routes.Settings.Theme
                                )
                            }
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        SoftBoardTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Outlined.Keyboard,
                            title = "Keyboard",
                            subtitle = "Keys & layout",
                            onClick = {
                                navController.navigate(
                                    Routes.Settings.Keyboard
                                )
                            }
                        )
                        SoftBoardTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.SmartButton,
                            title = "Smartbar",
                            subtitle = "Quick tools",
                            onClick = {
                                navController.navigate(
                                    Routes.Settings.Smartbar
                                )
                            }
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        SoftBoardTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Spellcheck,
                            title = "Typing",
                            subtitle = "Typing options",
                            onClick = {
                                navController.navigate(
                                    Routes.Settings.Typing
                                )
                            }
                        )
                        SoftBoardTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Gesture,
                            title = "Gestures",
                            subtitle = "Touch & glide",
                            onClick = {
                                navController.navigate(
                                    Routes.Settings.Gestures
                                )
                            }
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        SoftBoardTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.AutoMirrored.Outlined.Assignment,
                            title = "Clipboard",
                            subtitle = "Copied items",
                            onClick = {
                                navController.navigate(
                                    Routes.Settings.Clipboard
                                )
                            }
                        )
                        SoftBoardTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.SentimentSatisfiedAlt,
                            title = "Emoji",
                            subtitle = "Emoji & media",
                            onClick = {
                                navController.navigate(
                                    Routes.Settings.Media
                                )
                            }
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        SoftBoardTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Extension,
                            title = "Add-ons",
                            subtitle = "Extra features",
                            onClick = {
                                navController.navigate(Routes.Ext.Home)
                            }
                        )
                        SoftBoardTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Outlined.Build,
                            title = "More",
                            subtitle = "Other settings",
                            onClick = {
                                navController.navigate(
                                    Routes.Settings.Other
                                )
                            }
                        )
                    }

                    SoftBoardTile(
                        modifier = Modifier.fillMaxWidth(),
                        icon = Icons.Outlined.Info,
                        title = "About SoftBoard",
                        subtitle = "Version and information",
                        onClick = {
                            navController.navigate(
                                Routes.Settings.About
                            )
                        }
                    )

                    Spacer(Modifier.size(8.dp))
                }
            }
        }
    }
}

@Composable
private fun SoftBoardHeader() {
    val transition = rememberInfiniteTransition(
        label = "softboard_glow"
    )

    val glow by transition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(27.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF26133C),
                        Color(0xFF151020),
                        Color(0xFF25122F)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        NeonPurple.copy(alpha = glow),
                        Color(0xFF49305D),
                        NeonPink.copy(alpha = glow * 0.65f)
                    )
                ),
                shape = RoundedCornerShape(27.dp)
            )
            .padding(21.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(59.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFFB45AFF),
                                Color(0xFF6E3CC2)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.28f),
                        RoundedCornerShape(19.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "S",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "SOFTBOARD",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MainText,
                    letterSpacing = 1.sp
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    text = "Made by Yashi",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NeonPink
                )
            }
        }

        Spacer(Modifier.size(20.dp))

        Text(
            text = "YOUR WORDS. YOUR STYLE.",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.8.sp,
            color = NeonPurple
        )

        Spacer(Modifier.size(7.dp))

        Text(
            text = "Express yourself with every tap.",
            fontSize = 14.sp,
            color = MutedText
        )

        Spacer(Modifier.size(17.dp))

        Row(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color(0xFF30213D))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(NeonPink)
            )
            Text(
                text = "GAMING STYLE • MADE FOR YOU",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                color = MainText
            )
        }
    }
}

@Composable
private fun SoftBoardBanner(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.975f else 1f,
        animationSpec = spring(
            dampingRatio = 0.65f,
            stiffness = 500f
        ),
        label = "setup_press"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF21162F))
            .border(
                1.dp,
                NeonPurple.copy(alpha = 0.65f),
                RoundedCornerShape(20.dp)
            )
            .clickable(
                interactionSource = interaction,
                indication = null,
                onClick = onClick
            )
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFF3A2451)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "S",
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                color = NeonPink
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 13.dp)
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MainText
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MutedText
            )
        }

        Text(
            text = "›",
            fontSize = 28.sp,
            color = NeonPurple
        )
    }
}

@Composable
private fun SoftBoardTile(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(21.dp)
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.965f else 1f,
        animationSpec = spring(
            dampingRatio = 0.65f,
            stiffness = 500f
        ),
        label = "tile_press"
    )

    Column(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(shape)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF20162D),
                        CardDark,
                        Color(0xFF130F1D)
                    )
                )
            )
            .border(
                1.dp,
                CardBorder,
                shape
            )
            .clickable(
                interactionSource = interaction,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 13.dp, vertical = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .size(43.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF493063),
                            Color(0xFF2D2040)
                        )
                    )
                )
                .border(
                    1.dp,
                    NeonPurple.copy(alpha = 0.22f),
                    RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = NeonPurple,
                modifier = Modifier.size(23.dp)
            )
        }

        Spacer(Modifier.size(13.dp))

        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MainText
        )

        Spacer(Modifier.size(5.dp))

        Text(
            text = subtitle,
            fontSize = 11.sp,
            color = MutedText,
            lineHeight = 15.sp
        )

        Spacer(Modifier.size(2.dp))
    }
}
