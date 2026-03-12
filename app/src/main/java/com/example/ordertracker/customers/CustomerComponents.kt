package com.example.ordertracker.customers

import android.Manifest
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ordertracker.R
import com.example.ordertracker.orders.SectionHeader
import com.example.ordertracker.viewmodels.CreateCustomerViewModel


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
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
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
                        .width(2.dp)
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
fun ChooseFromContact(
    onContactSelected: (name: String, phone: String) -> Unit
) {

    val context = LocalContext.current

    val contactPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact()
    ) { uri ->

        uri?.let {
            val resolver = context.contentResolver

            val cursor = resolver.query(
                it, null, null, null, null
            )

            cursor?.use { c ->

                if (c.moveToFirst()) {

                    val nameIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

                    val idIndex = c.getColumnIndex(ContactsContract.Contacts._ID)

                    val name = if (nameIndex != -1) c.getString(nameIndex) else ""
                    val contactId = if (idIndex != -1) c.getString(idIndex) else ""

                    if (contactId.isNotEmpty()) {
                        val phoneCursor = resolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            arrayOf(contactId),
                            null
                        )

                        phoneCursor?.use { p ->

                            if (p.moveToFirst()) {

                                val phoneIndex = p.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )

                                val phone = if (phoneIndex != -1) p.getString(phoneIndex) else ""

                                onContactSelected(name, phone)
                            }
                        }
                    }
                }
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            contactPicker.launch(null)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(100))
            .clickable {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context, Manifest.permission.READ_CONTACTS
                    ) -> {
                        contactPicker.launch(null)
                    }

                    else -> {
                        permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                    }
                }
            }) {
        Image(
            painter = painterResource(id = R.drawable.background_border),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 14.dp, end = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Image(
                painter = painterResource(id = R.drawable.contact_svg),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )

            Column {
                Text(
                    "CHOOSE FROM CONTACTS",
                    fontSize = 12.sp,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 1.68.sp
                )

                Text(
                    "Pull details from your phonebook",
                    fontSize = 11.sp,
                    fontWeight = MaterialTheme.typography.labelSmall.fontWeight,
                    color = MaterialTheme.colorScheme.secondary
                )
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

@Composable
fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String? = null,
    minLines: Int = 1
) {

    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.text_fields_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    width = 1.dp,
                    color = if (error != null) MaterialTheme.colorScheme.error
                    else if (isFocused) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.secondary.copy(0.12f),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {

            if (isFocused || value.isNotEmpty()) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 10.dp)
                )

            }


            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                shape = RoundedCornerShape(12.dp),

                placeholder = {
                    if (!isFocused && value.isEmpty()) {
                        Label(label)
                    }
                },

                minLines = minLines,
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onTertiary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused = it.isFocused },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
                )
            )
        }
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

@Composable
fun CustomerForm(
    onOrderCreated: () -> Unit, viewModel: CreateCustomerViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    if (state.showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismissSuccessDialog() },
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.secondary,
            textContentColor = MaterialTheme.colorScheme.secondary,
            title = { Text("Customer Added") },
            text = { Text("Customer added successfully.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onDismissSuccessDialog()
                        onOrderCreated()
                    }) {
                    Text("OK", color = MaterialTheme.colorScheme.secondary)
                }
            })
    }


    SectionHeader("Customer Details", MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(16.dp))
    InputTextField(
        value = state.customerName,
        onValueChange = { viewModel.onCustomerNameChange(it) },
        label = "Full Name",
        error = state.customerNameError

    )
    Spacer(modifier = Modifier.height(16.dp))
    InputTextField(
        value = state.contact,
        onValueChange = { viewModel.onContactChange(it) },
        label = "Contact Number",
        error = state.contactError
    )
    Spacer(modifier = Modifier.height(16.dp))
    InputTextField(
        value = state.email,
        onValueChange = { viewModel.onEmailChange(it) },
        label = "Email Address (Optional)",
        minLines = 3,
        error = state.emailError
    )
    Spacer(modifier = Modifier.height(36.dp))
    SectionHeader("LOCATION DATA", MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(modifier = Modifier.height(16.dp))
    InputTextField(
        value = state.address,
        onValueChange = { viewModel.onAddressChange(it) },
        label = "Delivery Address",
        error = state.addressError
    )
    Spacer(modifier = Modifier.height(36.dp))

    Button(
        onClick = { viewModel.onSaveCustomer() },
        enabled = state.isFormValid,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.save_button),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
            Text(
                text = "Save",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }


}

@Composable
fun Label(label: String) {
    Text(
        text = label,
        fontSize = 11.sp,
        letterSpacing = 0.12.sp,
        fontWeight = MaterialTheme.typography.labelSmall.fontWeight,
        textAlign = TextAlign.Center
    )
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
