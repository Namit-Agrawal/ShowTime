package edu.cs371m.silverscreen.ui.recommendation

import android.text.style.TtsSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MovieDBPost
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel

class RecommendationsAdapter(private val viewModel: MoviesViewModel): RecyclerView.Adapter<RecommendationsAdapter.VH>() {
    private var recommendations = listOf<MovieDBPost>()

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        var movieTitleText = itemView.findViewById<TextView>(R.id.movie_name)
        var durationText = itemView.findViewById<TextView>(R.id.duration)
        var actors = itemView.findViewById<TextView>(R.id.pop_actors)
        var date = itemView.findViewById<TextView>(R.id.date)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var wholePost = itemView.findViewById<LinearLayout>(R.id.post)

        var fav = itemView.findViewById<ImageButton>(R.id.favBut)

        init{

        }
        fun bind(item: MovieDBPost?) {
            if (item == null) {
                return;
            }
            movieTitleText.text = item.movieName


            if(item.thumbnail!=null)
            {
                viewModel.netFetchImage("https://image.tmdb.org/t/p/w185/"+item.thumbnail, thumbnail, false)
            }


        }


    }

    override fun onBindViewHolder(holder: VH, position: Int) {

            holder.bind(recommendations[holder.adapterPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_row, parent, false)
        return VH(itemView)
    }
    override fun getItemCount(): Int {
        return recommendations.size
    }
    fun submitList(items: List<MovieDBPost>) {
        recommendations = items
        notifyDataSetChanged()
    }

}
