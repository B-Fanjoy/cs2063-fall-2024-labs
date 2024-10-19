package mobiledev.unb.ca.threadinglab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.TextView
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO 1
        //  Get the intent that started this activity, and get the extras from it
        //  corresponding to the title and description of the course
        val title = this.getIntent().getStringExtra("name")
        val description = this.getIntent().getStringExtra("description")
        // TODO 2
        //  Set the description TextView to be the course description
        var descTextView = findViewById<TextView>(R.id.description_textview)
        descTextView.setText(description)

        // TODO 3
        //  Make the TextView scrollable
        //  HINT: Set the movementMethod attribute for descTextView
        descTextView.movementMethod = ScrollingMovementMethod()

        // TODO 4
        //  Set the title of the action bar to be the course title
        this.title = title
    }
}