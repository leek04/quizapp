package uk.ac.aber.dcs.cs31620.quizappnew.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizappnew.ui.SaveQuestionToFile
import uk.ac.aber.dcs.cs31620.quizappnew.ui.deleteQuestion
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.QuestionSaving
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme


@Composable
fun RemoveQuestionNavBar(navController: NavHostController) {

    val items = listOf(
        QuestionSaving.Delete,
        QuestionSaving.Cancel
    )

    NavigationBar {
        items.forEach { item ->
            AddItemRemoveQuestion(
                options = item,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItemRemoveQuestion(
    options: QuestionSaving,
    navController: NavHostController
) {
    val context = LocalContext.current

    NavigationBarItem(
        // Text that shows bellow the icon
        label = {
            Text(text = options.label)
        },

        // The icon resource
        icon = {options.icon},

        // Display if the icon it is select or not
        selected = true,

        // Always show the label bellow the icon or not
        alwaysShowLabel = true,

        // Click listener for the icon
        onClick = {
            if (options.label == "Delete") {

                deleteQuestion(context)
                navController.navigate(options.route)
            } else {
                navController.navigate(options.route)
            } },

        // Control all the colors of the icon
        colors = NavigationBarItemDefaults.colors()
    )
}

@Preview
@Composable
private fun MainPageNavigationBarPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        RemoveQuestionNavBar(navController)
    }
}