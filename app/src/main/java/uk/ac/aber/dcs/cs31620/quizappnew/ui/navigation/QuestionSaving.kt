package uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class QuestionSaving(
    val route: String, val icon: ImageVector, val label: String
) {

    object Save : QuestionSaving("home", Icons.Filled.Done, "Save")
    object Cancel : QuestionSaving("home", Icons.Filled.Close, "Cancel")
    object Delete : QuestionSaving("home", Icons.Filled.Delete, "Delete")
}