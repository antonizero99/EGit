package models;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeItems {

	ArrayList<Product> lstFeatureProd;
	ArrayList<Category> lstCat;
	ArrayList<Brand> lstBrand;
	
	HashMap<String, ArrayList<Product>> productByCat = new HashMap<>();

	
	public void addLstFeatureProd (Product prod) {
		lstFeatureProd.add(prod);
	}
	
	public void addLstCat (Category cat) {
		lstCat.add(cat);
	}
	
	public void addLstBrand (Brand brand) {
		lstBrand.add(brand);
	}
	
	
	
	public ArrayList<Product> getLstFeatureProd() {
		return lstFeatureProd;
	}

	public void setLstFeatureProd(ArrayList<Product> lstFeatureProd) {
		this.lstFeatureProd = lstFeatureProd;
	}

	public ArrayList<Category> getLstCat() {
		return lstCat;
	}

	public void setLstCat(ArrayList<Category> lstCat) {
		this.lstCat = lstCat;
	}

	public ArrayList<Brand> getLstBrand() {
		return lstBrand;
	}

	public void setLstBrand(ArrayList<Brand> lstBrand) {
		this.lstBrand = lstBrand;
	}

	public HashMap<String, ArrayList<Product>> getProductByCat() {
		return productByCat;
	}

	public void setProductByCat(HashMap<String, ArrayList<Product>> productByCat) {
		this.productByCat = productByCat;
	}
	
	
}
