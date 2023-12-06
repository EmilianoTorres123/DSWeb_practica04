/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.jsfsample1;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegistroServlet")
public class registro extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PreparedStatement pstmt = null;

        try {
            conexion cx=conexion.getConexion();

            String usuario = request.getParameter("nombre");
            String email = request.getParameter("email");
            String contrasena = request.getParameter("contrasena");
            String sql = "INSERT INTO usuario (nombre, email, contrasena) VALUES (?, ?, ?)";
            pstmt = cx.con.prepareStatement(sql);
            pstmt.setString(1, usuario);
            pstmt.setString(2, email);
            pstmt.setString(3, contrasena);
            pstmt.executeUpdate();
           response.sendRedirect("index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

