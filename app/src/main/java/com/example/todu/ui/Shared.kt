package com.example.todu.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todu.ui.theme.PriorityHigh
import com.example.todu.ui.theme.PriorityLow
import com.example.todu.ui.theme.PriorityMedium

@Composable
fun PrioritySelector(
    selectedPriority: Int,
    onPrioritySelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PriorityButton(
            text = "Low",
            isSelected = selectedPriority == 0,
            onClick = { onPrioritySelected(0) }
        )
        PriorityButton(
            text = "Medium",
            isSelected = selectedPriority == 1,
            onClick = { onPrioritySelected(1) }
        )
        PriorityButton(
            text = "High",
            isSelected = selectedPriority == 2,
            onClick = { onPrioritySelected(2) }
        )
    }
}

@Composable
fun PriorityButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                when (text) {
                    "Low" -> PriorityLow
                    "Medium" -> PriorityMedium
                    "High" -> PriorityHigh
                    else -> MaterialTheme.colorScheme.primary
                }
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Text(text)
    }
}