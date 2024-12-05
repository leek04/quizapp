package uk.ac.aber.dcs.cs31620.quizappnew.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.currentCoroutineContext
import uk.ac.aber.dcs.cs31620.quizappnew.data.Question
import uk.ac.aber.dcs.cs31620.quizappnew.data.loadQuestionsFromFile
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.QuizScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

@Composable
fun IncorrectScreen(
    navController: NavHostController,
    questionNum: Int
) {
    QuizScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            IncorrectScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController,
                questionNum = questionNum
            )

        }
    }
}

@Composable
fun IncorrectScreenContent(
    modifier: Modifier = Modifier,
    navController : NavHostController,
    questionNum: Int
) {

    val context = LocalContext.current
    val questionsList = loadQuestionsFromFile(context,"questions.json")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Incorrect!",
            fontSize = 24.sp,
            textAlign = Center,
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "The correct answer was... ",
            fontSize = 24.sp,
            textAlign = Center
        )

        Spacer(modifier = Modifier.height(50.dp))

        val correctAnswerNum: Int = questionsList[questionNum].correctAnswerIndex

        Text(
            text = (correctAnswerNum+1).toString() + " : " + questionsList[questionNum].answers[correctAnswerNum],
            fontSize = 24.sp,
            textAlign = Center
        )

        Spacer(modifier = Modifier.height(50.dp))

        FilledTonalButton(
            onClick = {if (questionNum >= questionsList.size) {
                navController.navigate(Screen.Finish.route)
            } else {
                navController.navigate(Screen.Question.createRoute(questionNum = questionNum+1))
            }},
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(text = "Next Question")
        }
    }
}

@Preview
@Composable
fun IncorrectScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        IncorrectScreen(navController,0)
    }
}