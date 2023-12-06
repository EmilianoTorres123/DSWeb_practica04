/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.company.jsfsample1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author zurisaddairj
 */
@Named(value = "employeeBean")
public class EmployeeBean {

    /**
     * Creates a new instance of EmployeeBean
     */
    private String clave;
    private String nombre;
    private String direccion;
    private String telefono;
    private String password;

    public ArrayList usersList;

    private int claveInt = 0;

    private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    Connection connection;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void incrementar() {
        claveInt++;
        clave = String.valueOf(claveInt);
    }

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://172.17.0.2:5432/ejemplo", "postgres", "empleado");
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
    //public void guardar() {
    //  addMessage(FacesMessage.SEVERITY_INFO, "Atenciòn...", "Se guardo...");
    //}
    
   // private static EmployeeBean Conexion=null;
    //protected Connection con=null;
    
    //public static EmployeeBean getConnection() throws NamingException{
      //  if(Conexion==null)
          //  Conexion=new EmployeeBean();
        
       // return Conexion;
   // }
    
   // private EmployeeBean() throws NamingException {
    //    try {
       //     Context initContext =new InitialContext();
          //  Context envContext = (Context) initContext.lookup("java:/comp/env");
          //  DataSource dataSource = (DataSource) envContext.lookup("jdbc/mydb");
          //  con = dataSource.getConnection();
       // } catch (SQLException ex) {
         //   Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
       // }
   // }

    public String guardar() {
        int result = 0;
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "insert into empleado(nombre,direccion,password,telefeno) values(?,?,?,?)");
            stmt.setString(1, nombre);
            stmt.setString(2, direccion);
            stmt.setString(3, password);
            stmt.setString(4, telefono);
            result = stmt.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (result != 0) {
            return "index.xhtml?faces-redirect=true";
        } else {
            return "alta.xhtml?faces-redirect=true";
        }
    }

    // Used to fetch all records  
    public ArrayList usersList() {
        try {
            usersList = new ArrayList();
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from empleado");
            while (rs.next()) {
                EmployeeBean  user = new EmployeeBean ();
                user.setClave(rs.getString("clave"));
                user.setNombre(rs.getString("nombre"));
                user.setDireccion(rs.getString("direccion"));
                user.setPassword(rs.getString("password"));
                user.setTelefono(rs.getString("telefeno"));
                usersList.add(user);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return usersList;
    }
    
    
     public String edit(int id) {
        EmployeeBean user = null;
        System.out.println(id);
        try {
            connection = getConnection();
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("select * from users where id = " + (id));
            rs.next();
            user = new EmployeeBean();
            user.setClave(rs.getString("clave"));
            user.setNombre(rs.getString("nombre"));
            user.setDireccion(rs.getString("direccion"));
            user.setTelefono(rs.getString("telefono"));
            user.setPassword(rs.getString("password"));
            System.out.println(rs.getString("password"));
            sessionMap.put("editUser", user);
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/edit.xhtml?faces-redirect=true";
    }
    
     public String update(EmployeeBean u) {
//int result = 0;  
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "update empleado set nombre=?,direccion=?,password=?,telefono=? where clave=?");
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getDireccion());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getTelefono());
            stmt.setString(5, u.getClave());
            stmt.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println();
        }
        return "/index.xhtml?faces-redirect=true";
    }
// Used to delete user record  

    public void delete(int id) {
        try {
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("delete from empleado where clave = " + id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

}
