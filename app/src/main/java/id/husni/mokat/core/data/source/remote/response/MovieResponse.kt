package id.husni.mokat.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(

	@field:SerializedName("results")
	val results: List<MoviesItem>

) : Parcelable

@Parcelize
data class MoviesItem(

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("poster_path")
	val posterPath: String
) : Parcelable
