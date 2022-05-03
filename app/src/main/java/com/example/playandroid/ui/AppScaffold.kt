package com.example.playandroid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.playandroid.ui.page.home.HomePage
import com.example.playandroid.ui.page.home.RouteName
import com.example.playandroid.ui.page.login.LoginPage
import com.example.playandroid.ui.widgets.MainBottomBar
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            if (currentDestination?.route == RouteName.HOME ||
                currentDestination?.route == RouteName.PROFILE
            ) {
                MainBottomBar(navCtrl = navController)
            }
        },

        content = {
            NavHost(
                navController = navController,
                modifier = Modifier.background(MaterialTheme.colors.background),
                startDestination = RouteName.HOME // 判断登录状态
            ) {
                composable(RouteName.HOME) {
                    HomePage(navController, scaffoldState)
                }

                composable(RouteName.PROFILE) {
                    MinePage(navController, scaffoldState)
                }

                composable(RouteName.LOGIN) {
                    LoginPage(navController, scaffoldState)
                }
            }
        }
    )
}

@Composable
fun MinePage(navController: NavHostController, scaffoldState: ScaffoldState) {
    Column {
        Text(text = "我的")
        Button(onClick = {
            navController.navigate(RouteName.LOGIN) {
                popUpTo(navController.graph.findStartDestination().id)
            }

        }) {
            Text("登录")
        }
    }
}