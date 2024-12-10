package uk.ac.aber.dcs.cs31620.quizappnew.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.ui.graphics.vector.ImageVector

sealed class QuestionSaving(
    val route: String, val icon: ImageVector, val label: String
) {

    data object Save : QuestionSaving("home", Icons.Filled.Done, "Save")
    data object Cancel : QuestionSaving("home", Icons.Filled.Close, "Cancel")
    data object Delete : QuestionSaving("home", Icons.Filled.Delete, "Delete")
}