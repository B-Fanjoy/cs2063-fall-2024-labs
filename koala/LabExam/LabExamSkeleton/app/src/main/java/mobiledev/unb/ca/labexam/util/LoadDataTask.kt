package mobiledev.unb.ca.labexam.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.Toast
import mobiledev.unb.ca.labexam.model.EventInfo
import mobiledev.unb.ca.labexam.MyAdapter
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class LoadDataTask(private val activity: AppCompatActivity) {
    private val appContext: Context = activity.applicationContext
    private var progressBar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var sharedPreferences: SharedPreferences? = null

    fun setProgressBar(progressBar: ProgressBar?): LoadDataTask {
        this.progressBar = progressBar
        return this
    }

    fun setRecyclerView(recyclerView: RecyclerView?): LoadDataTask {
        this.recyclerView = recyclerView
        return this
    }

    fun setSharedPreferences(sharedPreferences: SharedPreferences?): LoadDataTask {
        this.sharedPreferences = sharedPreferences
        return this
    }

    fun execute() {
        // TODO
        //  Show the progress bar
        //  Hint:
        //    Read the documentation on ProgressBar - http://developer.android.com/reference/android/widget/ProgressBar.html
        //    Refer to the threading examples should you need extra inspiration
        progressBar!!.visibility = ProgressBar.VISIBLE

        // Update the display elements in a separate thread
        Executors.newSingleThreadExecutor()
            .execute {
                val mainHandler = Handler(Looper.getMainLooper())
                // TODO
                //  Load the data from the JSON assets file and return the list of host nations
                val jsonUtils = JsonUtils(appContext)
                val eventsInfoList = jsonUtils.getHostCities()

                // Simulating long-running operation
                for (i in 1 until DOWNLOAD_TIME) {
                    sleep()
                    // TODO
                    //  Update the progress bar using values
                    progressBar!!.progress = i
                }

                // TODO
                //  Using the updateDisplay method update the UI with the results
                mainHandler.post { updateDisplay(eventsInfoList) }
            }
    }

    private fun sleep() {
        try {
            val mDelay = 500
            TimeUnit.MILLISECONDS.sleep(mDelay.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun updateDisplay(eventsInfoList: List<EventInfo>) {
        // TODO
        //  Reset the progress bar, and make it disappear
        progressBar!!.progress = 0
        progressBar!!.visibility = ProgressBar.INVISIBLE

        // TODO
        //  Setup the RecyclerView using the setupRecyclerView method
        setupRecyclerView(eventsInfoList)

        // TODO
        //  Create a Toast indicating that the file has been loaded
        Toast.makeText(appContext, "File has been loaded", Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(eventsInfoList: List<EventInfo>) {
        recyclerView!!.adapter = MyAdapter(activity, eventsInfoList, sharedPreferences!!)
    }

    companion object {
        private const val DOWNLOAD_TIME = 10 // Download time simulation
    }
}