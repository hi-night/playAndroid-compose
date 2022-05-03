package com.example.playandroid.ui.page.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.playandroid.ui.page.home.question.QuestionPage
import com.example.playandroid.ui.page.home.recommend.RecommendPage
import com.example.playandroid.ui.page.home.square.SquarePage
import com.example.playandroid.ui.widgets.TextTabBar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun HomePage(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val titles = viewModel.viewState.titles
    val scopeState = rememberCoroutineScope()

    Column {
        val pagerState = rememberPagerState(
            initialPage = 0
        )

        TextTabBar(
            index = pagerState.currentPage,
            tabTexts = titles,
            onTabSelected = { index ->
                scopeState.launch {
                    pagerState.scrollToPage(index)
                }
            }
        )

        HorizontalPager(
            count = titles.size,
            state = pagerState,
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 50.dp)
        ) { page ->
            when (page) {
                0 -> SquarePage(navController, scaffoldState)
                1 -> RecommendPage(navController, scaffoldState)
                2 -> QuestionPage(navController, scaffoldState)
            }
        }
    }
}



















