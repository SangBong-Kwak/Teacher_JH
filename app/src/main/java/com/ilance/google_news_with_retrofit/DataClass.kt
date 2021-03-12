package com.ilance.google_news_with_retrofit

/*
import com.google.gson.annotations.SerializedName

data class RespNewsRss(
    @field:SerializedName("channel") var channel: RespNewsRssChannel? = null
)

class RespNewsRssChannel(
    @field:SerializedName("items") var newsList: List<RespNews>? = null
)

data class RespNews(
    @field:SerializedName("title") var title: String? = null,
    @field:SerializedName("link") var link: String? = null,
    @field:SerializedName("guid") var guid: String? = null,
    @field:SerializedName("pubDate") var pubDate: String? = null,
    @field:SerializedName("description") var description: String? = null,
    @field:SerializedName("source") var source: String? = null
)

 */
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RespNewsRss(
    @field:Element(name = "channel") var channel: RespNewsRssChannel? = null
)

@Root(name = "channel", strict = false)
class RespNewsRssChannel(
    @field:ElementList(entry = "item", inline = true) var newsList: List<RespNews>? = null
)

@Element(name = "item")
data class RespNews(
    @field:Element(name = "title") var title: String? = null,
    @field:Element(name = "link") var link: String? = null,
    @field:Element(name = "guid") var guid: String? = null,
    @field:Element(name = "pubDate") var pubDate: String? = null,
    @field:Element(name = "description") var description: String? = null,
    @field:Element(name = "source") var source: String? = null
)