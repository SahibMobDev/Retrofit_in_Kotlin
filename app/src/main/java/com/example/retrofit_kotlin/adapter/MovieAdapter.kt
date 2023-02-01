package com.example.retrofit_kotlin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.retrofit_kotlin.DetailsMovieActivity
import com.example.retrofit_kotlin.R
import com.example.retrofit_kotlin.databinding.ItemRowBinding
import com.example.retrofit_kotlin.response.MovieListResponse
import com.example.retrofit_kotlin.utils.Constants.POSTER_BASEURL

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    lateinit var binding: ItemRowBinding
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        binding = ItemRowBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieListResponse.Result) {
            binding.apply {
                tvMovieName.text = item.title
                tvRate.text = item.voteAverage.toString()
                tvDate.text = item.releaseDate
                tvLang.text = item.originalLanguage
                val moviePosterUrl = POSTER_BASEURL + item.posterPath
                imgMovie.load(moviePosterUrl) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)

                }
                root.setOnClickListener {
                    val intent = Intent(context, DetailsMovieActivity::class.java)
                    intent.putExtra("id", item.id)
                    context.startActivity(intent)
                }
            }
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<MovieListResponse.Result>() {
        override fun areItemsTheSame(
            oldItem: MovieListResponse.Result,
            newItem: MovieListResponse.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieListResponse.Result,
            newItem: MovieListResponse.Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)
}