package com.tolgaocal.rawggamesgallery.home_files.home_adapter_files

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tolgaocal.rawggamesgallery.Commons.fetchImage
import com.tolgaocal.rawggamesgallery.R
import com.tolgaocal.rawggamesgallery.database_files.GameItem
import com.tolgaocal.rawggamesgallery.game_files.GameView
import com.tolgaocal.rawggamesgallery.home_files.HomeViewModel
import com.tolgaocal.rawggamesgallery.SingletonGame

class RecyclerViewAdapter(private val viewModel: HomeViewModel, private val activity: AppCompatActivity) : RecyclerView.Adapter<RecyclerViewViewHolder>() {

    private val inflater: LayoutInflater =
        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val gameItemList: List<GameItem>
        get() {
            viewModel.gameItemList.value?.apply {
                // if gameItemList is not empty(database is loaded) and filteredGameList is also not empty
                // so user searching in list return search result to view
                if (this.isNotEmpty() && this.size != filteredGameItemList.size) {
                    return filteredGameItemList
                }
                // else show first 3 game list item
                else if (this.size > 3) {
                    return this.subList(3, this.size)
                }
            }
            return emptyList()
        }

    private val filteredGameItemList: List<GameItem>
        get() = viewModel.searchedGameItemList.value ?: emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerViewViewHolder(inflater.inflate(R.layout.home_row, parent, false))

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val gameItem = gameItemList[position]

        // on tap to the game list item take user to game details
        holder.row.setOnClickListener {
            SingletonGame.currentGameItem = gameItem
            val intent = Intent(activity, GameView::class.java)
            activity.startActivity(intent)
        }
        holder.gameImage.apply { fetchImage(gameItem.image, this, activity) }
        holder.name.text = gameItem.name
        holder.rating.text = gameItem.rating
        holder.released.text = gameItem.releaseDate
    }

    override fun getItemCount() = gameItemList.size
}

// view holder for home list
class RecyclerViewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val row: LinearLayout = view.findViewById(R.id.gameRecyclerRow)
    val gameImage: ImageView = view.findViewById(R.id.gameImage)
    val name: TextView = view.findViewById(R.id.nameText)
    val rating: TextView = view.findViewById(R.id.ratingText)
    val released: TextView = view.findViewById(R.id.releaseDateText)
}