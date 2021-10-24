package com.tolgaocal.rawggamesgallery.game_files

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.tolgaocal.rawggamesgallery.Commons.fetchImage
import com.tolgaocal.rawggamesgallery.R
import com.tolgaocal.rawggamesgallery.SingletonGame
import kotlinx.android.synthetic.main.game.*


// details screen
class GameView : AppCompatActivity() {

    private val viewModel by lazy { GameViewModel(GameModel(this)) }

    // take the chosen game_files item
    val gameItem = SingletonGame.currentGameItem!!


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        title = "Detaylar"

        // initialize attributes to views
        nameText.text = gameItem.name
        descriptionText.text = gameItem.description
        fetchImage(gameItem.image, gameImage, this)
        releaseDateText.text = gameItem.releaseDate
        metacriticText.text = gameItem.metacritic

        // set favorite icon
        favoriteImage.apply {
            favoriteImage.setImageResource(if (gameItem.favorite) R.drawable.ic_favorite_heart else R.drawable.ic_empty_heart)
            this.setOnClickListener {
                changeFavoriteStatus(it)
            }
        }

    }


    // change favorite status
    private fun changeFavoriteStatus(it : View) {
        gameItem.favorite = !gameItem.favorite
        favoriteImage.setImageResource(if (gameItem.favorite) R.drawable.ic_favorite_heart else R.drawable.ic_empty_heart)
        viewModel.updateFavorite(gameItem)
        Snackbar.make(it,if (gameItem.favorite) "Favorilere eklendi !" else "Favorilerden kaldırıldı !",Snackbar.LENGTH_SHORT)
            .setBackgroundTint(if (gameItem.favorite) Color.rgb(5,70,5) else Color.BLACK).show()
    }


}