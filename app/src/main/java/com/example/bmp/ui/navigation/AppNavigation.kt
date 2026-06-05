package com.example.bmp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bmp.ui.articles.ArticleListScreen
import com.example.bmp.ui.bookmarks.BookmarksScreen
import com.example.bmp.ui.navigation.Screen.ArticleList
import com.example.bmp.ui.navigation.Screen.Bookmarks

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = ArticleList.route){
        composable(ArticleList.route){
            ArticleListScreen(navController)
        }
        composable(Bookmarks.route){
            BookmarksScreen(navController)
        }
    }
}

sealed class Screen(val route:String){
    object ArticleList : Screen(route = "article_list")
    object Bookmarks : Screen(route = "bookmarks")
}