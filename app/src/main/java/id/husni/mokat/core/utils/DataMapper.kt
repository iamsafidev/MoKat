package id.husni.mokat.core.utils

import id.husni.mokat.core.data.source.local.entity.MoviesEntity
import id.husni.mokat.core.data.source.remote.response.MoviesItem

object DataMapper {
    fun mapResponseToEntities(input: List<MoviesItem>): List<MoviesEntity>{
        val moviesList = ArrayList<MoviesEntity>()
        input.map {
            val movies = MoviesEntity(
                id = it.id,
                title = it.title,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                overview = it.overview,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath
            )
            moviesList.add(movies)
        }
        return moviesList
    }
}