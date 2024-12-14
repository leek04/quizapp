package uk.ac.aber.dcs.cs31620.quizappnew.ui

import android.content.Context
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.quizappnew.data.Answer
import uk.ac.aber.dcs.cs31620.quizappnew.data.QuestionWithAnswers
import uk.ac.aber.dcs.cs31620.quizappnew.data.QuizDatabase.Companion.getDatabase
import uk.ac.aber.dcs.cs31620.quizappnew.data.loadQuestionsFromDatabase
import uk.ac.aber.dcs.cs31620.quizappnew.data.newQuestion
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.EditQuestionScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

/**
 * I GAVE UP ON THIS, SO IT WILL NOT BE INCLUDED IN THE FINAL PROJECT VERSION
 */

var editIndex by mutableIntStateOf(-1)
var questionToEdit: newQuestion = newQuestion(0,"",0)
var choosing by mutableStateOf(true)

@Composable
fun EditQuestionScreen(
    navController: NavHostController
) {

    EditQuestionScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            EditQuestionScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController
            )

        }
    }
}

@Composable
fun EditQuestionScreenContent(
    modifier: Modifier = Modifier,
    navController : NavHostController
) {
    Spacer(modifier = Modifier.height(8.dp))

    EditQuestionList()
}

@Composable
fun EditQuestionList() {

    var isLoading by remember { mutableStateOf(true) }

    val context = LocalContext.current
    var questionsList: List<QuestionWithAnswers> by remember { mutableStateOf(emptyList()) }

    LaunchedEffect(Unit) { // Trigger only once when the composable is recomposed
        isLoading = true
        questionsList = loadQuestionsFromDatabase(context)
        isLoading = false
    }

    //i tried to make it one big screen instead of two larger screens, as it would make passing data easier, but it was still to complicated
    if (!isLoading) {
        if (choosing) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(questionsList) { index, _ ->
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
                            Text(text = questionsList[index].question.questionText)

                            RadioButton(
                                selected = deleteIndex == index,
                                onClick = {
                                    editIndex = index
                                    questionToEdit = questionsList[index].question
                                    choosing = false
                                }
                            )
                        }
                    }
                }
            }
        } else {
            EditQuestionQuestion(questionsList[editIndex])
        }
    }
    else {
        Text("loading")
    }
}

@Composable
fun EditQuestionQuestion(questionToEdit: QuestionWithAnswers) {

    var answerListNum by remember {
        mutableIntStateOf(questionToEdit.answers.size)

    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                shape = MaterialTheme.shapes.extraLarge,
            ) {
                Row(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropdownMenuBox(selectedValue = answerListNum, onValueChange = {
                        newValue -> answerListNum = newValue
                        //removes answers that go past selected number
                        for (x in 1..(10 - answerListNum)) {
                            answers.removeLast()
                        }
                    })
                }
            }
        }
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
                    QuestionTextField2(questionToEdit)
                }
            }
        }

        itemsIndexed(List(answerListNum) { it }) { index, _ ->
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
                    AnswerTextField2(index = index,questionToEdit)

                    RadioButton(
                        selected = correctAnswerIndex == index,
                        onClick = { correctAnswerIndex = index
                            println("index = $index")}
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerTextField2(index: Int,questionToEdit: QuestionWithAnswers) {
    var text by remember {
        if(index < questionToEdit.answers.size) {
        mutableStateOf(questionToEdit.answers[index].answerText)}
        else {
            mutableStateOf("Enter Value")}
    }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Possible Answer") }
    )

    answers[index] = text
}

@Composable
fun QuestionTextField2(questionToEdit: QuestionWithAnswers) {
    var text by remember { mutableStateOf(questionToEdit.question.questionText) }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Possible Answer") }
    )

    questionText = text
}

fun EditQuestion(context: Context) {

    //gets questionDao for modifying database
    val database = getDatabase(context)
    val questionDao = database.questionDao()

    GlobalScope.launch {
        //MAKE SURE ANSWER IS DELETED
        questionDao.deleteAnswerByquestionId(questionToEdit.id)
        // then delete question
        questionDao.deleteQuestion(questionToEdit)

        val newQuestion = newQuestion(questionText = questionText, correctAnswerIndex = correctAnswerIndex)
        val questionId = questionDao.insertQuestion(newQuestion) // Insert question and get its ID


        answers.removeAll{it.isEmpty()}

        val newAnswers = answers.mapIndexed { _, answerText ->
            Answer(
                questionId = questionId.toInt(), // Associate answers with the question ID
                answerText = answerText,
            )
        }


        println(questionDao.getAllQuestions())

        questionDao.insertAnswers(newAnswers) // Insert all answers

        //re declare answer defaults
        editIndex = -1
        questionToEdit= newQuestion(0,"",0)
        choosing = true
        answers = mutableListOf("","","","","","","","","","")
        correctAnswerIndex = 0
        questionText = ""
    }
}

@Preview
@Composable
fun EditQuestionScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        EditQuestionScreen(navController)
    }
}