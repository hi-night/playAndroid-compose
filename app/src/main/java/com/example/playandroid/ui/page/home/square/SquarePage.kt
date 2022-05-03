package com.example.playandroid.ui.page.home.square

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.playandroid.common.data.bean.Article
import com.example.playandroid.common.data.bean.WebData
import com.example.playandroid.ui.page.home.RouteName
import com.example.playandroid.ui.theme.*
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
        itemsIndexed(squareData) { _, item ->
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
    Card(
        modifier = modifier
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .background(color = MaterialTheme.colors.background)
            .fillMaxWidth()
            .clickable {
                //onSelected.invoke(WebData(data.title!!, data.link!!))
                onSelected.invoke(WebData(data.title!!, data.link!!))
            }
    ) {
        ConstraintLayout(modifier = Modifier.padding(10.dp)) {
            val (circleText, publishIcon, publishTime, title, content, tab1, tab2) = createRefs()
            // icon
            Text(
                text = getFirstCharFromName(data),
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary)
                    .constrainAs(circleText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                textAlign = TextAlign.Center,
                fontSize = H6,
                color = white1,
            )

            // title
            Text(
                text = data.shareUser ?: "",
                color = black3,
                modifier = Modifier
                    .padding(horizontal = 5.dp)
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
                    color = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .background(grey2)
                        .border(width = 1.dp, color = MaterialTheme.colors.primary)
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
                modifier = Modifier.constrainAs(publishTime) {
                    top.linkTo(title.top)
                    bottom.linkTo(title.bottom)
                    end.linkTo(parent.end)
                }
            )

            Text(
                text = data.title.notNull(),
                fontSize = H6,
                color = black1,
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(8.dp)
            )
            // 标签2
            LabelTextButton(
                text = data.chapterName.notNull(),
                modifier = Modifier
                    .constrainAs(tab1) {
                        top.linkTo(content.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(end = 8.dp)
            )
            // 标签2
            LabelTextButton(
                text = data.superChapterName.notNull(),
                modifier = Modifier.constrainAs(tab2) {
                    top.linkTo(tab1.top)
                    start.linkTo(tab1.end)
                }
            )
        }
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
