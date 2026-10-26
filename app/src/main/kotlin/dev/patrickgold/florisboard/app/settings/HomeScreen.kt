
/*

- Copyright (C) 2021-2025 The FlorisBoard Contributors
- 
- Licensed under the Apache License, Version 2.0 (the "License");
- you may not use this file except in compliance with the License.
- You may obtain a copy of the License at
- 
- http://www.apache.org/licenses/LICENSE-2.0
- 
- Unless required by applicable law or agreed to in writing, software
- distributed under the License is distributed on an "AS IS" BASIS,
- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
- See the License for the specific language governing permissions and
- limitations under the License.
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
import androidx.compose.foundation.layout.weight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import dev.patrickgold.florisboard.R
import dev.patrickgold.florisboard.app.LocalNavController
import dev.patrickgold.florisboard.app.Routes
import dev.patrickgold.florisboard.lib.compose.FlorisScreen
import dev.patrickgold.florisboard.lib.util.InputMethodUtils
import org.florisboard.lib.compose.stringRes

private val NeonPink = Color(0xFFFF42D0)
private val NeonPurple = Color(0xFF9D4DFF)
private val NeonBlue = Color(0xFF28C9FF)
private val SoftText = Color(0xFFE9DFFF)
private val DarkBackground = Color(0xFF080519)

@Composable
fun HomeScreen() = FlorisScreen {
    title = stringRes(R.string.settings__home__title)
    navigationIconVisible = false
    previewFieldVisible = true

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
                .background(DarkBackground)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // SoftBoard branding
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(26.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF200A48),
                                Color(0xFF100A2B),
                                Color(0xFF360B45)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        Brush.horizontalGradient(
                            listOf(NeonPink, NeonPurple, NeonBlue)
                        ),
                        RoundedCornerShape(26.dp)
                    )
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "♛  SoftBoard",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = NeonPink
                )
                Text(
                    text = "Med by Yashi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = "TYPE  •  CREATE  •  EXPRESS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftText
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Your Keyboard, Your Style ✨",
                    fontSize = 15.sp,
                    color = NeonPink
                )
            }

            // Keyboard setup status
            if (!isEnabled) {
                SoftBoardBanner(
                    title = "Enable SoftBoard",
                    subtitle = "Tap here to enable your keyboard",
                    onClick = {
                        InputMethodUtils.showImeEnablerActivity(context)
                    }
                )
            } else if (!isSelected) {
                SoftBoardBanner(
                    title = "Select SoftBoard",
                    subtitle = "Tap here to make SoftBoard your keyboard",
                    onClick = {
                        InputMethodUtils.showImePicker(context)
                    }
                )
            }

            Text(
                text = "PERSONALIZE YOUR KEYBOARD",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = NeonPink,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )

            // Languages + Theme
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Language,
                    title = "Languages",
                    subtitle = "Language and input method",
                    accent = NeonPurple,
                    onClick = {
                        navController.navigate(Routes.Settings.Localization)
                    }
                )
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Palette,
                    title = "Theme",
                    subtitle = "Colors and appearance",
                    accent = NeonPink,
                    onClick = {
                        navController.navigate(Routes.Settings.Theme)
                    }
                )
            }

            // Keyboard + Smartbar
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Keyboard,
                    title = "Keyboard",
                    subtitle = "Keys, height and layout",
                    accent = NeonBlue,
                    onClick = {
                        navController.navigate(Routes.Settings.Keyboard)
                    }
                )
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.SmartButton,
                    title = "Smartbar",
                    subtitle = "Quick access tools",
                    accent = NeonPurple,
                    onClick = {
                        navController.navigate(Routes.Settings.Smartbar)
                    }
                )
            }

            // Typing + Gestures
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Spellcheck,
                    title = "Typing",
                    subtitle = "Typing preferences",
                    accent = NeonPink,
                    onClick = {
                        navController.navigate(Routes.Settings.Typing)
                    }
                )
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Gesture,
                    title = "Gestures",
                    subtitle = "Gestures and glide",
                    accent = NeonBlue,
                    onClick = {
                        navController.navigate(Routes.Settings.Gestures)
                    }
                )
            }

            // Clipboard + Emoji
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.AutoMirrored.Outlined.Assignment,
                    title = "Clipboard",
                    subtitle = "Your copied items",
                    accent = Color(0xFFFF884D),
                    onClick = {
                        navController.navigate(Routes.Settings.Clipboard)
                    }
                )
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.SentimentSatisfiedAlt,
                    title = "Emoji",
                    subtitle = "Emoji and media",
                    accent = NeonPurple,
                    onClick = {
                        navController.navigate(Routes.Settings.Media)
                    }
                )
            }

            // Add-ons + Other
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Extension,
                    title = "Add-ons",
                    subtitle = "Extra features",
                    accent = NeonBlue,
                    onClick = {
                        navController.navigate(Routes.Ext.Home)
                    }
                )
                SoftBoardTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Build,
                    title = "Other",
                    subtitle = "More settings",
                    accent = NeonPurple,
                    onClick = {
                        navController.navigate(Routes.Settings.Other)
                    }
                )
            }

            // About
            SoftBoardTile(
                modifier = Modifier.fillMaxWidth(),
                icon = Icons.Outlined.Info,
                title = "About",
                subtitle = "SoftBoard information",
                accent = NeonPink,
                onClick = {
                    navController.navigate(Routes.Settings.About)
                }
            )

            Spacer(modifier = Modifier.size(8.dp))
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
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF260A50), Color(0xFF120B30))
                )
            )
            .border(
                1.dp,
                Brush.horizontalGradient(listOf(NeonPink, NeonPurple)),
                RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "♛", fontSize = 30.sp, color = NeonPink)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = subtitle,
                color = SoftText,
                fontSize = 12.sp
            )
        }
        Text(text = "›", color = NeonPink, fontSize = 32.sp)
    }
}

@Composable
private fun SoftBoardTile(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    accent: Color,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(22.dp)

    Row(
        modifier = modifier
            .clip(shape)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF171038),
                        Color(0xFF0D0B29),
                        Color(0xFF211039)
                    )
                )
            )
            .border(
                1.dp,
                Brush.horizontalGradient(
                    listOf(accent.copy(alpha = 0.9f), NeonPurple.copy(alpha = 0.65f))
                ),
                shape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        listOf(accent, accent.copy(alpha = 0.48f))
                    )
                )
                .border(
                    1.dp,
                    Color.White.copy(alpha = 0.45f),
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(27.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = SoftText,
                lineHeight = 14.sp
            )
        }

        Text(
            text = "›",
            fontSize = 26.sp,
            color = NeonPink
        )
    }
}
