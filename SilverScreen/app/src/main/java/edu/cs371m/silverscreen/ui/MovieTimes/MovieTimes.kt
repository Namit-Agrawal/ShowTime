package edu.cs371m.silverscreen.ui.movie_times

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.cs371m.silverscreen.R
import kotlinx.android.synthetic.main.movie_times_fragment.*

class MovieTimes : Fragment() {

    companion object {
        fun newInstance() = MovieTimes()
    }

    private lateinit var viewModel: MovieTimesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_times_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieTimesViewModel::class.java)
        // TODO: Use the ViewModel
        button.setOnClickListener {

            button.setBackgroundColor(Color.parseColor("#FF8000"))
            day_of_week.setTextColor(Color.WHITE)
            day.setTextColor(Color.WHITE)
            month.setTextColor(Color.WHITE)
        }
    }

}