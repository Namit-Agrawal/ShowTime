package edu.cs371m.silverscreen.ui.trailer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.api.api.MoviePost

class Trailers : Fragment() {

    companion object {
        fun newInstance(moviePost: MoviePost): Trailers{
            val trailer = Trailers()
            val b = Bundle()
            b.putParcelable("moviepost", moviePost)
            trailer.arguments =b
            return trailer
        }
    }

    private lateinit var viewModel: TrailersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TrailersViewModel::class.java)
        val post = this.arguments?.getParcelable<MoviePost>("moviepost")
        if (post != null) {

        }
    }

}
