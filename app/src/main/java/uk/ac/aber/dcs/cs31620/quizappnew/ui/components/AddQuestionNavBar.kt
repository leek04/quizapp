package uk.ac.aber.dcs.cs31620.quizappnew.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.quizappnew.data.Question
import uk.ac.aber.dcs.cs31620.quizappnew.data.QuestionWithAnswers
import uk.ac.aber.dcs.cs31620.quizappnew.data.QuizDatabase.Companion.getDatabase
import uk.ac.aber.dcs.cs31620.quizappnew.data.loadQuestionsFromDatabase
import uk.ac.aber.dcs.cs31620.quizappnew.data.toQuestion
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.QuestionSaving
import uk.ac.aber.dcs.cs31620.quizappnew.ui.saveQuestionWithAnswers
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

@Composable
fun AddQuestionNavBar(navController: NavHostController) {

    val items = listOf(
        QuestionSaving.Save,
        QuestionSaving.Cancel
    )

    NavigationBar {
        items.forEach { item ->
            AddItemAddQuestion(
                options = item,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItemAddQuestion(
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
            if (options.label == "Save") {

                val database = getDatabase(context)
                val questionDao = database.questionDao()

                //TODO check if this works, or creates a memory leak
                GlobalScope.launch {
                    saveQuestionWithAnswers(questionDao)
                }
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
private fun AddQuestionNavigationBarPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        AddQuestionNavBar(navController)
    }
}