package com.tolgaocal.rawggamesgallery

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tolgaocal.rawggamesgallery.database_files.GameDatabase
import com.tolgaocal.rawggamesgallery.database_files.GameItem
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val isFirstLaunch = "isFirstLaunch"
    private val sharedPref = "sharedPref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView: BottomNavigationView = findViewById(R.id.nav_view)
        val hostFragment = supportFragmentManager.
        findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navigationController = hostFragment.navController

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_favorite))
        setupActionBarWithNavController(navigationController, appBarConfiguration)
        navigationView.setupWithNavController(navigationController)

        val sharedPreferences = getSharedPreferences(sharedPref, 0)

        // Call loadDatabase method to show game list
        loadDatabase()

        // After first launch progress animation will be hidden
        if (!sharedPreferences.getBoolean(isFirstLaunch, true)) {
            progressBarLayout.hideAnimation()
            nav_host_fragment.showAnimation()
        }

    }



    // When user switches between apps reload list
    override fun onResume() {
        super.onResume()
        loadDatabase()
    }


    private fun loadDatabase() {

        val databaseDao = GameDatabase.getDatabase(this).dbDao()
        val client = OkHttpClient()
        var request = Request.Builder().url( "https://api.rawg.io/api/games?key=${Constants.API_KEY}").build()

        Thread{
            try {
                // fetch game list as JSON list
                client.newCall(request).execute().use { responseGameList ->
                    if (!responseGameList.isSuccessful) throw IOException("Unexpected code $responseGameList")

                    val jsonGameListObject = JSONObject(responseGameList.body!!.string())
                    val resultsArray = jsonGameListObject.getJSONArray("results")

                    // for every item in game list get attributes, add to database
                    for (i in 0 until resultsArray.length()) {

                        val jsonGameObject = JSONObject(resultsArray[i].toString())
                        val id = jsonGameObject.getString("id")

                        if (databaseDao.getGameItemById(id) != null) {
                            continue
                        }

                        // fetch game details
                        request = Request.Builder()
                            .url("https://api.rawg.io/api/games/${id}?key=${Constants.API_KEY}")
                            .build()
                        client.newCall(request).execute().use { responseGameDetails ->
                            if (!responseGameList.isSuccessful) throw IOException("Unexpected code $responseGameList")

                            val jsonGameDetailsObject =
                                JSONObject(responseGameDetails.body!!.string())

                            val name = jsonGameObject.getString("name")
                            val rating = jsonGameObject.getDouble("rating").toString()
                            val metacritic = jsonGameObject.getInt("metacritic").toString()
                            val image = jsonGameObject.getString("background_image")
                            val released = jsonGameObject.getString("released")

                            val description = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Html.fromHtml(
                                    jsonGameDetailsObject.getString("description"),
                                    Html.FROM_HTML_MODE_LEGACY
                                ).toString()
                            } else {
                                Html.fromHtml(jsonGameDetailsObject.getString("description"))
                                    .toString()
                            }

                            // Add all items to database
                            databaseDao.addGameItem(
                                GameItem(
                                    id,
                                    name,
                                    image,
                                    description,
                                    rating,
                                    metacritic,
                                    released,
                                    false,
                                )
                            )


                        }

                    }

                    val sharedPreferences = getSharedPreferences(sharedPref, 0)

                    if (sharedPreferences.getBoolean(isFirstLaunch, true)) {
                        // once data fetched show game list.
                        runOnUiThread {
                            with(sharedPreferences.edit()) {
                                putBoolean(isFirstLaunch, false)
                                apply()
                            }
                            recreate()
                        }
                    }

                }
            } catch (exception: Exception) {
                exception.localizedMessage
            }
        }.start()

    }


}