package com.example.playandroid.ui.page.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.playandroid.ui.widgets.TabTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    var viewState by mutableStateOf(HomeViewState())
        private set

    init {
        viewState = viewState.copy(
            titles = listOf(
                TabTitle(101, "广场"),
                TabTitle(102, "推荐"),
                TabTitle(103, "问答"),
            )
        )
    }
}

data class HomeViewState(val titles: List<TabTitle> = emptyList())