package com.ilance.google_news_with_retrofit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class MainActivity : AppCompatActivity() {

    val list:ArrayList<RecyclerView_Data> = ArrayList<RecyclerView_Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews();
    }
    private val newsService = GoogleNewsServiceFactory.makeNewsService(true, "https://news.google.com")

    private fun initViews() {
        btn_main.setOnClickListener {
            newsService.getNews("ko", "KR", "KR:ko")
                    .enqueue(object : Callback<RespNewsRss> {
                        override fun onResponse(call: Call<RespNewsRss>, response: Response<RespNewsRss>) {
                            tv_main.text = response.body()?.channel?.newsList?.toString()
                            val content = response.body()?.channel?.newsList?.toString()?.split("RespNews");

//                          title=내용 - 뉴스, link=https:// , pubDate=Fri, 11 Mar 2021 23:55:55 GMT , description=각종 정보, source=뉴스
                            if (content != null) {
                                for(item in content){
                                    val detail_Content = item.split("(title=")
                                    var index_cnt = 0;
                                    var title:String = ""
                                    var uri:Uri = Uri.parse("https://")
                                    var pubdate: String = ""
                                    var source:String = ""
                                    var title_end_index = 0
                                    var link_end_index = 0
                                    var pubdate_start_index = 0
                                    var pubdate_end_index = 0
                                    var source_start_index = 0

                                    for (real_Content in detail_Content){
                                        title_end_index = real_Content.indexOf(", link=")
                                        link_end_index = real_Content.indexOf(", guid=")
                                        pubdate_start_index = real_Content.indexOf(", pubDate=")
                                        pubdate_end_index = real_Content.indexOf(", description=")
                                        source_start_index = real_Content.indexOf(", source=")
                                        if(real_Content.length > 5){ // 임의로 ,, 최초에 '[' 하나 들은 배열이 들어옴 예외처리
                                            title = real_Content.substring(0, title_end_index)
                                            uri = Uri.parse(real_Content.substring(title_end_index + 7, link_end_index ))
                                            pubdate = real_Content.substring(pubdate_start_index + 10, pubdate_end_index)
                                            source = real_Content.substring(source_start_index + 9)
                                            source = source.substring(0, source.indexOf(")"))
                                            Log.d("DEBUG_SB", "title = " + title)
                                            Log.d("DEBUG_SB", "uri = " + uri)
                                            Log.d("DEBUG_SB", "pubdate = " + pubdate)
                                            Log.d("DEBUG_SB", "source = " + source)
                                            list.add(index_cnt++, RecyclerView_Data(title, uri, pubdate, source))
                                        }
                                    }
                                }
                                Insert_List()
                            }
                        }

                        override fun onFailure(call: Call<RespNewsRss>, t: Throwable) {
                            Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
        }
    }


    fun Insert_List(){
        val adapter = RecyclerView_Adap(list)
        rv.adapter = adapter

        adapter.setItemClickListener(object :RecyclerView_Adap.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                Toast.makeText( applicationContext,"$position 번째 list 클릭", Toast.LENGTH_SHORT).show()
            }

        })
    }



}