package com.brhn.xpnsr.ui.components

import android.app.DatePickerDialog
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.brhn.xpnsr.data.repository.CategoryRepository
import com.brhn.xpnsr.data.repository.TransactionRepository
import com.brhn.xpnsr.models.Category
import com.brhn.xpnsr.models.Transaction
import com.brhn.xpnsr.models.TransactionType
import com.brhn.xpnsr.ui.activities.ReportActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun TransactionForm(
    onBack: () -> Unit,
    transactionRepository: TransactionRepository,
    onTransactionAdded: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val today = sdf.format(Date())
    val (name, setName) = remember { mutableStateOf("") }
    val (date, setDate) = remember { mutableStateOf(today) }
    val (amount, setAmount) = remember { mutableStateOf("") }
    val (category, setCategory) = remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            setDate(sdf.format(calendar.time))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val (selectedCategory, setSelectedCategory) = remember { mutableStateOf<Category?>(null) }
    val (type, setType) = remember { mutableStateOf(TransactionType.EXPENSE) }

    Column {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Button(
                onClick = {

                },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("Transaction List")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    context.startActivity(Intent(context, ReportActivity::class.java))
                },
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("View Report")
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Add Transaction", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = name, onValueChange = setName, label = { Text("Name") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = date,
                onValueChange = {},
                label = { Text("Date") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = { if (it.matches(Regex("^\\d*\\.?\\d*$"))) setAmount(it) },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Transaction Type", style = MaterialTheme.typography.bodyLarge)
            TransactionType.values().forEach { transactionType ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = type == transactionType,
                        onClick = {
                            setType(transactionType)
                            setSelectedCategory(null) // Reset selected category on type change
                        }
                    )
                    Text(
                        text = transactionType.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Category", style = MaterialTheme.typography.bodyLarge)
            val filteredCategories = CategoryRepository.getCategoriesByType(type)

            // Using a horizontal scroll row for categories
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 8.dp),
            ) {
                filteredCategories.forEach { category ->
                    val isSelected = selectedCategory == category
                    Button(
                        onClick = {
                            setSelectedCategory(category)
                            setCategory(category.name)
                        },
                        modifier = Modifier.padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = category.icon),
                            contentDescription = category.name,
                            tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer // Adjust icon tint based on selection
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = category.name)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val parsedAmount = amount.toDoubleOrNull()
                if (parsedAmount != null) {
                    val transaction = Transaction(
                        name = name,
                        date = date,
                        amount = parsedAmount,
                        type = type,
                        category = category
                    )
                    coroutineScope.launch {
                        transactionRepository.insert(transaction)
                        Toast.makeText(context, "Transaction added", Toast.LENGTH_SHORT).show()
                        onTransactionAdded()
                    }
                } else {
                    Toast.makeText(context, "Invalid amount", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Add Transaction")
            }
        }
    }
}