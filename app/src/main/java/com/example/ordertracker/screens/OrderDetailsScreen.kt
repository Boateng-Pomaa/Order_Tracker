package com.example.ordertracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ordertracker.OrderTrackerDivider
import com.example.ordertracker.orders.OrderFormContent
import com.example.ordertracker.viewmodels.OrderDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderDetailsViewModel = hiltViewModel(), onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(modifier = modifier.fillMaxSize()) {
        OrderTrackerDivider()
        Spacer(modifier = Modifier.size(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .imePadding()
                .background(MaterialTheme.colorScheme.background)
        ) {
            OrderFormContent(state = state, onCustomerNameChange = {

                viewModel.updateCustomerName(it)
            }, onContactChange = {
                viewModel.updateContact(it)
            }, onItemChange = {
                viewModel.updateItem(it)
            }, onPriceChange = {
                viewModel.updatePrice(it)
            }, onUnitsChange = {
                viewModel.updateUnits(it)
            }, onDeliveryChange = {
                viewModel.updateDelivery(it)
            }, onStatusChange = {
                viewModel.updateStatus(it)
            })

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                }

                Button(
                    enabled = if (state.isEditing) state.hasChange else true, onClick = {
                        if (state.isEditing) {
                            viewModel.saveOrder()
                        }
                        viewModel.toggleEditing()
                    }, modifier = Modifier
                        .weight(1f)
                        .height(56.dp), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        if (state.isEditing) "Save Changes"
                        else "Enable Editing",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
