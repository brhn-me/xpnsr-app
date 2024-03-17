package com.brhn.xpnsr.data.repository

import com.brhn.xpnsr.models.Category
import com.brhn.xpnsr.models.TransactionType
import com.brhn.xpnsr.R

object CategoryRepository { // or object CategoryUtil

    private val categories = listOf(
        // Expenses
        Category("Groceries", R.drawable.baseline_shopping_cart_24, TransactionType.EXPENSE),
        Category("Dining", R.drawable.baseline_dining_24, TransactionType.EXPENSE),
        Category("Cafe", R.drawable.baseline_dining_24, TransactionType.EXPENSE),
        Category("Fitness", R.drawable.baseline_fitness_center_24, TransactionType.EXPENSE),
        Category("Gym Membership", R.drawable.baseline_fitness_center_24, TransactionType.EXPENSE),
        Category("Yoga Classes", R.drawable.baseline_fitness_center_24, TransactionType.EXPENSE),
        Category("Bills", R.drawable.baseline_attach_money_24, TransactionType.EXPENSE),
        Category("Electricity Bill", R.drawable.baseline_attach_money_24, TransactionType.EXPENSE),
        Category("Gas Bill", R.drawable.baseline_attach_money_24, TransactionType.EXPENSE),
        Category("Rent", R.drawable.baseline_add_home_24, TransactionType.EXPENSE),
        Category("Entertainment", R.drawable.baseline_movie_24, TransactionType.EXPENSE),
        Category("Movie Night", R.drawable.baseline_movie_24, TransactionType.EXPENSE),
        Category("Concert Tickets", R.drawable.baseline_movie_24, TransactionType.EXPENSE),
        Category("Theater Tickets", R.drawable.baseline_movie_24, TransactionType.EXPENSE),
        Category("Education", R.drawable.baseline_school_24, TransactionType.EXPENSE),
        Category("Book Purchase", R.drawable.baseline_school_24, TransactionType.EXPENSE),
        Category("Online Course", R.drawable.baseline_school_24, TransactionType.EXPENSE),
        Category("Transport", R.drawable.baseline_directions_transit_24, TransactionType.EXPENSE),
        Category(
            "Train Ticket",
            R.drawable.baseline_directions_transit_24,
            TransactionType.EXPENSE
        ),
        Category("Taxi Ride", R.drawable.baseline_directions_transit_24, TransactionType.EXPENSE),
        Category("Uber Ride", R.drawable.baseline_directions_transit_24, TransactionType.EXPENSE),
        Category("Healthcare", R.drawable.baseline_medical_services_24, TransactionType.EXPENSE),
        Category("Pharmacy", R.drawable.baseline_medical_services_24, TransactionType.EXPENSE),
        Category("Pet Vet Visit", R.drawable.baseline_medical_services_24, TransactionType.EXPENSE),
        Category("Home", R.drawable.baseline_local_florist_24, TransactionType.EXPENSE),
        Category(
            "Gardening Supplies",
            R.drawable.baseline_local_florist_24,
            TransactionType.EXPENSE
        ),
        Category("Garden Tools", R.drawable.baseline_local_florist_24, TransactionType.EXPENSE),
        Category("Pets", R.drawable.baseline_pets_24, TransactionType.EXPENSE),
        Category("Pet Food", R.drawable.baseline_pets_24, TransactionType.EXPENSE),
        Category("Electronics", R.drawable.baseline_computer_24, TransactionType.EXPENSE),
        Category("Laptop Repair", R.drawable.baseline_computer_24, TransactionType.EXPENSE),
        // Miscellaneous / Other
        Category(
            "Gifts",
            R.drawable.baseline_cake_24,
            TransactionType.EXPENSE
        ),
        Category("Birthday Gift", R.drawable.baseline_cake_24, TransactionType.EXPENSE),

        // Earnings
        Category("Salary", R.drawable.baseline_attach_money_24, TransactionType.EARNING),
        Category("Freelance", R.drawable.baseline_attach_money_24, TransactionType.EARNING),
        Category("Dividends", R.drawable.baseline_attach_money_24, TransactionType.EARNING),
        Category("Investments", R.drawable.baseline_attach_money_24, TransactionType.EARNING),
        Category("Bonus", R.drawable.baseline_attach_money_24, TransactionType.EARNING),
    )


    fun getCategoriesByType(type: TransactionType): List<Category> {
        return categories.filter { it.type == type }
    }

    fun getCategoryIcon(categoryName: String): Int {
        return categories.find { it.name == categoryName }?.icon
            ?: R.drawable.money
    }
}
