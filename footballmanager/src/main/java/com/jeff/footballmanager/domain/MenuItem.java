package com.jeff.footballmanager.domain;

public class MenuItem {

	private String itemName;
	
	private int ImageId;

	public MenuItem(int icLauncher, String string) {
		this.ImageId = icLauncher;
		this.itemName = string;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getImageId() {
		return ImageId;
	}

	public void setImageId(int imageId) {
		ImageId = imageId;
	}
	
}
