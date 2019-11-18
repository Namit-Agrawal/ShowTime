package edu.cs371m.silverscreen.ui.movies

import android.graphics.Movie
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
            }
        }

        fun bind(item: MoviePost?)
        {
            if(item == null)
                return;
            movieTitleText.text = item.movieName
            date.text = item.date
//            MovieViewModel.netCreditRefresh(item.movie_id)
//            MovieViewModel.netMovieRefresh(item.movie_id)
           // val cast = MovieViewModel.observeCast().observe(fragment, Observer {  })
            val movie = MovieViewModel.observeMovieInfo().value

           // durationText.text = movie?.duration.toString() + "minutes"

//            val cast_array = cast!!.cast
//            actors.text = cast_array[0].actor +", " +cast_array[1].actor
            if(item.thumbnail!=null)
            {
                MovieViewModel.netFetchImage(item.thumbnail, thumbnail)
            }


           // date.text = item.date.toString()
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_row, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
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