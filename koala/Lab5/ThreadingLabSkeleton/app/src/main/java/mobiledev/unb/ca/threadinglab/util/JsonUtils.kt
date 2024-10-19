package mobiledev.unb.ca.threadinglab.util

import android.content.Context
import android.content.res.AssetManager
import mobiledev.unb.ca.threadinglab.model.Course
import org.json.JSONObject
import org.json.JSONException
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class JsonUtils(context: Context) {
    private lateinit var courses: ArrayList<Course>

    private fun processJSON(context: Context): ArrayList<Course> {
        courses = ArrayList()
        try {
            // Create a JSON Object from file contents String
            val jsonObject = JSONObject(Objects.requireNonNull(loadJSONFromAssets(context)))

            // Create a JSON Array from the JSON Object
            // This array is the "courses" array mentioned in the lab write-up
            val jsonArray = jsonObject.getJSONArray(KEY_COURSES)
            for (i in 0 until jsonArray.length()) {
                // TODO 1:
                //  Using the JSON array set the array of courses
                //  1. Retrieve the current JSON object from the array by index
                //  2. Using the JSON object create a Course object
                //  3. Add the Course object to courses ArrayList
                val newCourse = jsonArray.getJSONObject(i)
                val newCourse2 = Course(newCourse.getString("courseID"), newCourse.getString("name"), newCourse.getString("description"))
                courses.add(newCourse2)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return courses
    }

    private fun loadJSONFromAssets(context: Context): String? {
        // TODO 2
        //  1. Obtain an instance of the AssetManager class from the referenced context
        //    (https://developer.android.com/reference/android/content/Context#getAssets())
        //  2. Open the CS_JSON_FILE from the assets folder
        //     (https://developer.android.com/reference/android/content/res/AssetManager)
        //  3. Read the file contents into a string
        //  HINT:
        //   A BufferedReader (https://www.geeksforgeeks.org/read-from-files-using-bufferedreader-in-kotlin/)
        //   or InputStream (https://www.baeldung.com/kotlin/inputstream-to-string)
        //   works well in this case

        val inputString = context.assets.open("CS.json").bufferedReader ().use {
            it.readText ()
        }
        return inputString
    }

    // Getter method for courses ArrayList
    fun getCourses(): ArrayList<Course> {
        return courses
    }

    companion object {

        lateinit var courses: ArrayList<Course>

        fun processJSON(context: Context): ArrayList<Course> {
            courses = ArrayList()
            try {
                // Create a JSON Object from file contents String
                val jsonObject = JSONObject(Objects.requireNonNull(loadJSONFromAssets(context)))

                // Create a JSON Array from the JSON Object
                // This array is the "courses" array mentioned in the lab write-up
                val jsonArray = jsonObject.getJSONArray(KEY_COURSES)
                for (i in 0 until jsonArray.length()) {
                    // TODO 1:
                    //  Using the JSON array set the array of courses
                    //  1. Retrieve the current JSON object from the array by index
                    //  2. Using the JSON object create a Course object
                    //  3. Add the Course object to courses ArrayList
                    val newCourse = jsonArray.getJSONObject(i)
                    val newCourse2 = Course(newCourse.getString("courseID"), newCourse.getString("name"), newCourse.getString("description"))
                    courses.add(newCourse2)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return courses
        }

        fun loadJSONFromAssets(context: Context): String? {
            val inputString = context.assets.open("CS.json").bufferedReader ().use {
                it.readText ()
            }
            return inputString
        }

        private const val CS_JSON_FILE = "CS.json"
        private const val KEY_COURSES = "courses"
        private const val KEY_COURSE_ID = "courseID"
        private const val KEY_NAME = "name"
        private const val KEY_DESCRIPTION = "description"
    }

    // Initializer to read our data source (JSON file) into an array of course objects
    init {
        processJSON(context)
    }
}