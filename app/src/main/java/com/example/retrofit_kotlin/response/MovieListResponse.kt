package com.example.retrofit_kotlin.response


import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("page")
    val page: Int, // 1
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int, // 36892
    @SerializedName("total_results")
    val totalResults: Int // 737828
) {
    data class Result(
        @SerializedName("adult")
        val adult: Boolean, // false
        @SerializedName("backdrop_path")
        val backdropPath: String, // /faXT8V80JRhnArTAeYXz0Eutpv9.jpg
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("id")
        val id: Int, // 315162
        @SerializedName("original_language")
        val originalLanguage: String, // en
        @SerializedName("original_title")
        val originalTitle: String, // Puss in Boots: The Last Wish
        @SerializedName("overview")
        val overview: String, // Puss in Boots discovers that his passion for adventure has taken its toll: He has burned through eight of his nine lives, leaving him with only one life left. Puss sets out on an epic journey to find the mythical Last Wish and restore his nine lives.
        @SerializedName("popularity")
        val popularity: Double, // 5032.178
        @SerializedName("poster_path")
        val posterPath: String, // /kuf6dutpsT0vSVehic3EZIqkOBt.jpg
        @SerializedName("release_date")
        val releaseDate: String, // 2022-12-07
        @SerializedName("title")
        val title: String, // Puss in Boots: The Last Wish
        @SerializedName("video")
        val video: Boolean, // false
        @SerializedName("vote_average")
        val voteAverage: Double, // 8.6
        @SerializedName("vote_count")
        val voteCount: Int // 3032
    )
}