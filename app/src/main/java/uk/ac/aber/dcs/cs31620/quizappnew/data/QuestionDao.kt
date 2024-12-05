package uk.ac.aber.dcs.cs31620.quizappnew.data

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction

@Dao
interface QuestionDao {
    @Insert
    suspend fun insertQuestion(question: newQuestion): Long

    @Insert
    suspend fun insertAnswers(answers: List<Answer>)

    @Transaction
    @Query("SELECT * FROM newQuestion")
    abstract fun getAllQuestions(): List<QuestionWithAnswers>
}

data class QuestionWithAnswers(
    @Embedded val question: newQuestion,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val answers: List<Answer>
)

fun QuestionWithAnswers.toQuestion(): Question {
    return Question(
        questionText = question.questionText,
        answers = answers.map { it.answerText }, // Convert AnswerEntity to String
        correctAnswerIndex = question.correctAnswerIndex
    )
}