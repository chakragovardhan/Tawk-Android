package com.tawk.example.view.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawk.example.R
import com.tawk.example.data.model.ApiUser
import com.tawk.example.view.retrofit.single.UsersListActivity
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class ApiUserAdapter(
    private val users: ArrayList<ApiUser>, val activity: UsersListActivity
) : RecyclerView.Adapter<ApiUserAdapter.DataViewHolder>(), Filterable {
    var usersFilterList = ArrayList<ApiUser>()

    init {
        usersFilterList = users
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: ApiUser, activity: UsersListActivity) {
            itemView.textViewUserName.text = user.login
            itemView.textViewUserDetails.text = user.url
            Glide.with(itemView.imageViewAvatar.context)
                .load(user.avatar_url)
                .into(itemView.imageViewAvatar)
            itemView.setOnClickListener {
                activity.onItemClick(user.login)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = usersFilterList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(usersFilterList[position], activity)

    fun addData(list: List<ApiUser>) {
        users.addAll(list)
        usersFilterList = users
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    usersFilterList = users
                } else {
                    val resultList = ArrayList<ApiUser>()
                    for (row in users) {
                        if (row.login.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    usersFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = usersFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                usersFilterList = results?.values as ArrayList<ApiUser>
                notifyDataSetChanged()
            }

        }
    }

}