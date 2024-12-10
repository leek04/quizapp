package uk.ac.aber.dcs.cs31620.quizappnew.ui

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
import uk.ac.aber.dcs.cs31620.quizappnew.data.toQuestion
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    //uses custom scaffold component for ui structure
    TopLevelScaffold(
        navController = navController
        //padding to avoid overlapping content
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            //after rendering scaffold, it then renders the rest of the home page
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
        //spaces out the text so it looks cleaner
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
    //remembers the state of the questionslist while being mapped
    var questionsList by remember {
        mutableStateOf(mutableListOf<Question>())
    }

    //starts a coroutine that loads questions from the database
    LaunchedEffect(Unit) {
        val questionsWithAnswers: List<QuestionWithAnswers> = loadQuestionsFromDatabase(context)
        //maps the loaded questions onto the Question class.
        // i chose to do it this way because the Question class was being used a lot before i added the database
        //and i didnt want to go back and redo all the work i had done
        questionsList = questionsWithAnswers.map { it.toQuestion() }.toMutableList()
    }

    //checks if there are actually questions to show
    if (questionsList.isNotEmpty()) {
        //displays the questions in a dynamic list, that changes length depending on how many questions there are
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //each question is displayed as a card
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
            //then a button to start the quiz is at the bottom of the list
            item {
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
    //if there are no questions
    else {

        Text(
            text = "There are no Questions!",
            fontSize = 24.sp,
            textAlign = Center,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}

//displays what the page will look like in the ide
@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        HomeScreen(navController)
    }
}



