package cn.landis.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetBookDetail {
	public List<String> getList(String bookname) throws Exception {
		List<String> list = new ArrayList<String>();
		Document doc = Jsoup.connect(
				"https://book.douban.com/subject_search?search_text=" + bookname + "&cat=1001")
				.get();
		Element content = doc.getElementById("content");
		Elements links = content.getElementsByTag("a");
		List<String> arrayList = new ArrayList<String>();
		for (Element link : links) {
			String linkHref = link.attr("href");
			arrayList.add(linkHref);
		}
		String url = arrayList.get(1);
		String number = url.substring(url.lastIndexOf("subject/") + 8,
				url.lastIndexOf("/"));
		Document nextdoc = Jsoup.connect(
				"https://book.douban.com/subject/" + number + "/comments/")
				.get();
		Elements nextcontent = nextdoc.getElementsByClass("comment-content");
		int count = 0;
		for (Element element : nextcontent) {
			count++;
			String linkText = element.text();
			list.add(linkText);
			if (count == 10) {
				break;
			}
		}
		return list;
	}
}