package edu.cs371m.silverscreen.ui.Showtime

import android.graphics.Movie
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.Times
import edu.cs371m.silverscreen.ui.movie_times.TheatreTimes
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel
import okhttp3.internal.notify

class ShowtimesAdapter(private val viewModel: ShowtimesViewModel) : RecyclerView.Adapter<ShowtimesAdapter.VH>() {
    private var movies = listOf<MoviePost>()
    private lateinit var date:String
    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        var movieTitle = itemView.findViewById<TextView>(R.id.movie_name)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var wholePost = itemView.findViewById<LinearLayout>(R.id.post)
        var r0 = itemView.findViewById<TableRow>(R.id.r0)
        var r1 = itemView.findViewById<TableRow>(R.id.r1)
        var r2 = itemView.findViewById<TableRow>(R.id.r2)
        var r3 = itemView.findViewById<TableRow>(R.id.r3)
        var r4 = itemView.findViewById<TableRow>(R.id.r4)
        var r5 = itemView.findViewById<TableRow>(R.id.r5)


        init{
            wholePost.setOnClickListener {
                val position = adapterPosition
                MoviesViewModel.doMoviePost(itemView.context, movies[position], false)
            }
        }

        fun bind(item: MoviePost) {
            if (item == null) return

            r0.removeAllViews()
            r1.removeAllViews()
            r2.removeAllViews()
            r3.removeAllViews()
            r4.removeAllViews()
            r5.removeAllViews()






            var rowList = mutableListOf<TableRow>()
            rowList.add(r0)
            rowList.add(r1)
            rowList.add(r2)
            rowList.add(r3)
            rowList.add(r4)
            rowList.add(r5)

            //submit moviepost, but also reorganize showtimes to be in correct order
            movieTitle.text = item.movieName
            viewModel.netFetchImage(
                "http://demo.tmsimg.com/" + item.img!!.image_url,
                thumbnail,
                false
            )
            //need to check for each showtime
//            //call organize post
            var list = organizeShowtimes(item.showtimes!!)
            var size = list!!.size
            var buttonsList = mutableListOf<Button>()
            var i = 0
            var rowNum=0
            while (i < size) {
                var button = Button(itemView.context)
                button.text= list!![i].substring(11)
//                //TODO: what is index????? how to insure no overlapping?
                buttonsList.add(button)
                rowList[rowNum].addView(button, i%3)
                i++
                if (i % 3 == 0) {
                    rowNum++
                }
            }

        }
    }


    override fun getItemCount(): Int {
        if (movies == null) {
            return 0
        }
        return movies.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(movies[holder.adapterPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.showtime_row, parent, false)
        return VH(itemView)


    }

    private fun organizeShowtimes(times: List<Times>): List<String>? {
        if (times == null) {
            return null
        }
        val correct_times = mutableListOf<String>()
        var index = 0
        while (index < times.size) {
            if (times[index].time!!.substring(0,10).equals(date)) {
                correct_times.add(times[index].time!!)
            }

            index++
        }
        return correct_times
    }

    fun submitList(items: List<MoviePost>)
    {
        movies = items
        notifyDataSetChanged()
    }

    fun changeDate(day:String) {
        date = day
        //does this work..
        notifyDataSetChanged()

    }


}
