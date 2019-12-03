package edu.cs371m.silverscreen

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.cs371m.silverscreen.ui.movies.MoviesViewModel

import kotlinx.android.synthetic.main.location_fragment.*


class Location : Fragment() {

    companion object {
        fun newInstance() = Location()
    }

    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view= inflater.inflate(R.layout.location_fragment, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity?.let { ViewModelProviders.of(it).get(MoviesViewModel::class.java) }!!


        search.setOnClickListener{

            viewModel.updateZip(edit_zip.text.toString())
            viewModel.updateRadius(edit_radius.text.toString())


            val frag = activity?.supportFragmentManager?.findFragmentByTag("location")
            Log.d("********************8", edit_zip.text.toString() )
            fragmentManager?.popBackStack()

            Log.d("********************8", activity?.supportFragmentManager?.backStackEntryCount.toString())
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(frag!!)
                ?.commit()
            //viewModel.netSubRefresh()

        }

    }



}