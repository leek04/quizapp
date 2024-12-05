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
    suspend fun insertQuestion(question: Question): Long

    @Insert
    suspend fun insertAnswers(answers: List<Answer>)

    @Transaction
    @Query("SELECT * FROM newQuestion")
    suspend fun getAllQuestionsWithAnswers(): List<QuestionWithAnswers>
    abstract fun getAllQuestions(): List<QuestionWithAnswers>
}

data class QuestionWithAnswers(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val answers: List<Answer>
)