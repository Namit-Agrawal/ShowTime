package edu.cs371m.silverscreen.ui.trailer

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.cs371m.silverscreen.R

class Trailers : Fragment() {

    companion object {
        fun newInstance() = Trailers()
    }

    private lateinit var viewModel: TrailersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_row, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TrailersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}