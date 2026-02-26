package com.example.ordertracker.orders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordertracker.R
import com.example.ordertracker.uistate.OrderDetailsUiState
import com.example.ordertracker.uistate.OrderUiState

@Composable
fun OrderItems(
    order: OrderModel, onDeleteOrderClick: () -> Unit, onOrderClick: (Long) -> Unit = {}
) {

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
                val accentColor = when (order.status) {
                    Status.PENDING -> MaterialTheme.colorScheme.tertiary
                    Status.DELIVERED -> MaterialTheme.colorScheme.onSurfaceVariant
                    Status.PICKED -> MaterialTheme.colorScheme.onSurfaceVariant
                }

                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .background(accentColor)
                        .fillMaxHeight()
                )
                Column(
                    modifier = Modifier.padding(
                        start = 12.dp, end = 12.dp, bottom = 12.dp, top = 11.dp
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        Text(
                            text = order.customerName,
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "GHC ${order.price}",
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.weight(1f)
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))


                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .paint(
                                painterResource(R.drawable.item_background),
                                contentScale = ContentScale.FillBounds,
                            )
                    ) {

                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = order.item,
                                fontSize = 13.sp,
                                letterSpacing = 0.2.sp,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)

                            )

                            Text(
                                text = "x${order.units}",
                                letterSpacing = 0.2.sp,
                                fontSize = 13.sp,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        shape = RoundedCornerShape(60)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)

                            )


                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(1.dp),
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                text = order.delivery.label,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.background,
                                        shape = RoundedCornerShape(50)
                                    )
                                    .border(
                                        shape = RoundedCornerShape(50),
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    .padding(horizontal = 12.dp, vertical = 4.dp)

                            )


                            val statusColor = when (order.status) {
                                Status.PENDING -> MaterialTheme.colorScheme.tertiary
                                Status.DELIVERED -> MaterialTheme.colorScheme.onSurfaceVariant
                                Status.PICKED -> MaterialTheme.colorScheme.onSurfaceVariant
                            }

                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
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
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.background,
                                )
                            }

                        }

                        IconButton(
                            onClick = { onDeleteOrderClick() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.img_delete),
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Delete order",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }

                    }
                }

            }
        }
    }
}


@Composable
fun PendingOrders(state: OrderUiState.Success) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box {

            Image(
                painter = painterResource(R.drawable.background_border),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {

                Text(
                    text = "Pending Orders",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .paint(painterResource(R.drawable.overlay_border))
                        .padding(horizontal = 12.dp, vertical = 4.dp)

                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${state.pendingCount}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onTertiary,
                )


            }
        }
    }
}


@Composable
fun Home(
    orders: List<OrderModel>,
    onDeleteOrderClick: (OrderModel) -> Unit,
    onOrderClick: (Long) -> Unit = {}
) {

    LazyColumn(
        contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            PendingOrders(state = OrderUiState.Success(orders))
        }
        item {
            Text(
                text = "All Orders",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 14.sp,
                letterSpacing = 0.5.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        items(
            items = orders, key = { it.id }) { order ->
            OrderItems(order = order, onDeleteOrderClick = {
                onDeleteOrderClick(order)
            }, onOrderClick = { onOrderClick(order.id) }

            )
        }
    }
}


@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    error: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var isFocused by remember { mutableStateOf(false) }

    val stateColor = when {
        isFocused -> MaterialTheme.colorScheme.onSurfaceVariant
        else -> MaterialTheme.colorScheme.secondary
    }
    Column(modifier = modifier) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .paint(
                    painter = painterResource(id = R.drawable.background_overlay),
                    contentScale = ContentScale.FillBounds
                )
                .border(
                    width = 2.dp, color = if (error != null) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else if (isFocused) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        Color.Transparent
                    }, shape = RoundedCornerShape(12.dp)
                )
        ) {

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = {
                    Text(
                        label,
                        fontSize = 15.sp,
                        color = stateColor,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused = it.isFocused },
                isError = error != null,
                keyboardOptions = keyboardOptions,
                singleLine = singleLine,
                enabled = enabled,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    errorTextColor = MaterialTheme.colorScheme.onTertiary,
                    cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary
                )

            )
        }

        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun SectionHeader(title: String, accentColor: Color) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .drawBehind {
                val strokeWidth = 4.dp.toPx()
                drawLine(
                    color = accentColor,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = strokeWidth
                )
            }
            .padding(start = 12.dp))
}

@Composable
fun DeliverySelector(
    selected: Delivery, onSelected: (Delivery) -> Unit, enabled: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Delivery.entries.forEach { delivery ->

            val isSelected = selected == delivery
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(30.dp)

            ) {
                if (isSelected) {
                    Image(
                        painter = painterResource(R.drawable.save_button),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize()
                    )
                }

                FilterChip(
                    selected = selected == delivery,
                    onClick = { onSelected(delivery) },
                    label = {
                        Text(
                            delivery.name.replace("_", " "),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = Color.Transparent,
                        selectedContainerColor = Color.Transparent,
                        labelColor = MaterialTheme.colorScheme.secondary,
                        selectedLabelColor = MaterialTheme.colorScheme.background,
                        disabledLabelColor = MaterialTheme.colorScheme.onSecondary,

                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusDropdown(
    selected: Status, onSelected: (Status) -> Unit, enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = if (enabled) !expanded else false }) {
            OutlinedTextField(
                value = selected.name,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = enabled,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    focusedTextColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary,
                    disabledBorderColor = MaterialTheme.colorScheme.onSecondary
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                onDismissRequest = { expanded = false }) {
                Status.entries.forEach { status ->
                    DropdownMenuItem(text = {
                        Text(
                            status.name, color = MaterialTheme.colorScheme.tertiary
                        )
                    }, onClick = {
                        onSelected(status)
                        expanded = false
                    })
                }
            }
        }
    }
}


@Composable
fun OrderFormContent(
    state: OrderDetailsUiState,
    onCustomerNameChange: (String) -> Unit,
    onContactChange: (String) -> Unit,
    onItemChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onUnitsChange: (String) -> Unit,
    onDeliveryChange: (Delivery) -> Unit,
    onStatusChange: (Status) -> Unit
) {

    val order = state.order ?: return

    SectionHeader("Customer Details", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    AppTextField(
        value = order.customerName,
        onValueChange = onCustomerNameChange,
        label = "Full Name",
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        enabled = state.isEditing
    )

    AppTextField(
        value = order.contact,
        onValueChange = onContactChange,
        label = "Contact Number",
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        enabled = state.isEditing
    )

    SectionHeader("Order Details", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    AppTextField(
        value = order.item,
        onValueChange = onItemChange,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        label = "Item Description",
        enabled = state.isEditing
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AppTextField(
            value = order.price.toString(),
            onValueChange = onPriceChange,
            label = "Price (Ghc)",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            enabled = state.isEditing
        )

        AppTextField(
            value = order.units.toString(),
            onValueChange = onUnitsChange,
            label = "Units",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 5.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            enabled = state.isEditing
        )
    }

    SectionHeader("Delivery Options", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    DeliverySelector(
        selected = order.delivery, onSelected = onDeliveryChange, enabled = state.isEditing
    )

    SectionHeader("Delivery Status", accentColor = MaterialTheme.colorScheme.onSurfaceVariant)

    StatusDropdown(
        selected = order.status, onSelected = onStatusChange, enabled = state.isEditing
    )
}
