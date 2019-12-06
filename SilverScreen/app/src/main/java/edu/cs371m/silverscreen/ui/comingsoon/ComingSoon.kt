package edu.cs371m.silverscreen.ui.comingsoon

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import edu.cs371m.silverscreen.R
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel
import edu.cs371m.silverscreen.ui.comingsoon.ComingSoonRowAdapter

class ComingSoon : Fragment() {

    companion object {
        fun newInstance() = ComingSoon()
    }

    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = activity?.let { ViewModelProviders.of(it).get(MoviesViewModel::class.java) }!!
        val root = inflater.inflate(R.layout.coming_soon_fragment, container, false)
        val toolBar = root.findViewById<Toolbar>(R.id.toolbar)
        if(activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolBar)
            (activity as AppCompatActivity).supportActionBar.let {
                it?.setDisplayShowTitleEnabled(true)
                it?.setDisplayShowCustomEnabled(true)
                it?.title = "Coming Soon"
            }
        }

        val adapter = initRecyclerView(root)

        viewModel.netSubComingSoonRefresh()

        viewModel.observeComingSoon().observe(this, Observer {
            adapter.submitList(it)
        })
        return root
    }

    private fun initRecyclerView(root: View?): ComingSoonRowAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_comingsoon)

        val adapter =ComingSoonRowAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }
}