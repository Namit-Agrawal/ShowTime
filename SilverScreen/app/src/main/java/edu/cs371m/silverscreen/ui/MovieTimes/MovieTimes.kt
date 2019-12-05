package edu.cs371m.silverscreen.ui.movie_times

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.Times
import edu.cs371m.silverscreen.ui.MovieTimes.MovieTimesAdapter
import edu.cs371m.silverscreen.ui.cast.Cast
import edu.cs371m.silverscreen.ui.movies.MovieRowAdapter
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel
import kotlinx.android.synthetic.main.movie_times_fragment.*

class MovieTimes : Fragment() {

    companion object {
        fun newInstance(moviePost: MoviePost) : MovieTimes {
            val times = MovieTimes()
            val b = Bundle()
            b.putParcelable("moviepost", moviePost)
            times.arguments = b
            return times
        }    }

    private lateinit var viewModel: MovieTimesViewModel
    private lateinit var date: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        date = "2019-12-03"
        viewModel =
            ViewModelProviders.of(this).get(MovieTimesViewModel::class.java)

        val root = inflater.inflate(R.layout.movie_times_fragment, container, false)

        val adapter = initRecyclerView(root)


        val post = this.arguments?.getParcelable<MoviePost>("moviepost")
        val times = post!!.showtimes

        //create buttons on click listeners
        var day1 = root.findViewById<LinearLayout>(R.id.day1)
        var day2 = root.findViewById<LinearLayout>(R.id.day2)
        var day3 = root.findViewById<LinearLayout>(R.id.day3)
        var day4 = root.findViewById<LinearLayout>(R.id.day4)
        var day5 = root.findViewById<LinearLayout>(R.id.day5)
        var day6 = root.findViewById<LinearLayout>(R.id.day6)
        var day7 = root.findViewById<LinearLayout>(R.id.day7)

        day1.setOnClickListener{
            date = "2019-12-01"
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }
        day2.setOnClickListener{
            date = "2019-12-02"
            var correct = organizeShowtimes(times!!)
            if (correct != null)

            adapter.submitList(correct)
        }
        day3.setOnClickListener{
            date = "2019-12-03"
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }
        day4.setOnClickListener{
            date = "2019-12-04"
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }
        day5.setOnClickListener{
            date = "2019-12-05"
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }
        day6.setOnClickListener{
            date = "2019-12-06"
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }
        day7.setOnClickListener{
            date = "2019-12-07"
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }

        var correct = organizeShowtimes(times!!)
        if (correct != null)
            adapter.submitList(correct)
        return root
    }

    private fun organizeShowtimes(times: List<Times>): List<TheatreTimes>? {
        if (times == null) {
            return null
        }
        val correct_times = mutableListOf<TheatreTimes>()

        var index = 0

        if (times.size > 0) {
            //for the first object

            var currentTheatre = times[index].theatre!!.name
            var currentList = mutableListOf<String>()


            while (index <times.size) {

                if (times[index].theatre!!.name?.equals(currentTheatre)!!) {
                    if (times[index].time!!.substring(0,10).equals(date)) {
                        currentList.add(times[index].time!!)
                        // edge case
                        if (index == times.size - 1) {

                            val theatre = TheatreTimes()
                            theatre.theatreName = currentTheatre!!
                            theatre.times = currentList
                            correct_times.add(theatre)
                        }
                    }
                } else {
                    //done with this particular theatre, add to list
                    val theatre = TheatreTimes()
                    theatre.theatreName = currentTheatre!!
                    theatre.times = currentList

                    correct_times.add(theatre)
                    //now keep track of new theatre
                    currentTheatre = times[index].theatre!!.name
                    currentList = mutableListOf()
                }
                index++

            }
        }
        Log.d("message"," ********8"+ correct_times.size.toString() + " " +times.size.toString())
        return correct_times
    }

    private fun initRecyclerView(root: View?): MovieTimesAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_movies)

        val adapter = MovieTimesAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }


}

class TheatreTimes {
     lateinit var theatreName :String

     lateinit var times: List<String>

}
