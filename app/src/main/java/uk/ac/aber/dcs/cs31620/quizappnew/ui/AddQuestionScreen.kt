package uk.ac.aber.dcs.cs31620.quizappnew.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizappnew.data.Answer
import uk.ac.aber.dcs.cs31620.quizappnew.data.QuestionDao
import uk.ac.aber.dcs.cs31620.quizappnew.data.newQuestion
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.AddQuestionScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

var questionText: String = ""
val answers = mutableListOf("","","","","","","","","","")

var correctAnswerIndex by mutableIntStateOf(0)

@Composable
fun AddQuestionScreen(
    navController: NavHostController
) {

    AddQuestionScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AddQuestionScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController
            )

        }
    }
}

@Composable
fun AddQuestionScreenContent(
    modifier: Modifier = Modifier,
    navController : NavHostController
) {
    Spacer(modifier = Modifier.height(8.dp))

    AddQuestionList()
}

@Composable
fun AddQuestionList() {

    var answerListNum by remember {
        mutableIntStateOf(1)

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
                    Demo_ExposedDropdownMenuBox(selectedValue = answerListNum, onValueChange = {newValue -> answerListNum = newValue})
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
                    QuestionTextField()
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
                    AnswerTextField(index = index)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_ExposedDropdownMenuBox(selectedValue: Int, onValueChange : (Int) -> Unit) {
    val nums = arrayOf(1,2,3,4,5,6,7,8,9,10)
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(nums[0].toString()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                nums.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.toString()) },
                        onClick = {
                            selectedText = item.toString()
                            onValueChange(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerTextField(index: Int) {
    var text by remember { mutableStateOf("Enter Value") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Possible Answer") }
    )

    answers[index] = text
}

@Composable
fun QuestionTextField() {
    var text by remember { mutableStateOf("Enter Value") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Question") }
    )

    questionText = text
}

//fun SaveQuestionToFile(context: Context, filename: String) {
//
//    for (i in answers.indices) {
//        if (i.equals("")) {
//            answers.removeAt(i)
//        }
//    }
//
//    val question = Question(
//        questionText = questionText,
//        answers = answers,
//        correctAnswerIndex = correctAnswerIndex
//    )
//
//    println(question.toString())
//
//    val file = File(context.filesDir, filename)
//    val gson = Gson()
//
//    val jsonString = gson.toJson(question)
//    println(jsonString)
//    file.writeText(jsonString)
//}

suspend fun saveQuestionWithAnswers(dao: QuestionDao) {
    // Convert the Question to NewQuestion and NewAnswer entities
    val newQuestion = newQuestion(questionText = questionText, correctAnswerIndex = correctAnswerIndex)
    val questionId = dao.insertQuestion(newQuestion) // Insert question and get its ID

    answers.removeAll{it.isEmpty()}

    val newAnswers = answers.mapIndexed { _, answerText ->
        Answer(
            questionId = questionId.toInt(), // Associate answers with the question ID
            answerText = answerText,
        )
    }

    println(dao.getAllQuestions())

    dao.insertAnswers(newAnswers) // Insert all answers
}

@Preview
@Composable
fun AddQuestionScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        AddQuestionScreen(navController)
    }
}