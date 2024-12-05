package uk.ac.aber.dcs.cs31620.quizappnew.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class newQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionText: String,
    val correctAnswerIndex: Int
)

@Entity(
    foreignKeys = [ForeignKey(
        entity = newQuestion::class,
        parentColumns = ["id"],
        childColumns = ["questionId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Answer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionId: Int,
    val answerText: String
)