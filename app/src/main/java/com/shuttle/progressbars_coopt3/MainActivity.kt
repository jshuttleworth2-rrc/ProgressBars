package com.shuttle.progressbars_coopt3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shuttle.progressbars_coopt3.ui.theme.ProgressBars_CoOpt3Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgressBars_CoOpt3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(75.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // The basics
                        DeterminateIndicator()
                        IndeterminateIndicator()

                        // Styling
                        StyleExamples()

                        // Potential usage examples
                        TimerProgress()
                        ChecklistProgress()
                        FundingProgress()

                        /* These are only 3 examples, there are many more times where you may want a progress bar.
                         *
                         * More Possibilities:
                         * Showing how long is on a form or test/quiz
                         * Showing check out process in an online store
                         * Health bars in a game
                         */
                    }
                }
            }
        }
    }
}

/* Progress Bars:
 * A bar to indicate the progression of something, e.g. downloading content, uploading content
 * buffering, timers, lists.
 *
 * Two Types:
 * Determinate: Displays how much progress has been made
 * Indeterminate: Continually shows progress with no indicator of time consumed or time remaining
 *
 * Two Styles (They work the same):
 * Linear: Horizontal bar
 * Circular: A circle
 *
 * Sources:
 * https://developer.android.com/develop/ui/compose/components/progress
 */

@Composable
fun DeterminateIndicator() {
    var currentProgress by remember { mutableStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            text = "Determinate"
        )

        Button(onClick = {
            loading = true
            scope.launch {
                loadProgress { progress ->
                    currentProgress = progress
                }
                loading = false // Reset loading when the coroutine finishes
            }
        }, enabled = !loading) {
            Text("Start loading")
        }

        if (loading) {
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        if (loading) {
            CircularProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

/** Iterate the progress value */
suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}

@Composable
fun IndeterminateIndicator() {
    var loading by remember { mutableStateOf(false) }

    Text(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        text = "Indeterminate"
    )

    Button(onClick = { loading = true }, enabled = !loading) {
        Text("Start loading")
    }

    Button(onClick = { loading = false }, enabled = loading) {
        Text("End loading")
    }

    if (!loading) return

    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )

    CircularProgressIndicator(
        modifier = Modifier.size(100.dp)
    )
}

@Composable
fun StyleExamples() {
    var currentProgress by remember { mutableStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Create a coroutine scope
    val starIcon = Icons.Default.Star

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            text = "Styling Options"
        )

        Button(onClick = {
            loading = true
            scope.launch {
                loadProgress { progress ->
                    currentProgress = progress
                }
                loading = false // Reset loading when the coroutine finishes
            }
        }, enabled = !loading) {
            Text("Start loading")
        }

        if (loading) {
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                color = Color(0xFF4CAF50),
                trackColor = Color(0xFF90EE90),
                gapSize = 20.dp,
                strokeCap = StrokeCap.Butt,
            )
        }

        if (loading) {
            Box (
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { currentProgress },
                    modifier = Modifier.size(100.dp),
                    color = Color(0xFFFF0000),
                    trackColor = Color(0xFFFFB6C1),
                    strokeWidth = 20.dp,
                    gapSize = 10.dp
                )
                Text(
                    text = "${"%.0f".format(currentProgress * 100)}%",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun TimerProgress() {
    var timeInput by remember { mutableStateOf("") } // Get user input
    var currentProgress by remember { mutableStateOf(0f) }
    var timerRunning by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            text = "Timer"
        )

        // Input field to get user input
        OutlinedTextField(
            value = timeInput,
            onValueChange = { input ->
                if (input.all { it.isDigit() }) { // Ensure only digits are entered
                    timeInput = input
                }
            },
            label = { Text("Give me a time (in seconds)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Button(onClick = {
            val maxTime = timeInput.toIntOrNull()
            if (maxTime != null && maxTime in 1..60) {
                currentProgress = 0f
                timerRunning = true
                scope.launch {
                    startTimer(maxTime) { progress ->
                        currentProgress = progress
                    }
                    timerRunning = false
                }
            }
        }, enabled = !timerRunning) {
            Text("Start Timer")
        }

        LinearProgressIndicator(
            progress = { currentProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            color = Color(0xFF4CAF50),
            trackColor = Color(0xFF90EE90)
        )
    }
}

/** Iterate the progress value
 * No defaults, max time is given by user
 */
suspend fun startTimer(maxTime: Int, updateProgress: (Float) -> Unit) {
    /*
    for (i in 1..maxTime) {
        updateProgress(i.toFloat() / maxTime)
        delay(1000) // Ensure its actually 1 second
    }
    */
    val steps = maxTime * 10 // 10 updates per second to make smooth
    for (i in 1..steps) {
        updateProgress(i.toFloat() / steps) // Baby steps to make smooth
        delay(100)
    }
}

@Composable
fun ChecklistProgress() {
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    val checkItems = remember { mutableStateOf<Set<String>>(emptySet()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            text = "Checklist"
        )

        // Display list of items with radio buttons for tracking
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = checkItems.value.contains(item),
                    onClick = {
                        checkItems.value = if (checkItems.value.contains(item)) {
                            checkItems.value - item // Don't select
                        } else {
                            checkItems.value + item // Add item
                        }
                    }
                )

                Text(
                    text = item,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        // Calculate and display progress
        val progress = checkItems.value.size.toFloat() / items.size // Percentage
        Text("Percentage Done: ${"%.0f".format(progress * 100)}%", fontSize = 16.sp)

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            color = Color(0xFF2196F3),
            trackColor = Color(0xFFE0E0E0)
        )
    }
}

@Composable
fun FundingProgress() {
    var currentFunding by remember { mutableStateOf(0f) }
    var goalFunding = 1000f

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Give Me Funding",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "$0", fontSize = 16.sp)
            Text(
                text = "$${"%.0f".format(currentFunding)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "$1000", fontSize = 16.sp)
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Bar to track goal funding
            LinearProgressIndicator(
                progress = { (currentFunding/goalFunding).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                color = Color(0xFF4CAF50),
                trackColor = Color(0xFFE0E0E0)
            )

            // Track excess funding
            if (currentFunding > goalFunding) {
                LinearProgressIndicator(
                    progress = { ((currentFunding-goalFunding) / goalFunding).coerceAtMost(1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .graphicsLayer( scaleX = -1f ),
                    color = Color(0xFFFF9800),
                    trackColor = Color.Transparent // Hide track, not needed if we don't exceed
                )
            }
        }

        if (currentFunding > goalFunding) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Goal Accomplished! Excess Funding: $${"%.0f".format(currentFunding - goalFunding)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Get funding from users
        var donateInput by remember { mutableStateOf("") }
        OutlinedTextField(
            value = donateInput,
            onValueChange = { input ->
                if (input.all { it.isDigit() }) {
                    donateInput = input
                }
            },
            label = { Text("How Much You Got?") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Button(
            onClick = {
                val donation = donateInput.toFloatOrNull()
                if (donation != null) {
                    currentFunding += donation
                    donateInput = ""
                }
        },
            enabled = donateInput.isNotEmpty()
        ) {
            Text("Donate!")
        }
    }
}
