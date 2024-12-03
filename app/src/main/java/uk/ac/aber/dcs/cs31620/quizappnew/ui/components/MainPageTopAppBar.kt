package uk.ac.aber.dcs.cs31620.quizappnew.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizappnew.R
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageTopAppBar(
    navController: NavHostController,
    onClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text("Quiz App")
        },
        colors = TopAppBarDefaults.topAppBarColors(),
        navigationIcon = {
            IconButton(onClick = {navController.navigate(Screen.Home.route)}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    stringResource(R.string.nav_back_button)
                )
            }
        }
    )
}

@Preview
@Composable
private fun MainPageTopAppBarPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        MainPageTopAppBar(navController)
    }
}