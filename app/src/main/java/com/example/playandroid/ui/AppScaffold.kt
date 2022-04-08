package com.example.playandroid.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.playandroid.ui.page.home.RouteName
import com.example.playandroid.ui.page.login.LoginPage
import com.example.playandroid.ui.widgets.MainBottomBar
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlin.math.log

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
//            navCtrl.navigate(item.routeName) {
//                popUpTo(navCtrl.graph.findStartDestination().id) {
//                    saveState = true
//                }
//                launchSingleTop = true
//                restoreState = true
//            }
            navController.navigate(RouteName.LOGIN){
                Log.d("11111", "MinePage: " + navController.graph.findStartDestination().id)
                popUpTo(navController.graph.findStartDestination().id)
            }

        }) {
            Text("登录")
        }
    }
}

@Composable
fun HomePage(navController: NavHostController, scaffoldState: ScaffoldState) {
    Text(text = "首页")
}
