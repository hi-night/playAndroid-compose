package com.example.playandroid.ui.page.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.playandroid.R

sealed class BottomNavRoute(var routeName: String, @StringRes var name: Int, var icon: ImageVector) {
    object Home : BottomNavRoute(RouteName.HOME, R.string.home, Icons.Default.Home)
    object ProFile : BottomNavRoute(RouteName.PROFILE, R.string.profile, Icons.Default.Person)
}