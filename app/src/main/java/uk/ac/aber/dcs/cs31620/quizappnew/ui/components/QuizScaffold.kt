package uk.ac.aber.dcs.cs31620.quizappnew.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController

@Composable
fun QuizScaffold(
    navController: NavHostController,
    pageContent: @Composable (innerPadding: PaddingValues) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MainPageTopAppBar(navController)
        },
        content = { innerPadding -> pageContent(innerPadding) }
    )
}