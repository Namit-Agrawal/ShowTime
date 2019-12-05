package edu.cs371m.silverscreen.ui.recommendation

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.cs371m.silverscreen.Location
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
        viewModel = activity?.let { ViewModelProviders.of(it).get(MoviesViewModel::class.java) }!!
        val root = inflater.inflate(R.layout.fragment_recommendations, container, false)
        val toolBar = root.findViewById<Toolbar>(R.id.toolbar)
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolBar)
            (activity as AppCompatActivity).supportActionBar.let {
                it?.setDisplayShowTitleEnabled(false)
                it?.setDisplayShowCustomEnabled(true)
                val customView: View =
                    layoutInflater.inflate(R.layout.actionbar_recommended, null)
                // Apply the custom view
                it?.customView = customView

                val rec = customView.findViewById<EditText>(R.id.get_Rec)

                rec.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            viewModel.updateAMovieForRec(rec.text.toString())
                            viewModel.netSubRecommendedSearchRefresh()

                            return true
                        }
                        return false
                    }
                })

            }
        }

        val adapter = initRecyclerView(root)

        viewModel.observeSearchRecommended().observe(this, Observer {
            if(it.size>0)
            {
                Log.d("************************* MOVIE ID", it[0].movie_id.toString())
                viewModel.updateId(it[0].movie_id)
                //viewModel.observeID().observe(this, Observer {
                    viewModel.netSubRecommendedRefresh()
                //})


            }
        })

        viewModel.observeRecommended().observe(this, Observer {
            adapter.submitList(it)
        })


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