package uk.ac.aber.dcs.cs31620.quizappnew.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import uk.ac.aber.dcs.cs31620.quizappnew.data.loadQuestionsFromFile
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.QuizScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

var choiceIndex by mutableStateOf(0)

@Composable
fun QuestionScreen(
    navController: NavHostController,
    questionsList: List<Question>,
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
            QuestionScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController, questionsList = questionsList, questionNum
            )

        }
    }
}

@Composable
fun QuestionScreenContent(
    modifier: Modifier = Modifier,
    navController : NavHostController,
    questionsList: List<Question>,
    questionNum: Int
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Question " + (questionNum+1) + ":",
            fontSize = 24.sp,
            textAlign = Center,
        )

        Spacer(modifier = Modifier.height(50.dp))

        QuestionList(questionsList, questionNum)

        Spacer(modifier = Modifier.height(50.dp))

        FilledTonalButton(onClick = {
            CheckCorrect(navController,questionsList,questionNum)
        }) {
            Text("Submit")
        }
    }
}

fun CheckCorrect(navController: NavHostController, questionsList: List<Question>, questionNum: Int) {
    if (choiceIndex.equals(questionsList[questionNum].correctAnswerIndex)) {
        println("CORRECT")
        navController.navigate(Screen.StartQuiz.route)
    } else {
        println("INCORRECT")
        navController.navigate(Screen.StartQuiz.route)
    }
}

@Composable
fun QuestionList(questionsList: List<Question>, questionNum: Int) {

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                modifier = Modifier.padding(10.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 25.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(questionsList[questionNum].questionText)
                }
            }
        }
        itemsIndexed(questionsList[questionNum].answers) { index, _ ->
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
                    Text(text = questionsList[questionNum].answers[index])

                    RadioButton(
                        selected = choiceIndex == index,
                        onClick = { choiceIndex = index }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun QuestionScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        QuestionScreen(navController, questionsList = listOf<Question>(), 0)
    }
}