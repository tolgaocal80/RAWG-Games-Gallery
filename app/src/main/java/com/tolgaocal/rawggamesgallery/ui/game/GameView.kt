package com.tolgaocal.rawggamesgallery.ui.game

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.tolgaocal.rawggamesgallery.Constants.fetchImage
import com.tolgaocal.rawggamesgallery.R
import com.tolgaocal.rawggamesgallery.SingletonGame
import kotlinx.android.synthetic.main.game.*

class GameView : AppCompatActivity() {

    private val viewModel by lazy { GameViewModel(GameModel(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        title = "Detaylar"

        if (SingletonGame.currentGameItem == null) {
            finish()
            return
        }

        val gameItem = SingletonGame.currentGameItem!!

        nameText.text = gameItem.name
        descriptionText.text = gameItem.description
        fetchImage(gameItem.image, gameImage, this)
        releaseDateText.text = gameItem.releaseDate
        metacriticText.text = gameItem.metacritic

        favoriteImage.apply {
            favoriteImage.setImageResource(if (gameItem.favorite) R.drawable.ic_favorite_heart else R.drawable.ic_empty_heart)

            this.setOnClickListener {
                gameItem.favorite = !gameItem.favorite
                favoriteImage.setImageResource(if (gameItem.favorite) R.drawable.ic_favorite_heart else R.drawable.ic_empty_heart)
                viewModel.updateFavorite(gameItem)
                Snackbar.make(this,if (gameItem.favorite) "Favorilere eklendi !" else "Favorilerden kaldırıldı !",Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(if (gameItem.favorite) Color.rgb(5,70,5) else Color.BLACK).show()
            }
        }


    }


}