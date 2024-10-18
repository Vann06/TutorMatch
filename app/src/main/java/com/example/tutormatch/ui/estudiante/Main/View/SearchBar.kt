package com.example.tutormatch.ui.estudiante.Main.View

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = modifier
) {
    TextField(
        value = query,
        onValueChange = { newText -> onQueryChanged(newText) },
        placeholder = { Text(text = "Buscar...") },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon") },
        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.surface)
    )
}


 */