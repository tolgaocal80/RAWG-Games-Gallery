package com.tolgaocal.rawggamesgallery.favorite_files

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tolgaocal.rawggamesgallery.Commons.hideView
import com.tolgaocal.rawggamesgallery.Commons.showView
import com.tolgaocal.rawggamesgallery.R
import com.tolgaocal.rawggamesgallery.favorite_files.favorite_adapter_files.FavoriteAdapter
import kotlinx.android.synthetic.main.favorite.*

class FavoriteView : Fragment() {

    private val viewModel by lazy { FavoriteViewModel(FavoriteModel(activity as AppCompatActivity)) }
    private val adapter by lazy { FavoriteAdapter(viewModel, activity as AppCompatActivity) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // if favorites are empty show no favorites text
        viewModel.gameItemList.observe(viewLifecycleOwner, {
            if (viewModel.gameItemList.value?.size == 0) {
                gameNotFoundText.visibility = View.VISIBLE
            } else {
                gameNotFoundText.visibility = View.GONE
            }
            gameListLayout.showView()
            progressBarLayout.hideView()
            adapter.notifyDataSetChanged()
        })

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = this@FavoriteView.adapter
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteGames()
    }

}