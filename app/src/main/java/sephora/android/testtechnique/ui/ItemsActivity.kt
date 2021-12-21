package sephora.android.testtechnique.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import sephora.android.testtechnique.databinding.ActivityItemsBinding
import sephora.android.testtechnique.ui.adapters.ItemsAdapter
import sephora.android.testtechnique.ui.viewmodels.ItemsViewModel
import sephora.android.testtechnique.utils.AppResult


@AndroidEntryPoint
class ItemsActivity : AppCompatActivity() {
    private val viewModel: ItemsViewModel by viewModels()

    private lateinit var binding: ActivityItemsBinding
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val itemsAdapter = ItemsAdapter()
        binding.recyclerViewItems.adapter = itemsAdapter

        viewModel.items.observe(this, { result ->
            when (result) {
                is AppResult.Success -> {
                    //hide loader and display list
                    Log.e(TAG, "onCreate: ${result.successData}")
                    itemsAdapter.submitList(result.successData)
                    {
                        binding.recyclerViewItems.scrollToPosition(0)
                        hideOrShowProgressBar(false)
                    }
                }
                is AppResult.Error -> {
                    //hide loader and display msg error
                    hideOrShowProgressBar(false)
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
                AppResult.Loading -> {
                    //show loader
                    hideOrShowProgressBar(true)
                }
            }
        })

    }


    private fun hideOrShowProgressBar(isShow: Boolean) {
        binding.progressBarItems.isVisible = isShow
    }


    companion object {
        private const val TAG = "ItemsActivity"
    }
}
