package com.digiart

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.digiart.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isFilterExpanded = false
    private var filterPopup: PopupWindow? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

// Hide ActionBar
        // Set status bar color to black
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.black, theme)
        }

        // Set navigation bar color to white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = resources.getColor(R.color.white, theme)
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Set up the BottomNavigationView with NavController
        binding.navView.setupWithNavController(navController)


    }

    private fun showFilterPopup(view: View) {
        val popupView = layoutInflater.inflate(R.layout.filter_options, null)
        filterPopup = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // Set up logic for applying filters within the popup
        val applyFilterButton: Button = popupView.findViewById(R.id.btnApplyFilter)
        applyFilterButton.setOnClickListener {
            // Handle apply filter logic here

            // Dismiss the popup after applying the filter
            filterPopup?.dismiss()
        }

        // Set up logic for dismissing the popup when clicked outside
        filterPopup?.isOutsideTouchable = true
        filterPopup?.isFocusable = true

        // Show the popup below the filter button
        filterPopup?.showAsDropDown(view)
    }
}