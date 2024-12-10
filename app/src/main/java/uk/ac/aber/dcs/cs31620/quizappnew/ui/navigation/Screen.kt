package uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String, val icon: ImageVector, val label: String
) {

    data object Home : Screen("home", Icons.Filled.Home, "Home")
    data object AddQuestion : Screen("AddQuestion",Icons.Filled.Create, "Add Question")
    data object StartQuiz : Screen("StartQuiz", Icons.Filled.PlayArrow, "Start Quiz!")
    data object RemoveQuestion : Screen("RemoveQuestion",Icons.Filled.Delete, "Remove Question")
    data object Question : Screen("question/{questionNum}/{correct}",Icons.Filled.Delete, "Question") {
        fun createRoute(questionNum: Int, correct: Int) = "question/$questionNum/$correct"
    }
    data object Correct : Screen("correct/{questionNum}/{correct}",Icons.Filled.Close, "Correct") {
        fun createRoute(questionNum: Int, correct: Int) = "correct/$questionNum/$correct"
    }
    data object Incorrect : Screen("incorrect/{questionNum}/{correct}",Icons.Filled.Close, "Incorrect") {
        fun createRoute(questionNum: Int, correct: Int) = "incorrect/$questionNum/$correct"
    }

    data object Finish : Screen("incorrect/{correct}",Icons.Filled.ThumbUp, "Finish") {
        fun createRoute(correct: Int) = "finish/$correct"
    }
}
