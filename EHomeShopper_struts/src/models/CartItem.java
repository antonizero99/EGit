package models;

public class CartItem {
	
	private String name;
	private long price;
	private int qty;
	private int userID;
	private int pID;
	private String dateAdd;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getpID() {
		return pID;
	}
	public void setpID(int pID) {
		this.pID = pID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getDateAdd() {
		return dateAdd;
	}
	public void setDateOrder(String dateAdd) {
		this.dateAdd = dateAdd;
	}
	
}
