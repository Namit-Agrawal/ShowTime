package edu.cs371m.silverscreen.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.view.isInvisible
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
            durationText.text = item.duration.substring(2)
            var i = 0
            val sb = StringBuilder()
            if(item.cast!=null) {
                while (i < item.cast.size) {
                    sb.append(item.cast[i])
                    sb.append(" ,")
                    i++
                }
                sb.deleteCharAt(sb.length - 1)
                actors.text = sb.toString()
            }
            else
            {
                actors.isInvisible = true
            }
            date.text = item.date.toString()
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_row, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.bind(movies[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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