package uk.ac.aber.dcs.cs31620.quizappnew.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.quizappnew.data.Question
import uk.ac.aber.dcs.cs31620.quizappnew.ui.components.TopLevelScaffold
import uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation.Screen

@Composable
fun CorrectScreen(
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
            CorrectScreenContent(
                modifier = Modifier.padding(8.dp),
                navController = navController
            )

        }
    }
}

@Composable
fun CorrectScreenContent(
    modifier: Modifier = Modifier,
    navController : NavHostController,
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Correct!",
            fontSize = 24.sp,
            textAlign = Center,
        )

        Spacer(modifier = Modifier.height(50.dp))

        FilledTonalButton(
            onClick = {navController.navigate(Screen.Question.route)},
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(text = "Next Question")
        }
    }
}