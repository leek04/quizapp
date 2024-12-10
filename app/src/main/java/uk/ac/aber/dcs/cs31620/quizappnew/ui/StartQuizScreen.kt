package uk.ac.aber.dcs.cs31620.quizappnew.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizappnew.R
import uk.ac.aber.dcs.cs31620.quizappnew.data.Question
import uk.ac.aber.dcs.cs31620.quizappnew.data.QuestionWithAnswers
import uk.ac.aber.dcs.cs31620.quizappnew.data.loadQuestionsFromDatabase
import uk.ac.aber.dcs.cs31620.quizappnew.data.toQuestion
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.AddQuestionTopAppBar
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.MainPageTopAppBar
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.QuizScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.RemoveQuestionScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

@Composable
fun StartQuizScreen(
    navController: NavHostController,
    //questionsList: List<Question>
) {

    QuizScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
                StartQuizScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController//, questionsList = questionsList
            )

        }
    }
}

@Composable
fun StartQuizScreenContent(
    modifier: Modifier = Modifier,
    navController : NavHostController,
    //questionsList: List<Question>
) {

    val context = LocalContext.current
    var questionsList by remember {
        mutableStateOf(mutableListOf<Question>())
    }

    LaunchedEffect(Unit) {
        val questionsWithAnswers: List<QuestionWithAnswers> = loadQuestionsFromDatabase(context)
        questionsList = questionsWithAnswers.map { it.toQuestion() }.toMutableList()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Ready to Start?",
            fontSize = 24.sp,
            textAlign = Center,
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "There will be " + questionsList.size + " Questions",
            fontSize = 24.sp,
            textAlign = Center,
        )

        Spacer(modifier = Modifier.height(50.dp))

        FilledTonalButton(
            onClick = {navController.navigate(Screen.Question.createRoute(0,0))},
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(text = "Start Quiz!")
        }
    }
}

@Preview
@Composable
fun StartQuizScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        StartQuizScreen(navController)
    }
}