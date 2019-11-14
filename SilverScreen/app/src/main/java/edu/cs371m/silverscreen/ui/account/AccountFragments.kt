package edu.cs371m.silverscreen.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import edu.cs371m.silverscreen.R

class AccountFragment : Fragment() {

    private lateinit var dashboardViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        if(activity is AppCompatActivity){
            val act = (activity as AppCompatActivity).supportActionBar
            act?.hide()
        }
        val textView: TextView = root.findViewById(R.id.text_account)
        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}