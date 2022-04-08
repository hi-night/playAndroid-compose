package com.example.playandroid.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    label: String = "",
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    onClearClick: ()-> Unit
) {
    OutlinedTextField(
        value = text,
        label = {
            Text(label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        onValueChange = {
            onValueChange(it)
        },
        singleLine = true,
        trailingIcon = {
            if (text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable {
                        onClearClick()
                    }
                )
            }
        },
        modifier = modifier
    )
}