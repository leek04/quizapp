package uk.ac.aber.dcs.cs31620.quizappnew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.HomeScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.AddQuestionScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.CorrectScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.FinishScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.IncorrectScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.QuestionScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.RemoveQuestionScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.StartQuizScreen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //applying app theme
            QuizAppNewTheme(dynamicColor = false) {
                //main surface container setup
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //building navigation graph
                    BuildNavigationGraph()
                }
            }
        }
    }
}

@Composable
private fun BuildNavigationGraph() {
    //navcontroller manages navigation
    val navController = rememberNavController()

    //sets up navcontroller with start destination, which is home screen
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        //defines destinations that the navcontroller can go to
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.AddQuestion.route) { AddQuestionScreen(navController) }
        composable(Screen.RemoveQuestion.route) { RemoveQuestionScreen(navController) }
        composable(Screen.StartQuiz.route) { StartQuizScreen(navController) }

        composable(
            //route includes the question number and how many points the user has got correct so far
            route = Screen.Question.route,
            arguments = listOf(navArgument("questionNum") {
                //type = NavType.IntType
                //defines as nullable because it was crashing otherwise
                nullable = true
                },
                navArgument("correct") {
                    nullable = true
                }
            )
        ) { backStackEntry ->
            //retrieving arguments from backstackentry
            val questionNum = backStackEntry.arguments?.getString("questionNum")?.toInt() ?: 0
            val correct = backStackEntry.arguments?.getString("correct")?.toInt() ?: 0

            QuestionScreen(
                //all arguments passed to the question display
                navController,
                questionNum,
                correct
            )
        }

        //basically the same as the question screen
        composable(
            route = Screen.Correct.route,
            arguments = listOf(navArgument("questionNum") {
                //type = NavType.IntType
                nullable = true
                },
                navArgument("correct") {
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val questionNum = backStackEntry.arguments?.getString("questionNum")?.toInt() ?: 0
            val correct = backStackEntry.arguments?.getString("correct")?.toInt() ?: 0
            CorrectScreen(
                navController,
                questionNum,
                correct
            )
        }

        composable(
            route = Screen.Incorrect.route,
            arguments = listOf(navArgument("questionNum") {
                //type = NavType.IntType
                nullable = true
            },
                navArgument("correct") {
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val questionNum = backStackEntry.arguments?.getString("questionNum")?.toInt() ?: 0
            val correct = backStackEntry.arguments?.getString("correct")?.toInt() ?: 0
            IncorrectScreen(
                navController,
                questionNum,
                correct
            )
        }

        composable(
            //i have no idea why the route needed to be different here, but it would crash otherwise
            route = "finish/{correct}",
            arguments = listOf(navArgument("correct") {
                nullable = true
            })
        ) { backStackEntry ->
            val correct = backStackEntry.arguments?.getString("correct")?.toInt() ?: 0
            FinishScreen(
                navController,
                //does not need questionNum, unlike the others
                correct
            )
        }
    }
}

