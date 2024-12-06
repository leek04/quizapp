package uk.ac.aber.dcs.cs31620.quizappnew.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
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
import uk.ac.aber.dcs.cs31620.quizappnew.data.loadQuestionsFromFile
import uk.ac.aber.dcs.cs31620.quizappnew.data.toQuestion
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    TopLevelScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HomeScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController
            )

        }
    }
}

    @Composable
    fun HomeScreenContent(
        modifier: Modifier = Modifier,
        navController : NavHostController
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Question List",
            fontSize = 24.sp,
            textAlign = Center,
        )

        Spacer(modifier = Modifier.height(20.dp))

        QuestionsList(
            navController = navController
        )
    }

@Composable
fun QuestionsList(
    navController : NavHostController
) {
    val context = LocalContext.current
    //val questionsList = loadQuestionsFromFile(context,"questions.json")
    var questionsList = listOf<Question>()

    LaunchedEffect(Unit) {
        val questionsWithAnswers: List<QuestionWithAnswers> = loadQuestionsFromDatabase(context)
        questionsList = questionsWithAnswers.map { it.toQuestion() }
    }


    if (questionsList.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(questionsList.count()) { question ->
                Card(
                    modifier = Modifier.padding(10.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 25.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = questionsList[question].questionText)
                    }
                }
            }
            item() {
                Card(
                    modifier = Modifier.padding(10.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .align(alignment = Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilledTonalButton(onClick = {
                            navController.navigate(Screen.StartQuiz.route)
                        }) {
                            Text("Start Quiz")
                        }
                    }
                }
            }
        }
    }
    else {

        Text(
            text = "There are no Questions!",
            fontSize = 24.sp,
            textAlign = Center,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        HomeScreen(navController)
    }
}



