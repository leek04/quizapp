package uk.ac.aber.dcs.cs31620.quizappnew.data

data class Question(
    val questionText: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)