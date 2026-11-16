package com.example.nexthire.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private data class SampleJob(
    val title: String,
    val company: String,
    val location: String
)

private val sampleJobs = listOf(
    SampleJob("Junior Data Analyst", "Northbridge Data", "Middlesbrough"),
    SampleJob("Android Developer Intern", "Cedar Mobile", "Newcastle"),
    SampleJob("Graduate Software Tester", "Harbour Software", "Remote")
)

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "NextHire",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Text(
                text = "Sample jobs",
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(sampleJobs) { job ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(job.title, style = MaterialTheme.typography.titleMedium)
                    Text(job.company, style = MaterialTheme.typography.bodyMedium)
                    Text(job.location, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}