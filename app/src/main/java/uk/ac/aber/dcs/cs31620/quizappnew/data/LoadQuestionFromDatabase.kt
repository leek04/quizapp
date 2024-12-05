package uk.ac.aber.dcs.cs31620.quizappnew.data

import android.content.Context

suspend fun loadQuestionsFromDatabase(context: Context): List<QuestionWithAnswers> {
    val database = QuizDatabase.getDatabase(context)
    return database.questionDao().getAllQuestions()
}