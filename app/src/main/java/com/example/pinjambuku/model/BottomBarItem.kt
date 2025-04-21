package com.example.pinjambuku.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pinjambuku.ui.navigation.Screen

data class BottomBarItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)
