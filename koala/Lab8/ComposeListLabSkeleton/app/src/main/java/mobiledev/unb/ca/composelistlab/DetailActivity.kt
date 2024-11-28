package mobiledev.unb.ca.composelistlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mobiledev.unb.ca.composelistlab.models.Course
import mobiledev.unb.ca.composelistlab.models.testCourse
import mobiledev.unb.ca.composelistlab.ui.theme.ComposeListLabSkeletonTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Extract the Parcelable object from the extras for use
        val currIntent = intent
        val course = currIntent.getParcelableExtra<Course>(Constants.INTENT_EXTRA_COURSE_OBJECT)

        setContent {
            ComposeListLabSkeletonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DetailContent(course = course, navigateBack = { finish() })
                }
            }
        }
    }
}

@Composable
fun DetailContent(
    course: Course?,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // TODO 1: Set the title as the course title
                    //  Hint: Look at using the Text object (also used in MainActivity)
                    Text(course?.id ?: "Course Details")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = LocalContext.current.getString(R.string.lbl_go_back),
                        )
                    }
                },
            )
        },
    ) {
        // TODO 2: Set the content as the course description
        //  Hint: Look at using the Text object

        // TODO - Optional: Update the view to use the following attributes:
        //   The ID as the top bar text instead of the course title
        //   Include the course name along with the course description for the content
        //   Apply CRAP principals to make the page more appealing

        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = course?.name ?: "No name available",
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = course?.description ?: "No description available",
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionActivityPreview() {
    val course = testCourse()
    ComposeListLabSkeletonTheme {
        DetailContent(
            course = course,
            navigateBack = {})
    }
}
