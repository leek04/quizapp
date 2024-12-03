package uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String, val icon: ImageVector, val label: String
) {

    object Home : Screen("home", Icons.Filled.Home, "Home")
    object AddQuestion : Screen("AddQuestion",Icons.Filled.Create, "Add Question")
    object StartQuiz : Screen("StartQuiz", Icons.Filled.PlayArrow, "Start Quiz!")
    object RemoveQuestion : Screen("RemoveQuestion",Icons.Filled.Delete, "Remove Question")
    object Question : Screen("question/questionNum",Icons.Filled.Delete, "Question") {
        fun createRoute(questionNum: Int) = "question/$questionNum"
    }
    object Correct : Screen("correct/questionNum",Icons.Filled.Close, "Correct") {
        fun createRoute(questionNum: Int) = "correct/$questionNum"
    }
    object Incorrect : Screen("incorrect/questionNum",Icons.Filled.Close, "Inorrect") {
        fun createRoute(questionNum: Int) = "incorrect/$questionNum"
    }
}

val screens = listOf(
    Screen.Home,
    Screen.AddQuestion,
    Screen.StartQuiz,
    Screen.RemoveQuestion,
    Screen.Question,
    Screen.Correct,
    Screen.Incorrect
)