package com.cognizant.truyum.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.cognizant.truyum.model.Cart;
import com.cognizant.truyum.model.MenuItem;

public class CartDaoSqlImpl implements CartDao {

@Override
public void addCartItem(long userId, Long menuItemId) {
Connection con = ConnectionHandler.getConnection();
String Query = "insert into cart(userId,menuItemId) values (" + userId + "," + menuItemId + ")";
try {
Statement stmt = con.createStatement();
stmt.executeUpdate(Query);
} catch (SQLException e) {

e.printStackTrace();
}

}

@Override
public ArrayList<MenuItem> getAllCartItems(long userId) throws CartEmptyException {
Connection con = ConnectionHandler.getConnection();
ArrayList<MenuItem> menuItemList = new ArrayList<MenuItem>();

Cart cart = new Cart(0, menuItemList);
String query = "select * from MenuItems JOIN cart ON MenuItems.id = cart.menuItemId where cart.userId = "
+ userId;
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
float value = 0.00f;
while (rs.next()) {
long id = rs.getLong("id");
String name = rs.getString("name");
float price = rs.getFloat("price");
value = value + price;
String active = rs.getString("active");
Date date1 = rs.getDate("dateOfLaunch");
String category = rs.getString("category");
String free_delivery = rs.getString("freeDelivery");
boolean act = false;
if (active.equalsIgnoreCase("yes")) {
act = true;
}
boolean free = false;
if (free_delivery.equalsIgnoreCase("yes")) {
free = true;
}
MenuItem m1 = new MenuItem(id, name, category, price, act, free,date1);
menuItemList.add(m1);
}
cart.setTotal(value);
cart.setMenuItemList(menuItemList);
} catch (SQLException e) {

e.printStackTrace();
}
return menuItemList;
}

@Override
public void removeCartItem(long userId, long menuItemId) {
Connection con = ConnectionHandler.getConnection();
String query = "delete from  cart where userId = "+userId+" AND menuItemId = "+menuItemId;
try {
Statement stmt = con.createStatement();
stmt.executeUpdate(query);

} catch (SQLException e) {

e.printStackTrace();
}
}
}