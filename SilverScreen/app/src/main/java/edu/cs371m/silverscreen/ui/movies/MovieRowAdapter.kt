package edu.cs371m.silverscreen.ui.movies

import android.graphics.Movie
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost

class MovieRowAdapter(private val MovieViewModel: MoviesViewModel)
    : RecyclerView.Adapter<MovieRowAdapter.VH>() {

    private var movies = listOf<MoviePost>()
    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        var movieTitleText = itemView.findViewById<TextView>(R.id.movie_name)
        var durationText = itemView.findViewById<TextView>(R.id.duration)
        var actors = itemView.findViewById<TextView>(R.id.pop_actors)
        var date = itemView.findViewById<TextView>(R.id.date)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var wholePost = itemView.findViewById<LinearLayout>(R.id.post)
        init{
            wholePost.setOnClickListener {
                val position = adapterPosition
                MoviesViewModel.doMoviePost(itemView.context, movies[position])
            }
        }

        fun bind(item: MoviePost?)
        {
            if(item == null)
                return;
            movieTitleText.text = item.movieName
            durationText.text = item.duration.substring(3,4)+" hr "+item.duration.substring(5,7)+" min"
           Log.d("message", item.img.image_url + "******")

            MovieViewModel.netFetchImage("http://developer.tmsimg.com/" +item.img.image_url +
                    "?api_key=bsj768xkm54t6wuchqxxrbrt", thumbnail, false)
            var i = 0
            val sb = StringBuilder()
            if(item.cast!=null) {
                while (i < item.cast.size) {
                    sb.append(item.cast[i])
                    sb.append(", ")
                    i++
                }
                sb.deleteCharAt(sb.length - 1)
                actors.text = sb.toString()
            }
            else
            {
                actors.isInvisible = true
            }
            date.isInvisible=true
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_row, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if(position==0)
        {
            holder.itemView.layoutParams= ViewGroup.LayoutParams(0,0)
        }
        else
            holder.bind(movies[holder.adapterPosition])

    }

    override fun getItemCount(): Int {
        return movies.size
    }
    fun getList(): List<MoviePost>
    {
        return movies
    }
    fun submitList(items: List<MoviePost>)
    {
        movies = items
        notifyDataSetChanged()
    }
    // Adapter does not have its own copy of list, it just observes
}