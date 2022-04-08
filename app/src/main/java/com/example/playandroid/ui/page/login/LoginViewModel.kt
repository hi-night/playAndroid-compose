package com.example.playandroid.ui.page.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playandroid.common.data.http.HttpResult
import com.example.playandroid.common.data.http.HttpService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var service: HttpService
) : ViewModel() {

    var viewState by mutableStateOf(LoginViewState())
        private set

    private var _viewEvent = Channel<LoginEvent>(Channel.BUFFERED)
    val viewEvent = _viewEvent.receiveAsFlow()

    fun dispatch(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> login()
            is LoginAction.ClearUserName -> clearUserName()
            is LoginAction.ClearPassword -> clearPassword()
            is LoginAction.UpdateUserName -> updateUserName(action.userName)
            is LoginAction.UpdatePassword -> updatePassword(action.password)
        }
    }

    private fun clearUserName() {
        viewState = viewState.copy(userName = "")
    }

    private fun clearPassword() {
        viewState = viewState.copy(password = "")
    }

    private fun updateUserName(userName: String) {
        viewState = viewState.copy(userName = userName)
    }

    private fun updatePassword(password: String) {
        viewState = viewState.copy(password = password)
    }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                emit(service.login(viewState.userName, viewState.password))
            }.map {
                if (it.errorCode == 0) {
                    if (it.data != null) {
                        HttpResult.Success(it.data!!)
                    } else {
                        throw Exception("the result of remote's request is null")
                    }
                } else {
                    throw Exception(it.errorMsg)
                }
            }
                .onEach {
                    _viewEvent.send(LoginEvent.Success(it.result.username))
                }
                .catch {
                    _viewEvent.send(LoginEvent.Error(it.message ?: ""))
                }
                .collect {}
        }
    }
}

sealed class LoginAction {
    object ClearUserName : LoginAction()
    object ClearPassword : LoginAction()
    object Login : LoginAction()
    class UpdateUserName(var userName: String) : LoginAction()
    class UpdatePassword(var password: String) : LoginAction()
}

sealed class LoginEvent() {
    class Success(var message: String) : LoginEvent()
    class Error(var message: String) : LoginEvent()
}

data class LoginViewState(
    var userName: String = "",
    var password: String = "",
    var isLogin: Boolean = false
)






