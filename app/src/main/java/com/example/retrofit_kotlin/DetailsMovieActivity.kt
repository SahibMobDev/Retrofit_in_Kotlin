package com.example.retrofit_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.Scale
import com.example.retrofit_kotlin.api.ApiClient
import com.example.retrofit_kotlin.api.ApiService
import com.example.retrofit_kotlin.databinding.ActivityDetailesMovieBinding
import com.example.retrofit_kotlin.response.DetailsMovieResponse
import com.example.retrofit_kotlin.utils.Constants.POSTER_BASEURL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailesMovieBinding
    private val api : ApiService by lazy { ApiClient().getClient().create(ApiService::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailesMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("id", 1)

        binding.apply {
            val callDetailsMovieApi = api.getMovieDetails(movieId)
            callDetailsMovieApi.enqueue(object : Callback<DetailsMovieResponse> {
                override fun onResponse(
                    call: Call<DetailsMovieResponse>,
                    response: Response<DetailsMovieResponse>
                ) {
                    when(response.code()) {
                        //Successful response
                        in 200..299 -> {
                            response.body().let { itBody ->
                                val imagePoster = POSTER_BASEURL + itBody!!.posterPath
                                imgMovie.load(imagePoster) {
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                                imgBackground.load(imagePoster) {
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }

                                tvMovieName.text = itBody.title
                                tvTagLine.text = itBody.tagline
                                tvMovieDateReleased.text = itBody.releaseDate
                                tvMovieRating.text = itBody.voteAverage.toString()
                                tvMovieRuntime.text = itBody.runtime.toString()
                                tvMovieBudget.text = itBody.budget.toString()
                                tvMovieRevenue.text = itBody.revenue.toString()
                                tvMovieOverview.text= itBody.overview.toString()


                            }
                        }
                        //Redirection message
                        in 300..399 -> {
                            Log.d("Response code", "Redirection message ${response.code()}")
                        }
                        //Client error response
                        in 400..499 -> {
                            Log.d("Response code", "Client error message ${response.code()}")}
                        //Server error response
                        in 500..599 -> {
                            Log.d("Response code", "Server error message ${response.code()}")}
                    }
                }

                override fun onFailure(call: Call<DetailsMovieResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}