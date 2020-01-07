package com.ramesh.reposearchingit.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.ramesh.reposearchingit.R
import com.ramesh.reposearchingit.extensions.toast
import com.ramesh.reposearchingit.models.Repository
import com.squareup.picasso.Picasso

class DisplayAdapter(private val context: Context, private var repoList: List<Repository>) : RecyclerView.Adapter<DisplayAdapter.MyViewHolder>() {
    companion object {

        private val TAG = DisplayAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = repoList[position]
        //get the avatar and set into Imageview
        Picasso.get().load(current.owner!!.avatar_url).resize(200, 200).into(holder.avatar)

        holder.setData(current, position)
    }

    override fun getItemCount(): Int = repoList.size //expression


    fun swap(dataList: List<Repository>) {
        if (dataList.isEmpty())
            context.toast("No Items Found")
        repoList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView
        private val language: TextView
        private val stars: TextView
        private val description: TextView
        private var pos: Int = 0
        val avatar: ImageView
        private var current: Repository? = null

        init {

            name = itemView.findViewById(R.id.txvName)
            avatar = itemView.findViewById(R.id.img_avatar)
            language = itemView.findViewById(R.id.txvLanguage)
            description = itemView.findViewById(R.id.txvDescription)
            stars = itemView.findViewById(R.id.txvStars)

            itemView.setOnClickListener {
                current?.let {
                    val url = current!!.htmlUrl
                    val webpage = Uri.parse(url)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }

            }
        }

        fun setData(current: Repository?, position: Int) {

            current?.let {
                this.name.text = current.name
                this.language.text = current.language
                this.description.text = current.description.toString()
                this.stars.text = current.stars.toString()
            }
            this.pos = position
            this.current = current

        }

    }

}
