package com.example.ordertracker.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordertracker.R
import com.example.ordertracker.orders.OrderModel
import com.example.ordertracker.orders.Status
import com.example.ordertracker.uistate.OrderUiModel
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

@Composable
fun OrderSearch(order: OrderUiModel, onOrderClick: (Long) -> Unit) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.background_overlay),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onOrderClick(order.id) }),
            border = BorderStroke(0.dp, Color.Transparent)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 12.dp, end = 12.dp, bottom = 12.dp, top = 11.dp
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = order.customerName,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier.weight(1f, fill = false),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "GHC ${order.price}",
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.tertiary,
                            textAlign = TextAlign.End
                        )

                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            modifier = Modifier.weight(1f, fill = false),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.wrapContentSize().clip(RoundedCornerShape(6.dp))) {
                                Image(
                                    painter = painterResource(R.drawable.item_background),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.matchParentSize()
                                )
                                Text(
                                    text = order.item,
                                    fontSize = 11.sp,
                                    letterSpacing = 0.5.sp,
                                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Box(modifier = Modifier.wrapContentSize().clip(RoundedCornerShape(6.dp))) {
                                Image(
                                    painter = painterResource(R.drawable.text_fields_background),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.matchParentSize()
                                )
                                Text(
                                    text = order.delivery.label,
                                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                    fontSize = 11.sp,
                                    letterSpacing = 0.5.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            val statusColor = when (order.status) {
                                Status.PENDING -> MaterialTheme.colorScheme.tertiary
                                Status.DELIVERED -> MaterialTheme.colorScheme.onSurfaceVariant
                                Status.PICKED -> MaterialTheme.colorScheme.onSurfaceVariant
                            }

                            Row(
                                modifier = Modifier
                                    .background(
                                        color = statusColor, shape = RoundedCornerShape(50)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                if (order.status == Status.DELIVERED || order.status == Status.PICKED) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.done_svg),
                                        contentDescription = "Done",
                                        tint = MaterialTheme.colorScheme.background,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Text(
                                    text = order.status.label,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.background,
                                )
                            }

                            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                                Box(modifier = Modifier.wrapContentSize()) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "right arrow",
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
