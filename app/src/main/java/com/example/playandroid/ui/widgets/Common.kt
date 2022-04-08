package com.example.playandroid.ui.widgets

import androidx.compose.foundation.selection.selectable
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.playandroid.ui.page.home.BottomNavRoute


@Composable
fun MainBottomBar(navCtrl: NavHostController) {

    val bottomList = listOf(BottomNavRoute.Home, BottomNavRoute.ProFile)
    val currentDestination = navCtrl.currentBackStackEntry?.destination

    BottomNavigation {
        bottomList.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = BottomNavRoute.Home.icon, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = item.name))
                },
                selected = currentDestination?.hierarchy?.any { it.route == item.routeName } == true,
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