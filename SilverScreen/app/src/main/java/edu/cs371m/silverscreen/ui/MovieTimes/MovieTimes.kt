
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
import android.widget.TextView
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost
import edu.cs371m.silverscreen.api.api.Times
import edu.cs371m.silverscreen.ui.MovieTimes.MovieTimesAdapter
import edu.cs371m.silverscreen.ui.cast.Cast
import edu.cs371m.silverscreen.ui.movies.MovieRowAdapter
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel
import kotlinx.android.synthetic.main.movie_times_fragment.*
import org.w3c.dom.Text
import java.time.LocalDateTime
import java.util.*

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
        //format: 2017-08-02T11:25:44.973
        var time =LocalDateTime.now()

        //get an array of the correct date ready to be used
        date = time.toString().substring(0,10)

        val days_of_week = mutableListOf<String>()
        days_of_week.add(time.toString())

        var i :Long = 1
        while (i < 7) {
            days_of_week.add(time.plusDays(i).toString())
            i++
        }


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

//        //set the correct dayes
        var day_date1 = root.findViewById<TextView>(R.id.date1)
        var day_date2 = root.findViewById<TextView>(R.id.date2)
        var day_date3 = root.findViewById<TextView>(R.id.date3)
        var day_date4 = root.findViewById<TextView>(R.id.date4)
        var day_date5 = root.findViewById<TextView>(R.id.date5)
        var day_date6 = root.findViewById<TextView>(R.id.date6)
        var day_date7 = root.findViewById<TextView>(R.id.date7)
//
        var day_month1 = root.findViewById<TextView>(R.id.month1)
        var day_month2 = root.findViewById<TextView>(R.id.month2)
        var day_month3 = root.findViewById<TextView>(R.id.month3)
        var day_month4 = root.findViewById<TextView>(R.id.month4)
        var day_month5 = root.findViewById<TextView>(R.id.month5)
        var day_month6 = root.findViewById<TextView>(R.id.month6)
        var day_month7 = root.findViewById<TextView>(R.id.month7)
        val months = listOf<String>("January", "February","March","April", "May", "June",
            "July","August","September","October", "November", "December")
//        //today
        day_date1.text = days_of_week[0].substring(8,10)
        day_month1.text = months[days_of_week[0].substring(5,7).toInt() -1]
//
        day_date2.text = days_of_week[1].substring(8,10)
        day_month2.text = months[days_of_week[1].substring(5,7).toInt()-1]
//
        day_date3.text = days_of_week[2].substring(8,10)
        day_month2.text = months[days_of_week[2].substring(5,7).toInt()-1]
//

//
        day_date4.text = days_of_week[3].substring(8,10)
        day_month4.text = months[days_of_week[3].substring(5,7).toInt()-1]
//
        day_date5.text = days_of_week[4].substring(8,10)
        day_month5.text = months[days_of_week[4].substring(5,7).toInt()-1]
//
        day_date6.text = days_of_week[5].substring(8,10)
        day_month6.text = months[days_of_week[5].substring(5,7).toInt() -1]
        day_date7.text = days_of_week[6].substring(8,10)
        day_month7.text = months[days_of_week[6].substring(5,7).toInt()- 1]

        day1.setOnClickListener{
            //today
            date = days_of_week[0].substring(0,10)
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }
        day2.setOnClickListener{
            //today+ 1
            date = days_of_week[1].substring(0,10)
            var correct = organizeShowtimes(times!!)
            if (correct != null)

                adapter.submitList(correct)
        }
        day3.setOnClickListener{
            //today + 2
            date = days_of_week[2].substring(0,10)

            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }
        day4.setOnClickListener{
            date = days_of_week[3].substring(0,10)
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }
        day5.setOnClickListener{
            date = days_of_week[4].substring(0,10)
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }
        day6.setOnClickListener{
            date = days_of_week[5].substring(0,10)
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }
        day7.setOnClickListener{
            date = days_of_week[6].substring(0,10)
            var correct = organizeShowtimes(times!!)
            if (correct != null)
                adapter.submitList(correct)
        }

        var correct = organizeShowtimes(times!!)
        if (correct != null)
            adapter.submitList(correct)
        Log.d("times", times!!.size.toString())
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

                            var theatre = TheatreTimes()
                            theatre.theatreName = currentTheatre!!
                            theatre.times = currentList
                            correct_times.add(theatre)
                        }
                    }
                } else {
                    Log.d("currentTheatre", currentTheatre)
                    Log.d("list of times", currentList!!.toString())
                    //done with this particular theatre, add to list
                    var theatre = TheatreTimes()
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