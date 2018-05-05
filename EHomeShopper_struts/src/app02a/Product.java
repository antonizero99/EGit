package app02a;

import com.opensymphony.xwork2.ActionSupport;

public class Product extends  ActionSupport{

	private String productName, description, price;
	
	
	
	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
}
