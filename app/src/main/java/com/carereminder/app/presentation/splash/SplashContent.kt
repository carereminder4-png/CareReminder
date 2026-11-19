package com.carereminder.app.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.carereminder.app.ui.theme.CareReminderTheme

@Composable
fun SplashContent() {

    val fadeAnimation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        fadeAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(fadeAnimation.value),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "CareReminder",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Composable
fun SplashPreviewLight() {
    CareReminderTheme(
        darkTheme = false
    ) {
        SplashContent()
    }
}

@Preview(
    name = "Dark Mode",
    showBackground = true
)
@Composable
fun SplashPreviewDark() {
    CareReminderTheme(
        darkTheme = true
    ) {
        SplashContent()
    }
}