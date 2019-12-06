package edu.cs371m.silverscreen.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import edu.cs371m.silverscreen.R

class RequestAdapter(private val viewModel: RequestViewModel): RecyclerView.Adapter<RequestAdapter.VH>() {
    private var requests = listOf<String>()

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.name)
        var add = itemView.findViewById<Button>(R.id.add_b)


        fun bind(item: String?) {
            if (item == null) {
                return;
            }

            //get the dipslay name instead of the uid
            var database = FirebaseFirestore.getInstance()
            val user = FirebaseAuth.getInstance().currentUser

            val requestDoc = database.collection("Users").document(item)
            requestDoc.get().addOnSuccessListener {
                val x = it.data!!.get("username")
                name.text = x.toString()
            }

            add.setOnClickListener{
                Log.d("button", "went in" + item)
                //user is b
                //a is the requestDoc


                //reuqest should disappear
                //this is user
                var doc = database.collection("Users").document(user!!.uid).collection("Other data")
                    .document("new requests")
                doc.update("list", FieldValue.arrayRemove(item))
                var doc_friend = database.collection("Users").document(user!!.uid).collection("Other data")
                    .document("friend")
                doc_friend.update("list",FieldValue.arrayUnion(item))

                var doc_friend2 = database.collection("Users").document(item).collection("Other data")
                    .document("friend").update("list",FieldValue.arrayUnion(user!!.uid))


                //and (a request to b)
                //b's friends now has a
                //a's friend now has b




            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
            Log.d("checking bind", requests.size.toString())
            holder.bind(requests[holder.adapterPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_row, parent, false)
        return VH(itemView)
    }
    override fun getItemCount(): Int {
        return requests.size
    }
    fun submitList(items: List<String>) {
        requests = items
        notifyDataSetChanged()
    }
}