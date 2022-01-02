package com.example.a1valettest.utils.extensions

import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import com.example.a1valettest.EspressoIdlingResource
import com.example.a1valettest.R
import com.google.android.material.navigation.NavigationView

fun NavigationView.customSetupWithNavController(
    drawerLayout: DrawerLayout,
    navController: NavController
) {
    this.setNavigationItemSelectedListener {
        navigationItemClick(
            item = it,
            resId = it.itemId,
            navView = this,
            drawerLayout = drawerLayout,
            navController = navController
        )
    }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        this.setCheckedItem(destination.id)
    }
}


private fun navigationItemClick(
    item: MenuItem,
    @IdRes resId: Int,
    navView: NavigationView,
    drawerLayout: DrawerLayout,
    navController: NavController,
): Boolean = if (item.isChecked) {
    closeDrawer(drawerLayout = drawerLayout)
    true
} else {
    navigate(resId = resId, navController = navController, drawerLayout = drawerLayout)
    navView.setCheckedItem(resId)
    true
}


private fun navigate(@IdRes resId: Int, navController: NavController, drawerLayout: DrawerLayout) {
    if (resId == R.id.homeFragment) {
        navController.navigate(
            R.id.action_to_homeFragment
        )
    } else {
        navController.navigate(resId)
    }
    EspressoIdlingResource.increment()
    Handler(Looper.getMainLooper()).postDelayed({
        closeDrawer(drawerLayout = drawerLayout)
        EspressoIdlingResource.decrement()
    }, 150)
}

private fun closeDrawer(drawerLayout: DrawerLayout) =
    drawerLayout.closeDrawer(GravityCompat.START, true)
