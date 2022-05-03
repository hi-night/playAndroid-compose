package com.example.playandroid.ui.page.home.square

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.playandroid.common.data.bean.Article
import com.example.playandroid.common.data.http.HttpService
import com.example.playandroid.common.paging.simplePager
import kotlinx.coroutines.flow.Flow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class SquareViewModel @Inject constructor(
    private var service: HttpService,
) : ViewModel() {
    private val pager by lazy {
        simplePager {
            if (it >= 3) {
                throw NullPointerException("")
            }
            delay(2000)
            service.getSquareData(it)
        }.cachedIn(viewModelScope)
    }
    var viewState by mutableStateOf(SquareViewState(pagingData = pager))
        private set
}

data class SquareViewState(
    val isRefreshing: Boolean = false,
    val listState: LazyListState = LazyListState(),
    val pagingData: PagingArticle
)

typealias PagingArticle = Flow<PagingData<Article>>