package com.example.ordertracker.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordertracker.R
import com.example.ordertracker.util.SearchType


@Composable
fun SearchTypeSelector(
    selectedTab: SearchType,
    onTabSelected: (SearchType) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.text_fields_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(28.dp))
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchType.entries.forEach { type ->
                val isSelected = type == selectedTab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(24.dp))
                        .clickable(enabled = enabled) { onTabSelected(type) },
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Image(
                            painter = painterResource(R.drawable.search_selector_svg),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            alpha = if (enabled) 1f else 0.5f,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                    Text(
                        text = type.label,
                        color = if (isSelected) Color.White
                        else MaterialTheme.colorScheme.secondary,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        fontSize = 14.sp,
                        letterSpacing = 0.2.sp,
                    )
                }
            }
        }
    }
}
