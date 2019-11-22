package edu.cs371m.silverscreen.ui.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import edu.cs371m.silverscreen.R

class TheatersFragment : Fragment() {

    private lateinit var viewModel: TheatersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
                ViewModelProviders.of(this).get(TheatersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_theaters, container, false)
        val toolBar = root.findViewById<Toolbar>(R.id.toolbar)
        if(activity is AppCompatActivity){
            (activity as AppCompatActivity).setSupportActionBar(toolBar)
            (activity as AppCompatActivity).supportActionBar.let {
                it?.setDisplayShowTitleEnabled(false)
                it?.setDisplayShowCustomEnabled(true)
                val customView: View =
                    layoutInflater.inflate(R.layout.actionbar_multiple, null)
                // Apply the custom view
                it?.customView = customView
            }
        }
        viewModel.netSubRefresh()
        val adapter = initRecyclerView(root)
        viewModel.observeTheaters().observe(this, Observer {
                        adapter.submitList(it)
        })


        return root
    }


    private fun initRecyclerView(root: View?): TheaterRowAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_theater)

        val adapter =TheaterRowAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }

}



