package com.example.ordertracker.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ordertracker.R
import com.example.ordertracker.orders.OrderFormContent
import com.example.ordertracker.viewmodels.OrderDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    viewModel: OrderDetailsViewModel = hiltViewModel(), onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            }

            Box(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.save_button),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize()
                )

                Button(
                    enabled = if (state.isEditing) state.hasChange else true,
                    onClick = {
                        if (state.isEditing) {
                            viewModel.saveOrder()
                        }
                        viewModel.toggleEditing()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),

                    shape = RoundedCornerShape(8.dp),
//                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary)
                ) {
                    Text(
                        if (state.isEditing) "Save Changes"
                        else "Enable Editing",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }
    }

}
