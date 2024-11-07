package mobiledev.unb.ca.labexam

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO
        //  Get the intent that started this activity along with the extras added to it
        val intent = this.intent

        // TODO
        //  Set the details for the number, year, and dates text views
        val number = intent.getStringExtra("number")
        val year = intent.getStringExtra("year")
        val dates = intent.getStringExtra("dates")

        val numberTextView = findViewById<TextView>(R.id.number_textview)
        numberTextView.text = number
        val yearTextView = findViewById<TextView>(R.id.year_textview)
        yearTextView.text = year
        val datesTextView = findViewById<TextView>(R.id.dates_textview)
        datesTextView.text = dates

        // TODO
        //  Set an onClickListener such that when this button is clicked, an implicit intent is started
        //  to open the wikipedia URL in a web browser. Be sure to check that there is
        //  an application installed that can handle this intent before starting it.
        //  If the intent can't be started, show a toast indicating this.
        // Hints:
        // https://developer.android.com/reference/android/content/Intent.html#resolveActivity(android.content.pm.PackageManager)
        // https://developer.android.com/guide/components/intents-common.html#Browser
        // https://developer.android.com/reference/android/net/Uri.html#parse(java.lang.String)
        val wikiButton = findViewById<Button>(R.id.wiki_button)
        wikiButton.setOnClickListener{
            val wikiUrl = intent.getStringExtra("url")
            val webpage: Uri = Uri.parse(wikiUrl)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "No application can handle this intent", Toast.LENGTH_SHORT).show()
            }
        }

        // TODO
        //  Set the title of the action bar to be the host nation name
        val hostNation = intent.getStringExtra("hostNation")
        supportActionBar?.title = hostNation

    }
}