package uk.ac.aber.dcs.cs31620.quizappnew.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

fun loadQuestionsFromFile(context: Context, fileName: String): MutableList<Question> {
    val gson = Gson()
    val type = object : TypeToken<List<Question>>() {}.type

    context.assets.open(fileName).use { inputStream ->
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return gson.fromJson(jsonString, type)
    }
}