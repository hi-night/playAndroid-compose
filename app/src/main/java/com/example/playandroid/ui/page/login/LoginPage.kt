package com.example.playandroid.ui.page.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.playandroid.ui.widgets.BaseTextField
import com.example.playandroid.ui.widgets.SNACK_ERROR
import com.example.playandroid.ui.widgets.popupSnackBar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPage(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: LoginViewModel = hiltViewModel()
) {


    val viewState = viewModel.viewState
    val coroutineState = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect {
            when (it) {
                is LoginEvent.Success -> {
                    popupSnackBar(coroutineState, scaffoldState, label = SNACK_ERROR, it.message)
                }
                is LoginEvent.Error -> {
                    popupSnackBar(coroutineState, scaffoldState, label = SNACK_ERROR, it.message)
                }
            }
        }
    }

    Column {
        Text(
            text = "登录事事明",
            fontSize = 26.sp,
            fontStyle = FontStyle.Normal,
            modifier = Modifier
                .padding(top = 120.dp)
                .align(Alignment.CenterHorizontally)
        )

        BaseTextField(
            text = viewState.userName,
            label = "用户名",
            placeholder = "请输入用户名",
            onValueChange = {
                viewModel.dispatch(LoginAction.UpdateUserName(userName = it))
            },
            onClearClick = {
                viewModel.dispatch(LoginAction.ClearUserName)
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        BaseTextField(
            text = viewState.password,
            label = "密码",
            placeholder = "请输入密码",
            onValueChange = {
                viewModel.dispatch(LoginAction.UpdatePassword(password = it))
            },
            onClearClick = {
                viewModel.dispatch(LoginAction.ClearPassword)
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = {
//                coroutineState.launch(Dispatchers.Main) {
//                    Log.d("成功", Thread.currentThread().name)
//                    state.snackbarHostState.showSnackbar(message = "成功！", duration = SnackbarDuration.Short)
//                }
                viewModel.login()
                keyboardController?.hide()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 24.dp)
        ) {
            Text(
                text = "登录", textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(4.dp)
            )
        }
    }

}




