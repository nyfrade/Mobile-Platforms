package com.example.trabalhoapi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DogListView(
    navController: NavController
) {
    val viewModel: DogListViewModel = viewModel()
    val uiState by viewModel.uiState

    LazyColumn {
        itemsIndexed(
            items = uiState.dogs
        ) { _, item ->
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        navController.navigate("dog/${item.breedName}")
                    }
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = item.breedName
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }
}