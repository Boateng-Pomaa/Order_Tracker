package com.example.ordertracker.orders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordertracker.R
import com.example.ordertracker.uistate.OrderDetailsUiState
import com.example.ordertracker.uistate.OrderUiState

@Composable
fun OrderItems(
    order: OrderModel, onDeleteOrderClick: () -> Unit, onOrderClick: (Long) -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onOrderClick(order.id) }),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary)

    ) {
        Column(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp, top = 11.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = order.customerName,
                    fontSize = 22.5.sp,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Ghc${order.price}",
                    fontSize = 22.5.sp,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f)
                )

            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.item,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(8.dp)

                )

                Text(
                    text = "x${order.units}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)

                )


            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = order.delivery.label,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.onBackground,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    Text(
                        text = order.status.label,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)

                    )


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


@Composable
fun PendingOrders(state: OrderUiState.Success) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${state.pendingCount}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onTertiary,
                )

                Text(
                    text = "Pending Delivery",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 12.sp,

                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier

                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(12.dp),
                        )
                        .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 4.dp)
                        .align(Alignment.CenterVertically)


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
            Text(
                text = "Pending Orders",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 14.dp, top = 16.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        item {
            PendingOrders(state = OrderUiState.Success(orders))
        }
        item {
            Text(
                text = "All Orders",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 6.dp, top = 16.dp),
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
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.onPrimary),
            isError = error != null,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            enabled = enabled,

            )

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
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
    )
}

@Composable
fun DeliverySelector(
    selected: Delivery, onSelected: (Delivery) -> Unit, enabled: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Delivery.entries.forEach { delivery ->
            FilterChip(
                selected = selected == delivery,
                onClick = { onSelected(delivery) },
                label = { Text(delivery.name.replace("_", " ")) },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp),
                enabled = enabled,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary

                )
            )
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
            expanded = expanded, onExpandedChange = { expanded = if (enabled) !expanded else false }) {
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
                    focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                modifier = Modifier.background(color = MaterialTheme.colorScheme.onPrimary),
                onDismissRequest = { expanded = false }) {
                Status.entries.forEach { status ->
                    DropdownMenuItem(text = { Text(status.name) }, onClick = {
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

    SectionHeader("Customer Details")

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

    SectionHeader("Order Details")

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

    SectionHeader("Delivery Options")

    DeliverySelector(
        selected = order.delivery,
        onSelected = onDeliveryChange,
        enabled = state.isEditing
    )

    SectionHeader("Delivery Status")

    StatusDropdown(
        selected = order.status,
        onSelected = onStatusChange,
        enabled = state.isEditing
    )
}
