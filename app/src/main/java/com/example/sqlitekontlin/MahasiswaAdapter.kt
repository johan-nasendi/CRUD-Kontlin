package com.example.sqlitekontlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MahasiswaAdapter : RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder>() {

    private  var mshList : ArrayList<MahasiswaModel> = ArrayList()
    private  var onclickItem: ((MahasiswaModel) -> Unit)? = null
    private  var onclickDeleteItem: ((MahasiswaModel) -> Unit)? = null
    fun  addItems(items: ArrayList<MahasiswaModel>) {
        this.mshList = items
        notifyDataSetChanged()
    }
    fun setOnclickitem(callback: (MahasiswaModel)-> Unit) {
        this.onclickItem = callback
    }
    fun setOnclickDeleteItem(callback: (MahasiswaModel)-> Unit) {
        this.onclickDeleteItem = callback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MahasiswaViewHolder(
         LayoutInflater.from(parent.context).inflate(R.layout.card_items_msh, parent, false)
    )

    override fun onBindViewHolder(holder: MahasiswaViewHolder, position: Int) {
        val msh = mshList[position]
        holder.bindView(msh)
        holder.itemView.setOnClickListener { onclickItem?.invoke(msh) }
        holder.btnDelete.setOnClickListener { onclickDeleteItem?.invoke(msh) }
    }



    override fun getItemCount(): Int {
        return mshList.size
    }

    class MahasiswaViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
         var btnDelete = view.findViewById<TextView>(R.id.btnDelete)

        fun bindView(msh : MahasiswaModel) {
            id.text = msh.id.toString()
            name.text = msh.name
            email.text = msh.email
        }
    }
}