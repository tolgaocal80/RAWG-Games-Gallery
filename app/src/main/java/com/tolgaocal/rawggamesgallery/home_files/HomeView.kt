package com.tolgaocal.rawggamesgallery.home_files

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.tolgaocal.rawggamesgallery.Commons.hideView
import com.tolgaocal.rawggamesgallery.Commons.showView
import com.tolgaocal.rawggamesgallery.R
import com.tolgaocal.rawggamesgallery.home_files.home_adapter_files.RecyclerViewAdapter
import com.tolgaocal.rawggamesgallery.home_files.home_adapter_files.ViewPagerAdapter
import kotlinx.android.synthetic.main.home.*

class HomeView : Fragment() {

    private val viewModel by lazy { HomeViewModel(HomeModel(activity as AppCompatActivity)) }

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter(
            viewModel,
            activity as AppCompatActivity
        )
    }

    private val recyclerviewAdapter by lazy {
        RecyclerViewAdapter(
            viewModel,
            activity as AppCompatActivity
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.home, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.apply {
            this.adapter = this@HomeView.viewPagerAdapter
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setCurrentIndicator(position)
                }
            })
        }

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = recyclerviewAdapter
        }

        viewModel.gameItemList.observe(viewLifecycleOwner, {
            // If search return nothing show game_files not found
            if (viewModel.searchedGameItemList.value?.size == 0) {
                gameNotFoundText.visibility = View.VISIBLE
            } else {
                gameNotFoundText.visibility = View.GONE
            }
            if (viewPager.isVisible) {
                gameListLayout.showView()
                progressBarLayout.hideView()
                setIndicator()
                setCurrentIndicator(0)
                viewPager.setCurrentItem(0, true)
                viewPagerAdapter.notifyDataSetChanged()
            }
            recyclerviewAdapter.notifyDataSetChanged()
        })
    }
    // Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    // Search menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchView = menu.findItem(R.id.item_homeSearch).actionView as SearchView
        searchView.setQuery("", true)
        searchView.queryHint = "Oyun aramak için yazınız"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            // if user searches in games, after 3 character input viewPager will be invisible
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! >= 3) {
                    viewPager.visibility = View.GONE
                    indicatorLayout.visibility = View.GONE
                    viewModel.searchGame(newText)
                } else {
                    viewPager.visibility = View.VISIBLE
                    indicatorLayout.visibility = View.VISIBLE
                    viewModel.loadData()
                    return false
                }
                return true
            }
        })
        super.onPrepareOptionsMenu(menu)
    }


    private fun setIndicator() {
        if (indicatorLayout.size > 0) return
        val indicators = arrayOfNulls<ImageView>(viewPagerAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(activity)
            indicators[i].apply {
                this?.setImageDrawable(activity?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.indicator_inactive
                    )
                })
                this?.layoutParams = layoutParams
            }
            indicatorLayout.addView(indicators[i])
        }
    }
    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorLayout.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorLayout[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(activity?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.indicator_active
                    )
                })
            } else {
                imageView.setImageDrawable(activity?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.indicator_inactive
                    )
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

}