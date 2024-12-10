package uk.ac.aber.dcs.cs31620.quizappnew.ui

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizappnew.data.Question
import uk.ac.aber.dcs.cs31620.quizappnew.data.QuestionWithAnswers
import uk.ac.aber.dcs.cs31620.quizappnew.data.loadQuestionsFromDatabase
import uk.ac.aber.dcs.cs31620.quizappnew.data.toQuestion
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.QuizScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

@Composable
fun CorrectScreen(
    navController: NavHostController,
    questionNum: Int,
    correct: Int
) {
    QuizScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            CorrectScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController,
                questionNum = questionNum,
                correct = correct
            )

        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CorrectScreenContent(
    modifier: Modifier = Modifier,
    navController : NavHostController,
    questionNum: Int,
    correct: Int
) {

    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }

    var questionsList by remember {
        mutableStateOf(mutableListOf<Question>())
    }

    LaunchedEffect(Unit) {
        isLoading = true
        val questionsWithAnswers: List<QuestionWithAnswers> = loadQuestionsFromDatabase(context)
        questionsList = questionsWithAnswers.map { it.toQuestion() }.toMutableList()
        isLoading = false
    }

    if (!isLoading) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(200.dp))

            Text(
                text = "Correct!",
                fontSize = 24.sp,
                textAlign = Center,
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "\uD83E\uDD29",
                fontSize = 100.sp,
                textAlign = Center,
            )

            Spacer(modifier = Modifier.height(25.dp))

            FilledTonalButton(
                onClick = {
                    if (questionNum >= questionsList.size - 1) {
                        navController.navigate(Screen.Finish.createRoute(correct))
                    } else {
                        navController.navigate(
                            Screen.Question.createRoute(
                                questionNum = questionNum + 1,
                                correct = correct
                            )
                        )
                    }
                },
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(text = "Next Question")
            }
        }
    } else {
        Text("Loading")
    }
}

@Preview
@Composable
fun CorrectScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        CorrectScreen(navController,0,0)
    }
}