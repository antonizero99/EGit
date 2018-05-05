package models;

public class Product {
	
	private int id;
	private long price;
	private String name, cats, brand;
	private int qtyLeft, qtyOnOrder;
	private boolean isDisCon;
	private String hilightDetail1, hilightDetail2, hilightDetail3, hilightDetail4, hilightDetail5;
	private String dateCreate;
	
	
	public String getDateCreate() {
		return dateCreate;
	}
	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCats() {
		return cats;
	}
	public void setCats(String cats) {
		this.cats = cats;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getQtyLeft() {
		return qtyLeft;
	}
	public void setQtyLeft(int qtyLeft) {
		this.qtyLeft = qtyLeft;
	}
	public int getQtyOnOrder() {
		return qtyOnOrder;
	}
	public void setQtyOnOrder(int qtyOnOrder) {
		this.qtyOnOrder = qtyOnOrder;
	}
	public boolean isDisCon() {
		return isDisCon;
	}
	public void setDisCon(boolean isDisCon) {
		this.isDisCon = isDisCon;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHilightDetail1() {
		return hilightDetail1;
	}
	public void setHilightDetail1(String hilightDetail1) {
		this.hilightDetail1 = hilightDetail1;
	}
	public String getHilightDetail2() {
		return hilightDetail2;
	}
	public void setHilightDetail2(String hilightDetail2) {
		this.hilightDetail2 = hilightDetail2;
	}
	public String getHilightDetail3() {
		return hilightDetail3;
	}
	public void setHilightDetail3(String hilightDetail3) {
		this.hilightDetail3 = hilightDetail3;
	}
	public String getHilightDetail4() {
		return hilightDetail4;
	}
	public void setHilightDetail4(String hilightDetail4) {
		this.hilightDetail4 = hilightDetail4;
	}
	public String getHilightDetail5() {
		return hilightDetail5;
	}
	public void setHilightDetail5(String hilightDetail5) {
		this.hilightDetail5 = hilightDetail5;
	}
}
