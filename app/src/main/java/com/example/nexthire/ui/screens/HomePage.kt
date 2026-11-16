package com.example.nexthire.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nexthire.ui.theme.nextHireBackground

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
    val colours = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(nextHireBackground())
            .safeDrawingPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "NextHire",
                    style = typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = colours.onBackground
                )
                Text(
                    text = "Sample jobs",
                    style = typography.titleMedium,
                    color = colours.onSurfaceVariant
                )
            }
        }

        items(sampleJobs) { job ->
            Card(
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
                    Text(
                        text = job.company,
                        style = typography.bodyMedium
                    )
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