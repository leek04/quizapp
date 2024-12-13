package uk.ac.aber.dcs.cs31620.quizappnew.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

@Composable
fun MainPageNavBar(navController: NavHostController) {

    val items = listOf(
        Screen.AddQuestion,
        Screen.RemoveQuestion,
    )

    NavigationBar {
        items.forEach { item ->
            AddItem(
                screen = item,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: Screen,
    navController: NavHostController
) {
    NavigationBarItem(
        // Text that shows bellow the icon
        label = {
            Text(text = screen.label)
        },
        // Display if the icon it is select or not
        selected = false,

        // The icon resource
        icon = { Icon(imageVector = screen.selectedIcon, contentDescription = null)},

        // Always show the label bellow the icon or not
        alwaysShowLabel = true,

        // Click listener for the icon
        onClick = {navController.navigate(screen.route)},

        // Control all the colors of the icon
        colors = NavigationBarItemDefaults.colors()
    )
}


@Preview
@Composable
private fun MainPageNavigationBarPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        MainPageNavBar(navController)
    }
}