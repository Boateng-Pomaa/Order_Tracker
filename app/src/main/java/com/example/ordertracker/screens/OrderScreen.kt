package com.example.ordertracker.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ordertracker.R
import com.example.ordertracker.orders.Home
import com.example.ordertracker.uistate.OrderUiState
import com.example.ordertracker.viewmodels.OrdersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackerScreen(
    modifier: Modifier = Modifier,
    viewModel: OrdersViewModel = hiltViewModel(),
    onOrderClick: (Long) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val orderToDelete by viewModel.orderToDelete.collectAsState()

    if (orderToDelete != null) {
        Dialog(onDismissRequest = { viewModel.cancelDelete() }) {
            Box(
                modifier = Modifier
                    .width(304.dp)
                    .height(252.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .paint(
                        painter = painterResource(id = R.drawable.dialog_svg),
                        contentScale = ContentScale.FillBounds
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.error_svg),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Delete Order",
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        fontSize = 24.sp,
                        lineHeight = 26.4.sp,
                        letterSpacing = (-0.76).sp,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.onTertiary,
                    )
                    Spacer(modifier = Modifier.height(10.39.dp))

                    Text(
                        text = "Are you sure you want to delete this order?",
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        fontSize = 15.sp,
                        lineHeight = 22.5.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Left
                    )

                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(44.dp)
                                    .width(92.dp)
                                    .clip(RoundedCornerShape(14.dp))
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.new_buttons_svg),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.matchParentSize()
                                )
                                Button(
                                    onClick = { viewModel.cancelDelete() },
                                    modifier = Modifier.fillMaxSize(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                    )
                                ) {
                                    Text("Cancel", color = MaterialTheme.colorScheme.secondary)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .height(44.dp)
                                    .width(92.dp)
                                    .clip(RoundedCornerShape(14.dp))
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.delete_dialog_svg),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.matchParentSize()
                                )
                                Button(
                                    onClick = { viewModel.confirmDelete() },
                                    modifier = Modifier.fillMaxSize(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                    )
                                ) {
                                    Text("Delete", color = MaterialTheme.colorScheme.onTertiary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .blur(if (orderToDelete != null) 5.dp else 0.dp)
    ) {
        when (uiState) {
            is OrderUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is OrderUiState.Success -> {
                Home(
                    orders = (uiState as OrderUiState.Success).orders,
                    onDeleteOrderClick = { order ->
                        viewModel.requestDelete(order)
                    },
                    onOrderClick = { orderId ->
                        onOrderClick(orderId)
                    })
            }

            is OrderUiState.Error -> Text("Error")
        }
    }
}
