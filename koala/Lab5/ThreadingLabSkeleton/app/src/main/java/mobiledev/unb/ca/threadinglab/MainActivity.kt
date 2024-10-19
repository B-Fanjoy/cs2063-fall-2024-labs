package mobiledev.unb.ca.threadinglab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import mobiledev.unb.ca.threadinglab.util.LoadDataTask

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO 1
        //  Get a reference to the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // TODO 2
        //  Get a reference to the progress indicator
        //  HINT: The class type to be used can be found 
        //        in the activity_main.xml layout file
        val progressIndicator: CircularProgressIndicator = findViewById(R.id.circularProgressIndicator)

        // TODO 3
        //  Create an instance of LoadDataTask using this activity in the
        //  constructor and use the setters to pass in the
        //  recycler view and progress bar objects needed during execution
        val loadDataTask = LoadDataTask(this)
        loadDataTask.setRecyclerView(recyclerView)
        loadDataTask.setCircularProgressIndicator(progressIndicator)
        loadDataTask.execute()
    }
}