package uk.ac.aber.dcs.cs31620.quizappnew.ui

import android.content.Context
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.google.gson.Gson
import uk.ac.aber.dcs.cs31620.quizappnew.data.Question
import uk.ac.aber.dcs.cs31620.quizappnew.data.loadQuestionsFromFile
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.AddQuestionScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.AddQuestionTopAppBar
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.RemoveQuestionScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme
import java.io.File

var deleteIndex by mutableStateOf(0)

@Composable
fun RemoveQuestionScreen(
    navController: NavHostController
) {

    RemoveQuestionScaffold(
        navController = navController
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            RemoveQuestionScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController
            )

        }
    }
}

@Composable
fun RemoveQuestionScreenContent(
    modifier: Modifier = Modifier,
    navController : NavHostController
) {
    Spacer(modifier = Modifier.height(8.dp))

    RemoveQuestionList()
}

@Composable
fun RemoveQuestionList() {

    var questionsList = listOf<Question>()
    val context = LocalContext.current
    questionsList = loadQuestionsFromFile(context,"questions.json")

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
                    Text(text = questionsList[index].questionText)

                    RadioButton(
                        selected = deleteIndex == index,
                        onClick = { deleteIndex = index }
                    )
                }
            }
        }
    }
}

fun deleteQuestion(context: Context) {
    var questionsList = mutableListOf<Question>()
    questionsList = loadQuestionsFromFile(context,"questions.json")

    questionsList.removeAt(deleteIndex)

    val file = File(context.filesDir, "questions.json")
    val gson = Gson()

    val jsonString = gson.toJson(questionsList)
    println(jsonString)
    file.writeText(jsonString)
}

@Preview
@Composable
fun RemoveQuestionScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        RemoveQuestionScreen(navController)
    }
}