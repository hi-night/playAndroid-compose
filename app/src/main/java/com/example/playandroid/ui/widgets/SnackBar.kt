package com.example.playandroid.ui.widgets

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val SNACK_INFO = ""
const val SNACK_WARN = " "
const val SNACK_ERROR = "  "
const val SNACK_SUCCESS = "OK"


fun popupSnackBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    label: String,
    message: String,
    onDismissCallback: () -> Unit = {}
) {
    scope.launch {
        scaffoldState.snackbarHostState.showSnackbar(actionLabel = label, message = message)
        onDismissCallback.invoke()
    }

}