package com.example.playandroid.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.playandroid.ui.theme.H6
import com.example.playandroid.ui.theme.white

@Composable
fun LabelTextButton(
    modifier: Modifier = Modifier,
    text: String = ""
) {
    Text(
        text = text,
        fontSize = H6,
        color = white,
        modifier = modifier
            .clip(shape = RoundedCornerShape(25.dp / 2))
            .background(MaterialTheme.colors.primary)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        textAlign = TextAlign.Center,
    )
}