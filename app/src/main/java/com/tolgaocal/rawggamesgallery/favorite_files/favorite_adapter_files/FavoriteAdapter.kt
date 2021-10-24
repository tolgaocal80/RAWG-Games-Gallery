package com.tolgaocal.rawggamesgallery.favorite_files.favorite_adapter_files

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
import com.tolgaocal.rawggamesgallery.favorite_files.FavoriteViewModel
import com.tolgaocal.rawggamesgallery.game_files.GameView
import com.tolgaocal.rawggamesgallery.SingletonGame

class FavoriteAdapter(private val viewModel: FavoriteViewModel, private val activity: AppCompatActivity) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private val inflater: LayoutInflater =
        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val gameItemList: List<GameItem>
        get() = viewModel.gameItemList.value ?: emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteViewHolder(inflater.inflate(R.layout.favorite_row, parent, false))

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val gameItem = gameItemList[position]

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

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val row: LinearLayout = view.findViewById(R.id.gameRecyclerRow)
    val gameImage: ImageView = view.findViewById(R.id.gameImage)
    val name: TextView = view.findViewById(R.id.nameText)
    val rating: TextView = view.findViewById(R.id.ratingText)
    val released: TextView = view.findViewById(R.id.releaseDateText)
}