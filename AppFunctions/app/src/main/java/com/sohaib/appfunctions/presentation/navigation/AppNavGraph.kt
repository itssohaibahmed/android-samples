package com.sohaib.appfunctions.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sohaib.appfunctions.presentation.add.AddNoteScreen
import com.sohaib.appfunctions.presentation.edit.EditNoteScreen
import com.sohaib.appfunctions.presentation.home.HomeScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToAdd = { navController.navigate(Routes.ADD) },
                onNavigateToEdit = { noteId -> navController.navigate(Routes.edit(noteId)) },
            )
        }
        composable(Routes.ADD) {
            AddNoteScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = Routes.EDIT,
            arguments = listOf(
                navArgument("noteId") { type = NavType.LongType },
            ),
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: return@composable
            EditNoteScreen(
                noteId = noteId,
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}