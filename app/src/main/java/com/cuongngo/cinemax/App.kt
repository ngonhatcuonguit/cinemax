package com.cuongngo.cinemax

import android.app.Application
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleObserver
import com.cuongngo.cinemax.di.appMovieModule
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.response.movie_response.GenresMovieResponse
import com.cuongngo.cinemax.roomdb.AppDatabase
import com.cuongngo.cinemax.roomdb.Dao.GenreDao
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class App : Application(), KodeinAware, LifecycleObserver {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))
        import(appMovieModule)
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        private var genresMovieResponse = GenresMovieResponse(
            null,
            null,
            genres = arrayListOf()
        )

        var movieTrending = MovieResponse(
            null,
            null,
            null,
            null,
            null,
            results = arrayListOf()
        )

        @Volatile
        private var instance: App? = null

        @JvmStatic
        fun getInstance(): App = instance ?: synchronized(this) {
            instance ?: App().also {
                instance = it
            }
        }
        fun getString(@StringRes strId: Int): String {
            return getResources().getString(strId)
        }

        fun getDrawableResource(@DrawableRes drawableRes: Int): Drawable? {
            return ContextCompat.getDrawable(getInstance(), drawableRes)
        }

        fun getResources(): Resources {
            return getInstance().resources
        }

        fun getGenreDatabase() : GenreDao {
            return AppDatabase.getDatabase(getInstance()).genreDao()
        }

        fun getGenres(): GenresMovieResponse {
            return genresMovieResponse
        }

        fun setGenres(genresMovieResponse: GenresMovieResponse){
            this.genresMovieResponse = genresMovieResponse
        }

        fun setListTrending(movieResponse: MovieResponse){
            this.movieTrending = movieResponse
        }

    }
}