
/*
 * Copyright (C) 2021-2025 The FlorisBoard Contributors
 *
 * Licensed under the Apache License, Version 2.0.
 */

package dev.patrickgold.florisboard.app.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

private val BackgroundTop = Color(0xFF100B1B)
private val BackgroundBottom = Color(0xFF08070D)
private val CardBackground = Color(0xFF171321)
private val AccentPurple = Color(0xFFB58AFF)
private val AccentPink = Color(0xFFFF82C8)
private val PrimaryText = Color(0xFFF7F2FF)
private val SecondaryText = Color(0xFFAAA2BC)
private val SubtleBorder = Color(0xFF30283D)

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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            BackgroundTop,
                            Color(0xFF0D0A16),
                            BackgroundBottom
                        )
                    )
                )
                .padding(horizontal = 18.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Premium SoftBoard header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF29203B),
                                Color(0xFF17111F),
                                Color(0xFF201328)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        Color(0xFF45334F),
                        RoundedCornerShape(28.dp)
                    )
                    .padding(22.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(58.dp)
                            .clip(RoundedCornerShape(19.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        AccentPurple,
                                        Color(0xFF7650B8)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "S",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "SOFTBOARD",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PrimaryText,
                            letterSpacing = 1.2.sp
                        )
                        Spacer(Modifier.size(3.dp))
                        Text(
                            text = "Made by Yashi",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = AccentPink
                        )
                    }
                }

                Spacer(Modifier.size(20.dp))

                Text(
                    text = "YOUR WORDS. YOUR STYLE.",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentPurple,
                    letterSpacing = 1.8.sp
                )

                Spacer(Modifier.size(7.dp))

                Text(
                    text = "A keyboard that feels like you.",
                    fontSize = 15.sp,
                    color = SecondaryText
                )

                Spacer(Modifier.size(18.dp))

                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF292139))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(
                                if (isEnabled && isSelected) {
                                    Color(0xFF79E6B0)
                                } else {
                                    AccentPink
                                }
                            )
                    )

                    Text(
                        text = when {
                            isEnabled && isSelected -> "KEYBOARD ACTIVE"
                            isEnabled -> "READY TO SELECT"
                            else -> "SETUP REQUIRED"
                        },
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        letterSpacing = 1.sp
                    )
                }
            }

            // Keyboard setup
            if (!isEnabled) {
                SoftBoardBanner(
                    title = "Enable SoftBoard",
                    subtitle = "Turn on your new keyboard",
                    onClick = {
                        InputMethodUtils.showImeEnablerActivity(context)
                    }
                )
            } else if (!isSelected) {
                SoftBoardBanner(
                    title = "Choose SoftBoard",
                    subtitle = "Set SoftBoard as your keyboard",
                    onClick = {
                        InputMethodUtils.showImePicker(context)
                    }
                )
            }

            // Settings section heading
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "MAKE IT YOURS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryText,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(Modifier.size(4.dp))
                    Text(
                        text = "Personalize your experience",
                        fontSize = 12.sp,
                        color = SecondaryText
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF21182F))
                        .padding(horizontal = 11.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "SETTINGS",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentPurple,
                        letterSpacing = 1.sp
                    )
                }
            }

            // Languages and theme
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Language,
                    title = "Languages",
                    subtitle = "Language & layouts",
                    onClick = {
                        navController.navigate(Routes.Settings.Localization)
                    }
                )
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Palette,
                    title = "Appearance",
                    subtitle = "Themes & colors",
                    onClick = {
                        navController.navigate(Routes.Settings.Theme)
                    }
                )
            }

            // Keyboard and smartbar
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Keyboard,
                    title = "Keyboard",
                    subtitle = "Keys & layout",
                    onClick = {
                        navController.navigate(Routes.Settings.Keyboard)
                    }
                )
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.SmartButton,
                    title = "Smartbar",
                    subtitle = "Quick tools",
                    onClick = {
                        navController.navigate(Routes.Settings.Smartbar)
                    }
                )
            }

            // Typing and gestures
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Spellcheck,
                    title = "Typing",
                    subtitle = "Typing options",
                    onClick = {
                        navController.navigate(Routes.Settings.Typing)
                    }
                )
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Gesture,
                    title = "Gestures",
                    subtitle = "Touch & glide",
                    onClick = {
                        navController.navigate(Routes.Settings.Gestures)
                    }
                )
            }

            // Clipboard and emoji
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.AutoMirrored.Outlined.Assignment,
                    title = "Clipboard",
                    subtitle = "Copied items",
                    onClick = {
                        navController.navigate(Routes.Settings.Clipboard)
                    }
                )
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.SentimentSatisfiedAlt,
                    title = "Emoji",
                    subtitle = "Emoji & media",
                    onClick = {
                        navController.navigate(Routes.Settings.Media)
                    }
                )
            }

            // Add-ons and other settings
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                        navController.navigate(Routes.Settings.Other)
                    }
                )
            }

            // About
            SoftBoardTile(
                modifier = Modifier.fillMaxWidth(),
                icon = Icons.Outlined.Info,
                title = "About SoftBoard",
                subtitle = "Version and information",
                onClick = {
                    navController.navigate(Routes.Settings.About)
                }
            )

            Spacer(Modifier.size(6.dp))
        }
    }
}

@Composable
private fun SoftBoardBanner(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF21182D))
            .border(
                1.dp,
                Color(0xFF75508A),
                RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFF38254B)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "S",
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AccentPink
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 13.dp)
        ) {
            Text(
                text = title,
                color = PrimaryText,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Spacer(Modifier.size(4.dp))
            Text(
                text = subtitle,
                color = SecondaryText,
                fontSize = 12.sp
            )
        }

        Text(
            text = "›",
            color = AccentPurple,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
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

    Column(
        modifier = modifier
            .clip(shape)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF1B1626),
                        CardBackground
                    )
                )
            )
            .border(
                1.dp,
                SubtleBorder,
                shape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(43.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFF302341)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = AccentPurple,
                modifier = Modifier.size(23.dp)
            )
        }

        Spacer(Modifier.size(14.dp))

        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )

        Spacer(Modifier.size(5.dp))

        Text(
            text = subtitle,
            fontSize = 11.sp,
            color = SecondaryText,
            lineHeight = 15.sp
        )

        Spacer(Modifier.size(2.dp))
    }
}
