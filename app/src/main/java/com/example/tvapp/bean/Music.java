package com.example.tvapp.bean;

import com.example.tvapp.utils.TVPlayer;

public class Music {
	
	
	public String name;
	private String fileid;
	private String type;
	private String subtype;
	private String title;
	private String tag;
	private String sammary;
	private String image;
	private String url;
	private String urlplus;
	private String seconds;
	private String clickcount;
	public int iconId;
	private int play_state=TVPlayer.STATE_STOP;//播放状态，与TVPlayer一样
	private boolean isPlay;
	
	
	public boolean isPlay() {
		return isPlay;
	}
	public void setPlay(boolean isPlay) {
		this.isPlay = isPlay;
	}
	public int getPlay_state() {
		return play_state;
	}
	public void setPlay_state(int play_state) {
		this.play_state = play_state;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getSammary() {
		return sammary;
	}
	public void setSammary(String sammary) {
		this.sammary = sammary;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlplus() {
		return urlplus;
	}
	public void setUrlplus(String urlplus) {
		this.urlplus = urlplus;
	}
	public String getSeconds() {
		return seconds;
	}
	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}
	public String getClickcount() {
		return clickcount;
	}
	public void setClickcount(String clickcount) {
		this.clickcount = clickcount;
	}
	public int getIconId() {
		return iconId;
	}
	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return iconId;
	}
	public void setId(int id) {
		this.iconId = id;
	}

}
