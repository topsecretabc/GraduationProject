package com.jeff.footballmanager.service;

public class ImageInfo implements Comparable<ImageInfo>{

	private String ImageName;
	
	private long modifyTime;

	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getImageName() {
		return ImageName;
	}

	public void setImageName(String imageName) {
		ImageName = imageName;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public int compareTo(ImageInfo another) {
		return (int) (this.getModifyTime()-another.getModifyTime());
	}

	@Override
	public String toString() {
		return "ImageInfo [ImageName=" + ImageName + ", modifyTime="
				+ modifyTime + "]";
	}
	
}
