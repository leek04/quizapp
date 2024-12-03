package uk.ac.aber.dcs.cs31620.quizappnew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.aber.dcs.cs31620.quizappnew.data.loadQuestionsFromFile
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.HomeScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.AddQuestionScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.CorrectScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.FinishScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.QuestionScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.RemoveQuestionScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.StartQuizScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppNewTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BuildNavigationGraph()
                }
            }
        }
    }
}

@Composable
private fun BuildNavigationGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.AddQuestion.route) { AddQuestionScreen(navController) }
        composable(Screen.RemoveQuestion.route) { RemoveQuestionScreen(navController) }
        composable(Screen.Finish.route) { FinishScreen(navController)}
        composable(Screen.StartQuiz.route) {
            StartQuizScreen(
                navController,
                loadQuestionsFromFile(context, "questions.json")
            )
        }
        composable(
            route = Screen.Question.route,
            arguments = listOf(navArgument("questionNum") { type = NavType.IntType })
        ) { backStackEntry ->
            val questionNum = backStackEntry.arguments?.getInt("questionNum") ?: 0
            QuestionScreen(
                navController,
                loadQuestionsFromFile(context, "questions.json"),
                questionNum
            )
        }
        composable(
            route = Screen.Correct.route,
            arguments = listOf(navArgument("questionNum") { type = NavType.IntType })
        ) { backStackEntry ->
            val questionNum = backStackEntry.arguments?.getInt("questionNum") ?: 0
            CorrectScreen(
                navController,
                questionNum
            )
        }
    }

}

