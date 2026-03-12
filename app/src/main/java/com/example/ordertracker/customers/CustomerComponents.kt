package com.example.ordertracker.customers

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ordertracker.R


@Composable
fun SearchBar(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(14.dp))
            .border(
                1.dp, MaterialTheme.colorScheme.secondary.copy(0.12f), RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }) {
        Image(
            painter = painterResource(id = R.drawable.text_fields_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        OutlinedTextField(
            enabled = false,
            value = "",
            onValueChange = {},
            maxLines = 1,
            placeholder = {
                Text(
                    "Search by name, phone, or email...",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = MaterialTheme.typography.labelSmall.fontWeight,
                    textAlign = TextAlign.Center
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        )
    }
}


@Composable
fun NewCustomer(onClick: () -> Unit) {
    Button(
        onClick = onClick, colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ), contentPadding = PaddingValues(0.dp), shape = RoundedCornerShape(14.dp)
    ) {
        Box(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.save_button),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().align(Alignment.Center)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                Text(
                    text = "New Customer",
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontSize = 16.sp,
                    lineHeight = 21.sp
                )
            }

        }
    }
}


@Composable
fun CustomerItem(customerModel: CustomerModel, onCustomerClick: (Long) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onCustomerClick(customerModel.id) }) {
        Image(
            painter = painterResource(id = R.drawable.background_overlay),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(0.dp, Color.Transparent)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                        .fillMaxHeight()
                )
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            customerModel.customerName,
                            color = MaterialTheme.colorScheme.onTertiary,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            fontSize = 17.sp
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.call_svg),
                                contentDescription = "Call",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            TextField(
                                customerModel.contact
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.email_svg),
                                contentDescription = "Email",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            TextField(
                                customerModel.email,

                                )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.location_svg),
                                contentDescription = "Location",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            TextField(
                                customerModel.address,
                            )
                        }

                    }

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

@Composable
fun TextField(text: String) {
    Text(
        text,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 14.sp,
        fontWeight = MaterialTheme.typography.labelSmall.fontWeight
    )
}

@Composable
fun CustomerHome(
    onSearchClick: () -> Unit,
    customers: List<CustomerModel>,
    onCustomerClick: (Long) -> Unit,
    onNewCustomerClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            "Your registered clients",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 15.sp,
            fontWeight = MaterialTheme.typography.labelSmall.fontWeight

        )
        SearchBar(onClick = onSearchClick)

        NewCustomer(onClick = onNewCustomerClick)

        LazyColumn(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(customers, key = { it.id }) { customer ->
                CustomerItem(
                    customerModel = customer, onCustomerClick = onCustomerClick
                )
            }
        }
    }
}
