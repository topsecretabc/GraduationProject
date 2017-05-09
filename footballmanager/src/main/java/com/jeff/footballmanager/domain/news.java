package com.jeff.footballmanager.domain;

import android.graphics.Bitmap;

public class news {

	private String news_id;
	private String title;
	private String top_image;
	private String text_image0;
	private String text_image1;
	private String source;
	private String content;
	private String digest;
	private String reply_count;
	private String edit_time;
	private Bitmap topmap;
	public Bitmap getTopmap() {
		return topmap;
	}
	public void setTopmap(Bitmap topmap) {
		this.topmap = topmap;
	}
	public String getNews_id() {
		return news_id;
	}
	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTop_image() {
		return top_image;
	}
	public void setTop_image(String top_image) {
		this.top_image = top_image;
	}
	public String getText_image0() {
		return text_image0;
	}
	public void setText_image0(String text_image0) {
		this.text_image0 = text_image0;
	}
	public String getText_image1() {
		return text_image1;
	}
	public void setText_image1(String text_image1) {
		this.text_image1 = text_image1;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getReply_count() {
		return reply_count;
	}
	public void setReply_count(String reply_count) {
		this.reply_count = reply_count;
	}
	public String getEdit_time() {
		return edit_time;
	}
	public void setEdit_time(String edit_time) {
		this.edit_time = edit_time;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("News [");
		sb.append("news_id=");
		sb.append(news_id);
		sb.append(",title=");
		sb.append(title);
		sb.append(",top_image=");
		sb.append(top_image);
		sb.append(",text_image0=");
		sb.append(text_image0);
		sb.append(",text_image1=");
		sb.append(text_image1);
		sb.append(",source=");
		sb.append(source);
		sb.append(",content=");
		sb.append(content);
		sb.append(",digest=");
		sb.append(digest);
		sb.append(",reply_count=");
		sb.append(reply_count);
		sb.append(",edit_time=");
		sb.append(edit_time);
		sb.append(",topmap=");
		sb.append(topmap);
		sb.append("]");
		return sb.toString();
	}
}
