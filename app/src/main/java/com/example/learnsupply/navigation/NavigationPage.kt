package com.example.learnsupply.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.learnsupply.ui.screen.changelog.view.ChangeLogScreen
import com.example.learnsupply.ui.screen.detailsupplier.view.DetailScreen
import com.example.learnsupply.ui.screen.supplier.view.SupplierScreen
import com.tagsamurai.tscomponents.snackbar.OnShowSnackBar

fun NavGraphBuilder.exampleGraph(
    navController: NavHostController,
    onShowSnackBar: OnShowSnackBar
) {
    composable(route = NavigationRoute.SupplierListScreen.route) {
        SupplierScreen(
            navController,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(
        route = NavigationRoute.DetailScreen.route,
        arguments = listOf(
            navArgument(ITEM_ID) {
                type = NavType.StringType
            },
        )
    ) { navBackStackEntry ->
        val itemId = navBackStackEntry.arguments?.getString(ITEM_ID).orEmpty()
        DetailScreen(
            itemId = itemId,
            navController,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable(route = NavigationRoute.ChangeLogScreen.route) {
        ChangeLogScreen(
            navController,
            onShowSnackBar = onShowSnackBar
        )
    }
}