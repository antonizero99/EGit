package DAL;

import java.sql.SQLException;
import java.util.ArrayList;

import models.*;

public class GenericDAO extends DA{

	public static void main (String arg[]) {
		GenericDAO gdao = new GenericDAO();
		
		try {
			System.out.println(GenericDAO.queryCmd.selectProdWithFilter.getCmd("CompanyName", "='" + "SONY" + "'"));
			System.out.println(gdao.DQLProduct(GenericDAO.queryCmd.selectProdWithFilter.getCmd("CompanyName", "='" + "SONY" + "'")).size());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public enum queryCmd {
		selectProdAll("select * from vwProducts"),
		selectProdWithFilter("select * from vwProducts where "),
		selectProdFeature("select top 6 * from vwProducts order by CreateDate"),
		selectProdParticular("select * from vwProducts where "),
		
		selectCatAll("select * from Categories"),
		
		selectBrandAllOrder("select * from Suppliers order by CompanyName");
		
		
		String main;
		
		queryCmd(String main) {
			this.main = main;
		};
		
		public String getCmd(String col, String criteria) {
			return main + col + " " + criteria;
		}
	}

	@Override
	public ArrayList<Product> queryProduct(String sql) throws SQLException, ClassNotFoundException {
		return DQLProduct(sql);
	}

	@Override
	public ArrayList<Category> queryCat(String sql) throws SQLException, ClassNotFoundException {
		return DQLCats(sql);
	}

	@Override
	public ArrayList<Brand> queryBrand(String sql) throws SQLException, ClassNotFoundException {
		return DQLBrands(sql);
	}

	@Override
	public int saveToCart(int userID, Product prod) throws SQLException, ClassNotFoundException {
		return 0;
	};
}
