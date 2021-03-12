package id.husni.mokat.core.utils

import id.husni.mokat.core.source.local.entity.MoviesEntity
import id.husni.mokat.core.source.remote.response.MoviesItem
import id.husni.mokat.core.domain.model.Movies

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
    fun mapEntitiesToDomain(input: List<MoviesEntity>): List<Movies> =
        input.map {
            Movies(
                id = it.id,
                title = it.title,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                overview = it.overview,
                backdropPath = it.backdropPath,
                posterPath = it.posterPath,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Movies) = MoviesEntity(
        id = input.id,
        title = input.title,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        overview = input.overview,
        backdropPath = input.backdropPath,
        posterPath = input.posterPath
    )

}