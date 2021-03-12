package com.ilance.google_news_with_retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_list.view.*

class RecyclerView_Adap (private val items : ArrayList<RecyclerView_Data>) : RecyclerView.Adapter<RecyclerView_Adap.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView_Adap.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_list, parent, false)
        return RecyclerView_Adap.ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView_Adap.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it->
            Toast.makeText(it.context, "Clicked :" + item.title, Toast.LENGTH_SHORT).show()
        }
        holder.apply{
            bind(listener, item)
            itemView.tag = item
        }

        holder.itemView.setOnClickListener{
            itemClickListner.onClick(it, position)
        }

    }

    // 구현해야되는 ViewHolder -> 클릭리스너는 여기서 사용 안함 .. 어떤게 나은지 확인 필요
    class ViewHolder(v:View):RecyclerView.ViewHolder(v){
        private var view : View = v
        fun bind(listener: View.OnClickListener, item:RecyclerView_Data){
            view.tv_Title.text = item.title
            view.tv_PubDate.text = item.pubDate
        }

    }

    //클릭 인터페이스 정의
    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }


}