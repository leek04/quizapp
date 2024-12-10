package uk.ac.aber.dcs.cs31620.quizappnew.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun FinishScreen(
    navController: NavHostController,
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
            FinishScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController,
                correct = correct
            )

        }
    }
}

@Composable
fun FinishScreenContent(
    modifier: Modifier = Modifier,
    navController : NavHostController,
    correct: Int
) {
    Spacer(modifier = Modifier.height(8.dp))

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
                text = "You Scored...",
                fontSize = 24.sp,
                textAlign = Center,
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = correct.toString() + " out of " + questionsList.size,
                fontSize = 24.sp,
                textAlign = Center,
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "\uD83E\uDD73",
                fontSize = 100.sp,
                textAlign = Center,
            )

            Spacer(modifier = Modifier.height(25.dp))

            FilledTonalButton(onClick = {
                navController.navigate(Screen.Home.route)
            }) {
                Text("Home")
            }
        }
    } else {
        Text("loading")
    }
}

@Preview
@Composable
fun FinishScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        FinishScreen(navController,0)
    }
}
