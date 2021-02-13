package id.husni.mokat.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movies(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val isFavorite: Boolean = false
): Parcelable