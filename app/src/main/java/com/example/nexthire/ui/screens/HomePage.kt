package com.example.nexthire.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nexthire.ui.theme.nextHireBackground

private enum class HomeTab {
    Jobs,
    Saved
}

private data class SampleJob(
    val title: String,
    val company: String,
    val location: String,
    val description: String
)

private val sampleJobs = listOf(
    SampleJob(
        title = "Junior Data Analyst",
        company = "Northbridge Data",
        location = "Middlesbrough",
        description = "Use Excel and SQL to check data, build reports and help teams understand their results."
    ),
    SampleJob(
        title = "Android Developer Intern",
        company = "Cedar Mobile",
        location = "Newcastle",
        description = "Build Android screens with Kotlin and Jetpack Compose, fix bugs and test new features."
    ),
    SampleJob(
        title = "Graduate Software Tester",
        company = "Harbour Software",
        location = "Remote",
        description = "Write test cases, check new features and report bugs with clear steps to reproduce them."
    )
)

@Composable
fun HomeScreen() {
    var searchText by remember { mutableStateOf("") }
    var selectedJob by remember { mutableStateOf<SampleJob?>(null) }
    var selectedTab by remember { mutableStateOf(HomeTab.Jobs) }
    val savedJobs = remember { mutableStateListOf<SampleJob>() }

    val focusManager = LocalFocusManager.current
    val jobToShow = selectedJob
    val showingJobs = selectedTab == HomeTab.Jobs

    BackHandler(enabled = jobToShow != null || !showingJobs) {
        if (jobToShow != null) {
            selectedJob = null
        } else {
            selectedTab = HomeTab.Jobs
        }
    }

    if (jobToShow != null) {
        JobDetailsScreen(
            job = jobToShow,
            isSaved = jobToShow in savedJobs,
            onToggleSaved = {
                if (jobToShow in savedJobs) {
                    savedJobs.remove(jobToShow)
                } else {
                    savedJobs.add(jobToShow)
                }
            },
            backLabel = if (showingJobs) "Back to jobs" else "Back to saved jobs",
            onBack = { selectedJob = null }
        )
        return
    }

    val query = searchText.trim()
    val matchingJobs = sampleJobs.filter { job ->
        query.isEmpty() ||
                job.title.contains(query, ignoreCase = true) ||
                job.location.contains(query, ignoreCase = true)
    }
    val visibleJobs = if (showingJobs) matchingJobs else savedJobs.toList()

    val resultsLabel = when {
        query.isEmpty() -> "${sampleJobs.size} sample jobs"
        matchingJobs.size == 1 -> "1 matching job"
        matchingJobs.isNotEmpty() -> "${matchingJobs.size} matching jobs"
        else -> null
    }

    val colours = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = showingJobs,
                    onClick = {
                        focusManager.clearFocus()
                        selectedTab = HomeTab.Jobs
                    },
                    icon = { Text("☰") },
                    label = { Text("Jobs") }
                )

                NavigationBarItem(
                    selected = !showingJobs,
                    onClick = {
                        focusManager.clearFocus()
                        selectedTab = HomeTab.Saved
                    },
                    icon = { Text("☆") },
                    label = { Text("Saved") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(nextHireBackground())
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .imePadding()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = if (showingJobs) "NextHire" else "Saved jobs",
                modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
                style = typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = colours.onBackground
            )

            if (showingJobs) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Search by title or location") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    trailingIcon = {
                        if (searchText.isNotEmpty()) {
                            TextButton(
                                onClick = {
                                    searchText = ""
                                    focusManager.clearFocus()
                                }
                            ) {
                                Text("Clear")
                            }
                        }
                    }
                )

                if (resultsLabel != null) {
                    Text(
                        text = resultsLabel,
                        modifier = Modifier.padding(top = 12.dp),
                        style = typography.bodyMedium,
                        color = colours.onSurfaceVariant
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (visibleJobs.isEmpty()) {
                    item {
                        Text(
                            text = if (showingJobs) {
                                "No matching jobs. Try a different title or location."
                            } else {
                                "No saved jobs yet. Open a job and tap Save job."
                            },
                            style = typography.bodyMedium,
                            color = colours.onSurfaceVariant
                        )
                    }
                }

                items(visibleJobs) { job ->
                    Card(
                        onClick = {
                            focusManager.clearFocus()
                            selectedJob = job
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colours.surface,
                            contentColor = colours.onSurface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = job.title,
                                style = typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = colours.primary
                            )
                            Text(job.company, style = typography.bodyMedium)
                            Text(
                                text = job.location,
                                style = typography.bodySmall,
                                color = colours.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun JobDetailsScreen(
    job: SampleJob,
    isSaved: Boolean,
    onToggleSaved: () -> Unit,
    backLabel: String,
    onBack: () -> Unit
) {
    val colours = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(nextHireBackground())
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(backLabel)
        }

        Text(
            text = job.title,
            style = typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = colours.onBackground
        )

        Text(
            text = "${job.company} · ${job.location}",
            style = typography.bodyLarge,
            color = colours.onSurfaceVariant
        )

        TextButton(onClick = onToggleSaved) {
            Text(if (isSaved) "Remove from saved jobs" else "Save job")
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colours.surface,
                contentColor = colours.onSurface
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("About the role", style = typography.titleMedium)
                Text(job.description, style = typography.bodyLarge)
            }
        }
    }
}