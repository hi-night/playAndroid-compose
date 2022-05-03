package com.example.playandroid.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.playandroid.ui.page.home.BottomNavRoute
import com.example.playandroid.ui.theme.H5
import com.example.playandroid.ui.theme.ToolBarHeight
import com.example.playandroid.ui.theme.ToolBarTitleSize


/**
 * 普通标题栏头部
 */
@Composable
fun AppToolsBar(
    title: String,
    rightText: String? = null,
    onBack: (() -> Unit)? = null,
    onRightClick: (() -> Unit)? = null,
    imageVector: ImageVector? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(ToolBarHeight)
            .background(MaterialTheme.colors.primary)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            if (onBack != null) {
                Icon(
                    Icons.Default.ArrowBack,
                    null,
                    Modifier
                        .clickable(onClick = onBack)
                        .align(Alignment.CenterVertically)
                        .padding(12.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 12.dp)
                        .clickable {
                            onRightClick?.invoke()
                        })
            }
        }
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 40.dp),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            fontSize = if (title.length > 14) H5 else ToolBarTitleSize,
            fontWeight = FontWeight.W500,
            maxLines = 1
        )

    }
}


@Composable
fun MainBottomBar(navCtrl: NavHostController) {

    val bottomList = listOf(BottomNavRoute.Home, BottomNavRoute.ProFile)
    val currentDestination = navCtrl.currentBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        bottomList.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = item.icon, contentDescription = null)
                },
                selected = currentDestination?.hierarchy?.any { it.route == item.routeName } == true,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray,
                onClick = {
                    if (currentDestination?.route != item.routeName) {
                        navCtrl.navigate(item.routeName) {
                            popUpTo(navCtrl.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                })
        }
    }
}

data class TabTitle(
    val id: Int,
    val text: String,
    var cachePosition: Int = 0,
    var selected: Boolean = false
)


@Composable
fun TextTabBar(
    index: Int,
    tabTexts: List<TabTitle>,
    bgColor: Color = MaterialTheme.colors.primary,
    onTabSelected: ((index: Int) -> Unit)? = null
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(bgColor)
            .horizontalScroll(state = rememberScrollState())
    ) {
        Row(modifier = Modifier.align(Alignment.Center)) {
            tabTexts.forEachIndexed { i, it ->
                Text(
                    text = it.text,
                    fontSize = if (index == i) 20.sp else 15.sp,
                    fontWeight = if (index == i) FontWeight.SemiBold else FontWeight.Normal,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 8.dp)
                        .clickable {
                            onTabSelected?.invoke(i)
                        }
                )
            }
        }
    }

}
