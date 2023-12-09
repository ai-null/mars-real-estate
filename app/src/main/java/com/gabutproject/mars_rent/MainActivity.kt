package com.gabutproject.mars_rent

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.gabutproject.mars_rent.network.MarsApiFilter
import com.gabutproject.mars_rent.overview.OverviewFragmentDirections
import com.gabutproject.mars_rent.overview.OverviewViewModel

class MainActivity : AppCompatActivity() {

    private var hideOptions = false
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this)[OverviewViewModel::class.java]
    }

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            hideOptions = destination.id != R.id.overviewFragment
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        menu?.forEach {
            it.isVisible = !hideOptions
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ABOUT -> {
                navController.navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToAboutFragment()
                )
            }
            android.R.id.home -> {
                navController.navigateUp() || onSupportNavigateUp()
            }
            else -> {
                viewModel.updateListFilter(
                    when (item.itemId) {
                        R.id.SHOW_RENT -> MarsApiFilter.SHOW_RENT
                        R.id.SHOW_BUY -> MarsApiFilter.SHOW_BUY
                        else -> MarsApiFilter.SHOW_ALL
                    }
                )
            }
        }

        return true
    }
}
