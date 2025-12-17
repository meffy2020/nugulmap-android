package com.example.neogulmap.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.neogulmap.domain.model.Zone
import com.example.neogulmap.presentation.viewmodel.HomeViewModel

import com.example.neogulmap.presentation.ui.components.KakaoMap

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val zones by viewModel.zones.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        KakaoMap(modifier = Modifier.fillMaxSize())
        
        if (zones.isEmpty()) {
            // Overlay loading or empty state
        } else {
            // Overlay list or markers (TODO: Add markers to map)
            // For now, let's keep the list visible on top or remove it to see the map clearly?
            // Let's use a BottomSheet scaffold later. For now, just the map.
            // Or maybe a floating list.
            
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 200.dp) // Just to see map behind?
            ) {
                items(zones) { zone ->
                    ZoneItem(zone)
                }
            }
        }
    }
}

@Composable
fun ZoneItem(zone: Zone) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Zone ID: ${zone.id}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Type: ${zone.type}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Region: ${zone.region}", style = MaterialTheme.typography.bodySmall)
            zone.description?.let {
                Text(text = it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}