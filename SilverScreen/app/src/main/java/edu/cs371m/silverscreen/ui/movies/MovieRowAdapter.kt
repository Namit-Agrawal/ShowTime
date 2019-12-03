package edu.cs371m.silverscreen.ui.movies

import android.graphics.Movie
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost

class MovieRowAdapter(private val MovieViewModel: MoviesViewModel)
    : ListAdapter<MoviePost, MovieRowAdapter.VH>(MovieDiff()) {

    class MovieDiff : DiffUtil.ItemCallback<MoviePost>(){
        override fun areItemsTheSame(oldItem: MoviePost, newItem: MoviePost): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: MoviePost, newItem: MoviePost): Boolean {
            return oldItem.movieName == newItem.movieName
                    && oldItem.duration == newItem.duration
                    && oldItem.releaseDate == newItem.releaseDate
        }
    }

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        var movieTitleText = itemView.findViewById<TextView>(R.id.movie_name)
        var durationText = itemView.findViewById<TextView>(R.id.duration)
        var actors = itemView.findViewById<TextView>(R.id.pop_actors)
        var date = itemView.findViewById<TextView>(R.id.date)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var wholePost = itemView.findViewById<LinearLayout>(R.id.post)
        var fav = itemView.findViewById<ImageButton>(R.id.favBut)
        init{
            wholePost.setOnClickListener {

                val position = adapterPosition
                MoviesViewModel.doMoviePost(itemView.context, getItem(position))
            }
            fav.setOnClickListener{
                var position = adapterPosition
                if(MovieViewModel.isFav(getItem(position))) {
                    MovieViewModel.removeFav(getItem(position))
                } else {
                    MovieViewModel.addFav(getItem(position))
                }
                notifyItemChanged(position)

            }
        }

        fun bind(item: MoviePost?)
        {
            if(item == null)
                return;
            if (MovieViewModel.isFav(item)) {
                fav.setImageResource(R.drawable.ic_favorite_black_24dp)
            } else {
                fav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
            movieTitleText.text = item.movieName
            if(item.duration!=null)
            durationText.text = item.duration.substring(3,4)+" hr "+item.duration.substring(5,7)+" min"
           Log.d("message", item.img.image_url + "******")

            MovieViewModel.netFetchImage("http://demo.tmsimg.com/"+item.img.image_url, thumbnail, false)

            if(item.cast!=null) {
                actors.text = item.getMovieList(item.cast)
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
        if(getItem(position).type.equals("Theatre Event"))
        {
            holder.itemView.layoutParams= ViewGroup.LayoutParams(0,0)
        }
        else
            holder.bind(getItem(holder.adapterPosition))

    }


    // Adapter does not have its own copy of list, it just observes
}