package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import models.*;

abstract public class DA {

	private Statement st;
	
	public static void main(String arg[]) {
//		DA da = new DA();
//		da.dataAccess();
	}

	private void initialConnection() throws SQLException, ClassNotFoundException{

		//1. load driver
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
		//2. connection DB
		String username = "sa";
		String password = "admin";
		Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost; databaseName=SNT", username, password); 
		
		//3. statement
		st = con.createStatement();
	}
	
	private ResultSet DQL(String sql) throws SQLException, ClassNotFoundException {

		initialConnection();
		
		//4. execute
		ResultSet rs = st.executeQuery(sql);
		
		return rs;
	}
	
	private int DML(String sql) throws SQLException, ClassNotFoundException {

		initialConnection();
		
		//4. execute
		int noofRow = st.executeUpdate(sql);
		
		return noofRow;
	}
	
	public ArrayList<Product> DQLProduct (String sql) throws SQLException, ClassNotFoundException {
		
		ArrayList<Product> lstProd = new ArrayList<>();
		ResultSet rs = DQL(sql);
		
//		rs.getMetaData().getcol
		
		while (rs.next()) {
			Product prod = new Product();
			
			prod.setId(rs.getInt("ProductID"));
			prod.setName(rs.getString("ProductName"));
			prod.setPrice(rs.getLong("UnitPrice"));
			prod.setBrand(rs.getString("CompanyName"));
			prod.setCats(rs.getString("categoryName"));
			prod.setQtyLeft(rs.getInt("UnitsInStock"));
			prod.setQtyOnOrder(rs.getInt("UnitsOnOrder"));
			prod.setDisCon(rs.getBoolean("Discountinued"));
			prod.setDateCreate(rs.getString("CreateDate"));
			prod.setHilightDetail1(rs.getString("HilightDetail1"));
			prod.setHilightDetail2(rs.getString("HilightDetail2"));
			prod.setHilightDetail3(rs.getString("HilightDetail3"));
			prod.setHilightDetail4(rs.getString("HilightDetail4"));
			prod.setHilightDetail5(rs.getString("HilightDetail5"));
			
			lstProd.add(prod);
		}
		
		return lstProd;
	}
	
	public ArrayList<Category> DQLCats(String sql)  throws SQLException, ClassNotFoundException {
		
		ArrayList<Category> lstCat = new ArrayList<>();
		
		ResultSet rs = DQL(sql);
		while (rs.next()) {
			Category cat = new Category();
			
			cat.setId(rs.getInt("CategoriesID"));
			cat.setName(rs.getString("CategoryName"));
			cat.setDiscription(rs.getString("Description"));
			
			lstCat.add(cat);
		}
		
		return lstCat;
	}
	
	public ArrayList<Brand> DQLBrands(String sql)  throws SQLException, ClassNotFoundException {
		
		ArrayList<Brand> lstBrand = new ArrayList<>();
		
		ResultSet rs = DQL(sql);
		while (rs.next()) {
			Brand brand = new Brand();
			
			brand.setId(rs.getInt("SupplierID"));
			brand.setName(rs.getString("CompanyName"));
			brand.setAddress(rs.getString("Address"));
			brand.setCity(rs.getString("City"));
			brand.setCountry(rs.getString("Country"));
			brand.setPhone(rs.getString("Phone"));
			brand.setHomepage(rs.getString("HomePage"));
			
			lstBrand.add(brand);
		}
		
		return lstBrand;
	}
	
	abstract public ArrayList<Product> queryProduct(String sql) throws SQLException, ClassNotFoundException;
	abstract public ArrayList<Category> queryCat(String sql) throws SQLException, ClassNotFoundException;
	abstract public ArrayList<Brand> queryBrand(String sql) throws SQLException, ClassNotFoundException;
	abstract public int saveToCart(int userID, Product prod) throws SQLException, ClassNotFoundException;

}