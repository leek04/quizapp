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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.quizappnew.data.loadQuestionsFromFile
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.QuizScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.quizappnew.ui.theme.QuizAppNewTheme

@Composable
fun FinishScreen(
    navController: NavHostController,
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
                navController = navController
            )

        }
    }
}

@Composable
fun FinishScreenContent(
    modifier: Modifier = Modifier,
    navController : NavHostController
) {
    Spacer(modifier = Modifier.height(8.dp))


    /** TODO **/
    val numPoints: Int = 0

    val context = LocalContext.current
    val questionsList = loadQuestionsFromFile(context,"questions.json")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "You Scored...",
            fontSize = 24.sp,
            textAlign = Center,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = numPoints.toString() + " out of " + questionsList.size,
            fontSize = 24.sp,
            textAlign = Center,
        )

        Spacer(modifier = Modifier.height(20.dp))

        FilledTonalButton(onClick = {
            navController.navigate(Screen.StartQuiz.route)
        }) {
            Text("Home")
        }
    }

}

@Preview
@Composable
fun FinishScreenPreview() {
    val navController = rememberNavController()
    QuizAppNewTheme(dynamicColor = false) {
        FinishScreen(navController)
    }
}
