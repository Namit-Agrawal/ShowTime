package edu.cs371m.silverscreen.ui.recommendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.ui.movies.MovieRowAdapter
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel

class RecommendationsFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recommendations, container, false)
        if(activity is AppCompatActivity){
            val act = (activity as AppCompatActivity).supportActionBar
            act?.hide()
        }



        val adapter = initRecyclerView(root)
//        viewModel.observeMovies().observe(this, Observer {
//            adapter.submitList(it)
//        })
        return root
    }


    private fun initRecyclerView(root: View?): RecommendationsAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_movies)

        val adapter =RecommendationsAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }}
