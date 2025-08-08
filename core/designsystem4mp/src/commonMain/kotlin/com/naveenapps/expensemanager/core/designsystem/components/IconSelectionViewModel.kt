package com.naveenapps.expensemanager.core.designsystem.components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// TODO Modify this with categories and icons in future
private val iconSelectionList = listOf(
    /*Res.drawable.*/ "account_balance",
    /*Res.drawable.*/ "account_balance_wallet",
    /*Res.drawable.*/ "agriculture",
    /*Res.drawable.*/ "apartment",
    /*Res.drawable.*/ "apparel",
    /*Res.drawable.*/ "car_rental",
    /*Res.drawable.*/ "car_repair",
    /*Res.drawable.*/ "credit_card",
    /*Res.drawable.*/ "devices",
    /*Res.drawable.*/ "dinner_dining",
    /*Res.drawable.*/ "directions_bike",
    /*Res.drawable.*/ "directions_boat",
    /*Res.drawable.*/ "directions_bus",
    /*Res.drawable.*/ "directions_car",
    /*Res.drawable.*/ "diversity",
    /*Res.drawable.*/ "electric_rickshaw",
    /*Res.drawable.*/ "electric_scooter",
    /*Res.drawable.*/ "emoji_food_beverage",
    /*Res.drawable.*/ "featured_seasonal_and_gifts",
    /*Res.drawable.*/ "fitness_center",
    /*Res.drawable.*/ "flight",
    /*Res.drawable.*/ "flights_and_hotels",
    /*Res.drawable.*/ "fluid_med",
    /*Res.drawable.*/ "hiking",
    /*Res.drawable.*/ "home_health",
    /*Res.drawable.*/ "interactive_space",
    /*Res.drawable.*/ "kayaking",
    /*Res.drawable.*/ "laptop_chromebook",
    /*Res.drawable.*/ "liquor",
    /*Res.drawable.*/ "local_shipping",
    /*Res.drawable.*/ "lunch_dining",
    /*Res.drawable.*/ "medication",
    /*Res.drawable.*/ "medication_liquid",
    /*Res.drawable.*/ "other_admission",
    /*Res.drawable.*/ "payments",
    /*Res.drawable.*/ "pool",
    /*Res.drawable.*/ "qr_code",
    /*Res.drawable.*/ "redeem",
    /*Res.drawable.*/ "restaurant",
    /*Res.drawable.*/ "savings",
    /*Res.drawable.*/ "shopping_cart",
    /*Res.drawable.*/ "snowmobile",
    /*Res.drawable.*/ "sports_esports",
    /*Res.drawable.*/ "sports_soccer",
    /*Res.drawable.*/ "sports_tennis",
    /*Res.drawable.*/ "store",
    /*Res.drawable.*/ "train",
    /*Res.drawable.*/ "travel",
    /*Res.drawable.*/ "wallet",
)

class IconSelectionViewModel : ViewModel() {

    private val _icons = MutableStateFlow(iconSelectionList)
    val icons = _icons.asStateFlow()
}