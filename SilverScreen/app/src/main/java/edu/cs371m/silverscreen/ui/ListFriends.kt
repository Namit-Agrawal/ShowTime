package edu.cs371m.silverscreen.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

import edu.cs371m.silverscreen.R
import kotlinx.android.synthetic.main.list_friends_fragment.*

class ListFriends : Fragment() {

    companion object {
        fun newInstance() = ListFriends()
    }

    private lateinit var viewModel: ListFriendsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.let { ViewModelProviders.of(it).get(ListFriendsViewModel::class.java) }!!

        var root = inflater.inflate(R.layout.list_friends_fragment, container, false)
        val adapter = initRecyclerView(root)

        viewModel.observeFriends().observe(this, Observer {
            Log.d("snaity", it.size.toString())
            adapter.submitList(it)
        })

        //addd the logic for adding new friend
        var add = root.findViewById<Button>(R.id.add)
        add.setOnClickListener{
            val new_friend = search.text.toString()
            val database = FirebaseFirestore.getInstance()
            database.collection("Users")
                .get()
                .addOnSuccessListener { result ->

                    for (document in result) {
                        Log.d("help", "${document.id} => ${document.data}")
                        val map = document.data
                        val name = map.get("username").toString()
                        Log.d("name", name)
                        if (name.equals(new_friend)) {
                            Log.d("here", "got friend from databasee")
                            //found_friend = document
                            val userInfo = database.collection("Users").document(document.id)
                                .collection("Other data").document("new requests")

                            userInfo.update("list", FieldValue.arrayUnion(FirebaseAuth.getInstance().currentUser!!.uid))

                            break
                        }
                    }


                }
        }


        return root
    }



    private fun initRecyclerView(root: View?): ListFriendsAdapter {
        val rv = root!!.findViewById<RecyclerView>(R.id.recycler_view_friend)

        val adapter =ListFriendsAdapter(viewModel)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        itemDecor.setDrawable(ContextCompat.getDrawable(rv.context, R.drawable.divider)!!)
        rv.addItemDecoration(itemDecor)
        return adapter
    }

}
