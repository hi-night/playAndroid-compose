package com.example.playandroid.ui.page.home.square

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.playandroid.common.data.bean.Article
import com.example.playandroid.common.data.bean.WebData
import com.example.playandroid.ui.page.home.RouteName
import com.example.playandroid.ui.theme.H6
import com.example.playandroid.ui.theme.H7
import com.example.playandroid.ui.theme.black1
import com.example.playandroid.ui.theme.black3
import com.example.playandroid.ui.widgets.LabelTextButton
import com.example.playandroid.ui.widgets.RefreshList
import com.example.playandroid.utils.RouteUtils
import com.example.playandroid.utils.notNull

@Composable
fun SquarePage(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    viewModel: SquareViewModel = hiltViewModel()
) {
    val viewState = remember { viewModel.viewState }
    val squareData = viewState.pagingData.collectAsLazyPagingItems()
    val listState = if (squareData.itemCount > 0) viewState.listState else LazyListState()

    RefreshList(squareData, listState = listState) {
        itemsIndexed(squareData.itemSnapshotList) { _, item ->
            MultiStateItemView(
                data = item!!,
                onSelected = {
                    RouteUtils.navTo(navController, RouteName.WEB_VIEW, it)
                }
            )
        }
    }

}

@Composable
fun MultiStateItemView(
    modifier: Modifier = Modifier,
    data: Article,
    onSelected: (data: WebData) -> Unit = {},
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (circleText, publishIcon, publishTime, title) = createRefs()
            // title
            Text(
                text = data.shareUser.notNull(),
                color = black3,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(circleText.end)
                    }
            )

            // state
            if (data.fresh) {
                Text(
                    text = "最新",
                    fontSize = H7,
                    modifier = Modifier
                        .border(width = 1.dp, color = MaterialTheme.colors.primary, CircleShape)
                        .padding(horizontal = 5.dp, 0.dp)
                        .constrainAs(publishIcon) {
                            top.linkTo(title.top)
                            bottom.linkTo(title.bottom)
                            start.linkTo(title.end)
                        }
                )
            }

            // time
            Text(
                text = data.niceDate.notNull(),
                fontSize = H7,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .constrainAs(publishTime) {
                        top.linkTo(title.top)
                        bottom.linkTo(title.bottom)
                        end.linkTo(parent.end)
                    }
            )
        }

        Text(
            text = data.title.notNull(),
            fontSize = H6,
            color = black1,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row {
            // 标签1
            LabelTextButton(
                text = data.chapterName.notNull(),
                modifier = Modifier
                    .padding(end = 8.dp)
            )
            // 标签2
            LabelTextButton(
                text = data.superChapterName.notNull(),
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            thickness = 0.5.dp
        )
    }
}


fun getFirstCharFromName(data: Article?): String {
    val author = getAuthorName(data)
    return if (author.isNotEmpty()) author.trim().substring(0, 1) else "?"
}


fun getAuthorName(data: Article?): String {
    return if (data?.shareUser.isNullOrEmpty()) {
        data?.author ?: ""
    } else {
        data?.shareUser ?: ""
    }
}
