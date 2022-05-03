package com.example.playandroid.ui.page.home.question

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun QuestionPage(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {

    Text(text = "问答", modifier = Modifier.fillMaxSize())
}
