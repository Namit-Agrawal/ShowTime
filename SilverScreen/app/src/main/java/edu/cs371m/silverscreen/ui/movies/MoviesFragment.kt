package edu.cs371m.silverscreen.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import edu.cs371m.silverscreen.R


class MoviesFragment : Fragment() {

    private lateinit var dashboardViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_movies, container, false)
        if(activity is AppCompatActivity){
            val act = (activity as AppCompatActivity).supportActionBar
            act?.show()
        }

//        val textView: TextView = root.findViewById(R.id.text_movies)
//        dashboardViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}