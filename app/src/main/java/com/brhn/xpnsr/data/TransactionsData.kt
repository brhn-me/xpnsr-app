package com.brhn.xpnsr.data

import com.brhn.xpnsr.R
import com.brhn.xpnsr.models.Transaction
import com.brhn.xpnsr.models.TransactionType


fun getCategoryIcon(category: String): Int {
    return when (category) {
        // these samples are generated by AI
        "Groceries" -> R.drawable.baseline_shopping_cart_24
        "Salary", "Freelance", "Dividends", "Investments", "Bonus" -> R.drawable.baseline_attach_money_24
        "Dining", "Cafe" -> R.drawable.baseline_dining_24
        "Fitness", "Gym Membership", "Yoga Classes" -> R.drawable.baseline_fitness_center_24
        "Bills", "Electricity Bill", "Gas Bill" -> R.drawable.baseline_attach_money_24
        "Rent" -> R.drawable.baseline_add_home_24
        "Entertainment", "Movie Night", "Concert Tickets", "Theater Tickets" -> R.drawable.baseline_movie_24
        "Education", "Book Purchase", "Online Course" -> R.drawable.baseline_school_24
        "Transport", "Train Ticket", "Taxi Ride", "Uber Ride" -> R.drawable.baseline_directions_transit_24
        "Insurance", "Car Insurance", "Health Insurance" -> R.drawable.baseline_attach_money_24
        "Gifts", "Birthday Gift" -> R.drawable.baseline_cake_24
        "Healthcare", "Pharmacy", "Pet Vet Visit" -> R.drawable.baseline_medical_services_24
        "Home", "Gardening Supplies", "Garden Tools" -> R.drawable.baseline_local_florist_24
        "Pets", "Pet Food" -> R.drawable.baseline_pets_24
        "Electronics", "Laptop Repair" -> R.drawable.baseline_computer_24
        else -> R.drawable.baseline_do_not_disturb_24
    }
}


fun getSampleTransactions(): List<Transaction> {
    return listOf(
        Transaction(0, "Groceries", "2023-01-01", 50.0, TransactionType.EXPENSE, "Groceries"),
        Transaction(1, "Salary", "2023-01-05", 3000.0, TransactionType.EARNING, "Salary"),
        Transaction(2, "Cafe", "2023-01-06", 15.0, TransactionType.EXPENSE, "Dining"),
        Transaction(3, "Gym Membership", "2023-01-10", 40.0, TransactionType.EXPENSE, "Fitness"),
        Transaction(
            4,
            "Freelance Project",
            "2023-01-15",
            500.0,
            TransactionType.EARNING,
            "Freelance"
        ),
        Transaction(5, "Electricity Bill", "2023-01-17", 60.0, TransactionType.EXPENSE, "Bills"),
        Transaction(6, "Rent", "2023-02-01", 800.0, TransactionType.EXPENSE, "Rent"),
        Transaction(7, "Movie Night", "2023-02-03", 25.0, TransactionType.EXPENSE, "Entertainment"),
        Transaction(8, "Book Purchase", "2023-02-05", 20.0, TransactionType.EXPENSE, "Education"),
        Transaction(9, "Train Ticket", "2023-02-07", 30.0, TransactionType.EXPENSE, "Transport"),
        Transaction(10, "Car Insurance", "2023-02-10", 100.0, TransactionType.EXPENSE, "Insurance"),
        Transaction(11, "Dividends", "2023-02-12", 150.0, TransactionType.EARNING, "Investments"),
        Transaction(12, "Groceries", "2023-02-15", 55.0, TransactionType.EXPENSE, "Groceries"),
        Transaction(13, "Birthday Gift", "2023-02-20", 75.0, TransactionType.EXPENSE, "Gifts"),
        Transaction(
            14,
            "Concert Tickets",
            "2023-02-23",
            90.0,
            TransactionType.EXPENSE,
            "Entertainment"
        ),
        Transaction(15, "Pharmacy", "2023-02-25", 15.0, TransactionType.EXPENSE, "Healthcare"),
        Transaction(16, "Gardening Supplies", "2023-02-28", 35.0, TransactionType.EXPENSE, "Home"),
        Transaction(17, "Pet Food", "2023-03-02", 25.0, TransactionType.EXPENSE, "Pets"),
        Transaction(18, "Taxi Ride", "2023-03-05", 18.0, TransactionType.EXPENSE, "Transport"),
        Transaction(
            19,
            "Laptop Repair",
            "2023-03-10",
            120.0,
            TransactionType.EXPENSE,
            "Electronics"
        ),
        Transaction(20, "Bonus", "2023-03-15", 1000.0, TransactionType.EARNING, "Salary"),
        Transaction(21, "Yoga Classes", "2023-03-18", 70.0, TransactionType.EXPENSE, "Fitness"),
        Transaction(22, "Online Course", "2023-03-21", 200.0, TransactionType.EXPENSE, "Education"),
        Transaction(23, "Dinner Out", "2023-03-25", 60.0, TransactionType.EXPENSE, "Dining"),
        Transaction(
            24,
            "Health Insurance",
            "2023-03-28",
            130.0,
            TransactionType.EXPENSE,
            "Insurance"
        ),
        Transaction(25, "Garden Tools", "2023-04-01", 45.0, TransactionType.EXPENSE, "Home"),
        Transaction(26, "Gas Bill", "2023-04-04", 50.0, TransactionType.EXPENSE, "Bills"),
        Transaction(
            27,
            "Theater Tickets",
            "2023-04-07",
            80.0,
            TransactionType.EXPENSE,
            "Entertainment"
        ),
        Transaction(28, "Pet Vet Visit", "2023-04-10", 100.0, TransactionType.EXPENSE, "Pets"),
        Transaction(29, "Uber Ride", "2023-04-12", 22.0, TransactionType.EXPENSE, "Transport")
    )
}