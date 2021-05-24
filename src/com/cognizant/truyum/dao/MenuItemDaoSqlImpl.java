package com.cognizant.truyum.dao;
import java.sql.Date;

import java.sql.*;
import java.util.*;

import com.cognizant.truyum.model.MenuItem;

//import java.util.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.PreparedStatement;
//import com.cognizant.truyum.model.MenuItem;

public class MenuItemDaoSqlImpl implements MenuItemDao {

public ArrayList<MenuItem> getMenuItemListAdmin() {
ArrayList<MenuItem> menuItemlist = new ArrayList<MenuItem>();
Connection con = ConnectionHandler.getConnection();
ResultSet rs = null;
try {
// PreparedStatement stmt=con.prepareStatement("select * from menu_item");
Statement stmt = con.createStatement();
rs = stmt.executeQuery("select * from MenuItems");
while (rs.next()) {
long id = rs.getLong("id");
String name = rs.getString("name");
float price = rs.getFloat("price");
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
MenuItem m1 = new MenuItem(id,name,category, price, act, free,date1);
menuItemlist.add(m1);
}
} catch (SQLException e) {

e.printStackTrace();
}

return menuItemlist;

}

@Override
public ArrayList<MenuItem> getMenuItemListCustomer() {

ArrayList<MenuItem> menuItemListCust = new ArrayList<MenuItem>();
Connection con = ConnectionHandler.getConnection();
final String Query = "select * from MenuItems where active='Yes' AND dateOfLaunch < '2018-12-02'";
try {
// PreparedStatement stmt = con.prepareStatement(Query);
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(Query);
while (rs.next()) {
long id = rs.getLong("id");
String name = rs.getString("name");
float price = rs.getFloat("price");
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
//MenuItem m1 = new MenuItem(id, name, price, act, date1, category, free);
MenuItem m1 = new MenuItem(id,name,category, price, act, free,date1);
menuItemListCust.add(m1);
}

} catch (SQLException e) {

e.printStackTrace();
}

return menuItemListCust;

}

@Override
public void modifyMenuItem(MenuItem menuItem) {
Connection con = ConnectionHandler.getConnection();
long id = menuItem.getId();
String name = menuItem.getName();
float price = menuItem.getPrice();
boolean active = menuItem.isActive();

Date d = new Date(menuItem.getDateOfLaunch().getTime());
String category = menuItem.getCategory();
boolean free = menuItem.isFreeDelivery();
String act;
if (active == true) {
act = "Yes";
} else {
act = "No";
}
String fd;
if (free == true) {
fd = "Yes";
} else {
fd = "No";
}
final String query = "update MenuItems" + "name = " + name + ",price = " + price + " active = "
+ act + ",dateOfLaunch = "+ d +",category = "+ category +",freeDelivery = "+ fd + "where id = "+id;
try {
Statement stmt = con.createStatement();
// stmt.setString(1, name);
// stmt.setFloat(2, price);
// stmt.setString(3, act);
// stmt.setDate(4, d);
// stmt.setString(5,category);
// stmt.setString(6, fd);
// stmt.setLong(7, id);
stmt.executeUpdate(query);

} catch (SQLException e) {
e.printStackTrace();
}

}


@Override
public MenuItem getMenuItem(long menuItemId) {
Connection con = ConnectionHandler.getConnection();
MenuItem m1 = null;
final String query = "select * from MenuItems where id= " + menuItemId;
MenuItem m = null;
try {

// PreparedStatement stmt = con.prepareStatement(query);
Statement stmt = con.createStatement();
// stmt.setLong(1, menuItemId);
ResultSet rs = stmt.executeQuery(query);
while (rs.next()) {
long id = rs.getLong("id");
String name = rs.getString("name");
float price = rs.getFloat("price");
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
//m = new MenuItem(id, name, price, act, date1, category, free);
 m = new MenuItem(id,name,category, price, act, free,date1);
// return m;
}

} catch (SQLException e) {

e.printStackTrace();
}
return m;
}
}
