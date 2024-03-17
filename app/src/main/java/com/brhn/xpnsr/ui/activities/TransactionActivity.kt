package com.brhn.xpnsr.ui.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.brhn.xpnsr.data.db.AppDatabase
import com.brhn.xpnsr.data.repository.CategoryRepository
import com.brhn.xpnsr.data.repository.TransactionRepository
import com.brhn.xpnsr.data.viewmodels.TransactionVM
import com.brhn.xpnsr.data.viewmodels.ViewModelFactory
import com.brhn.xpnsr.models.Category
import com.brhn.xpnsr.models.Transaction
import com.brhn.xpnsr.models.TransactionType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class TransactionActivity : BaseActivity() {
    private var transactionId: Long? = null
    private lateinit var viewModel: TransactionVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transactionRepository =
            TransactionRepository(AppDatabase.getDatabase(this).transactionDao())
        val viewModelFactory = ViewModelFactory(transactionRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[TransactionVM::class.java]
        transactionId = intent.getLongExtra("TRANSACTION_ID", -1L).takeIf { it != -1L }
        viewModel.loadTransaction(transactionId)
    }

    @Composable
    override fun ScreenContent(modifier: Modifier) {
        val transaction by viewModel.transaction.observeAsState()
        TransactionForm(transactionId, transaction,
            onSave = { finish() },
            onCancel = { finish() },
            onDelete = { finish() }
        )
    }

    override fun getAppBarTitle(): String {
        return if (transactionId != null) "Edit Transaction" else "Add Transaction"
    }

    @Composable
    fun TransactionForm(
        transactionId: Long?, transaction: Transaction?,
        onSave: () -> Unit,
        onCancel: () -> Unit,
        onDelete: () -> Unit
    ) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Date())

        val context = LocalContext.current

        val (name, setName) = remember { mutableStateOf(transaction?.name ?: "") }
        val (date, setDate) = remember { mutableStateOf(transaction?.date ?: today) }
        val (amount, setAmount) = remember { mutableStateOf(transaction?.amount?.toString() ?: "") }
        val (category, setCategory) = remember { mutableStateOf(transaction?.category ?: "") }
        val (selectedCategory, setSelectedCategory) = remember { mutableStateOf<Category?>(null) }
        val (type, setType) = remember {
            mutableStateOf(
                transaction?.type ?: TransactionType.EXPENSE
            )
        }

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

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = name,
                onValueChange = setName,
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
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
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { if (it.matches(Regex("^\\d*\\.?\\d*$"))) setAmount(it) },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 8.dp)
            ) {
                TransactionType.entries.forEach { transactionType ->
                    val isSelected = type == transactionType
                    OutlinedButton(
                        onClick = {
                            setType(transactionType)
                            setSelectedCategory(null)
                        }, modifier = Modifier.padding(end = 8.dp), border = BorderStroke(
                            1.dp,
                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.12f
                            )
                        ), colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(
                            text = transactionType.name, color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                val filteredCategories = CategoryRepository.getCategoriesByType(type)
                items(filteredCategories) { category ->
                    CategoryItem(category = category,
                        isSelected = selectedCategory == category,
                        onSelectCategory = { selected ->
                            setSelectedCategory(selected)
                            setCategory(selected.name)
                        })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Button(onClick = {
                        val parsedAmount = amount.toDoubleOrNull()
                        if (parsedAmount != null) {
                            val t = Transaction(
                                id = transactionId ?: 0,
                                name = name,
                                date = date,
                                amount = parsedAmount,
                                type = type,
                                category = category
                            )
                            viewModel.saveTransaction(t)
                            Toast.makeText(context, "Saved transaction", Toast.LENGTH_SHORT).show()
                            onSave()
                        } else {
                            Toast.makeText(context, "Invalid amount", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text("Save")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = {
                            // Handle cancel action
                            onCancel()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Cancel")
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                if (transactionId != null) {
                    Button(
                        onClick = {
                            viewModel.deleteTransaction(transactionId)
                            Toast.makeText(context, "Transaction deleted", Toast.LENGTH_SHORT)
                                .show()
                            onDelete()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                    ) {
                        Text("Delete", color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
            }
        }
    }


    @Composable
    fun CategoryItem(
        category: Category, isSelected: Boolean = false, onSelectCategory: (Category) -> Unit
    ) {
        Button(
            onClick = { onSelectCategory(category) },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = category.icon),
                    contentDescription = category.name,
                    tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = category.name)
            }
        }
    }
}