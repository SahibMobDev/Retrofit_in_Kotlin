package com.example.retrofit_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit_kotlin.adapter.MovieAdapter
import com.example.retrofit_kotlin.api.ApiClient
import com.example.retrofit_kotlin.api.ApiService
import com.example.retrofit_kotlin.databinding.ActivityMainBinding
import com.example.retrofit_kotlin.response.MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val api: ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            prgBarMovie.visibility = View.VISIBLE

            val callMovieApi = api.getPopularMovie(1)
            callMovieApi.enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    prgBarMovie.visibility = View.GONE
                    when(response.code()) {
                        //Successful response
                        in 200..299 -> {
                            response.body().let {itBody ->
                            itBody?.results.let {itData ->
                                if (itData!!.isNotEmpty()) {
                                    movieAdapter.differ.submitList(itData)
                                    rvMovie.layoutManager = LinearLayoutManager(this@MainActivity)
                                    rvMovie.adapter = movieAdapter
                                }
                            }

                            }
                        }
                        //Redirection message
                        in 300..399 -> {
                            Log.d("Response code", "Redirection message ${response.code()}")
                        }
                        //Client error response
                        in 400..499 -> {Log.d("Response code", "Client error message ${response.code()}")}
                        //Server error response
                        in 500..599 -> {Log.d("Response code", "Server error message ${response.code()}")}
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    binding.prgBarMovie.visibility = View.GONE
                    Log.e("onFailure", "Error: ${t.message}")
                }

            })
        }
    }
}