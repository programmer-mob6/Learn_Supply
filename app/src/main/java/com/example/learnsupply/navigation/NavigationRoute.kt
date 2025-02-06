package com.example.learnsupply.navigation

const val ITEM_ID = "itemId"

sealed class NavigationRoute(val route: String) {
    data object SupplierListScreen : NavigationRoute(
        route = "supplier_list_screen"
    )

    data object DetailScreen : NavigationRoute(
        route = "detail_screen/{$ITEM_ID}"
    ) {
        fun navigate(itemId: String): String {
            return "detail_screen/$itemId"
        }
    }

    data object ChangeLogScreen : NavigationRoute(
        route = "change_log_screen"
    )
}