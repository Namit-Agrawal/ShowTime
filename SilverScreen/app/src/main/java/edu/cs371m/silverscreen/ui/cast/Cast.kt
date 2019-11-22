package edu.cs371m.silverscreen.ui.cast
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.cs371m.silverscreen.R

class Cast : Fragment() {

    companion object {
        fun newInstance() = Cast()
    }

    private lateinit var viewModel: CastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CastViewModel::class.java)
        // TODO: Use the ViewModel
    }

}