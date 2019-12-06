package edu.cs371m.silverscreen.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import edu.cs371m.silverscreen.R

class ListFriendsAdapter(private val viewModel: ListFriendsViewModel): RecyclerView.Adapter<ListFriendsAdapter.VH>() {
    private var friends = listOf<String>()

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.name)

        fun bind(item: String?) {
            if (item == null) {
                return;
            }

            //uid of the friends
            var database = FirebaseFirestore.getInstance()
            val user = FirebaseAuth.getInstance().currentUser

            val requestDoc = database.collection("Users").document(item)
            requestDoc.get().addOnSuccessListener {
                val x = it.data!!.get("username")
                name.text = x.toString()
            }
            //set on click listeners


        }
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(friends[holder.adapterPosition])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ListFriendsAdapter.VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_row, parent, false)
        return VH(itemView)
    }
    override fun getItemCount(): Int {
        return friends.size
    }
    fun submitList(items: List<String>) {
        friends = items
        notifyDataSetChanged()
    }
}