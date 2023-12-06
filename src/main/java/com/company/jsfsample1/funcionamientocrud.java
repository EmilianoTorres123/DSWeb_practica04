/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.company.jsfsample1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author aaron-emiliano
 */
@WebServlet("/Guardar")
public class funcionamientocrud  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conexion cx = conexion.getConexion(); // Reemplaza ConexionDB con tu clase de conexión existente

            String action = request.getParameter("action");

            if (action != null && action.equals("guardar")) {
                // Insertar un nuevo registro
                int clave = Integer.parseInt(request.getParameter("clave"));
                String nombre = request.getParameter("name");
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");

                String sqlT = "SELECT * FROM empleado WHERE clave=?";
                pstmt = cx.con.prepareStatement(sqlT);
                pstmt.setInt(1, clave);
                ResultSet resultSet = pstmt.executeQuery();
                if (!resultSet.next()) {
                    String sql = "INSERT INTO empleado (clave, nombre, direccion, telefeno) VALUES (?, ?, ?, ?)";
                    pstmt = cx.con.prepareStatement(sql);
                    pstmt.setInt(1, clave);
                    pstmt.setString(2, nombre);
                    pstmt.setString(3, direccion);
                    pstmt.setString(4, telefono);

                    pstmt.executeUpdate();
                } else {
                    String sql = "UPDATE empleado SET nombre = ?, direccion = ?, telefeno = ? WHERE clave = ?";
                    pstmt = cx.con.prepareStatement(sql);

                    pstmt.setString(1, nombre);
                    pstmt.setString(2, direccion);
                    pstmt.setString(3, telefono);
                    pstmt.setInt(4, clave);
                    pstmt.executeUpdate();
                }
            } else if (action != null && action.equals("eliminar")) {
                // Eliminar un registro existente
                int claveEliminar = Integer.parseInt(request.getParameter("claveEliminar"));

                String sql = "DELETE FROM empleado WHERE clave=?";
                pstmt = cx.con.prepareStatement(sql);
                pstmt.setInt(1, claveEliminar);

                pstmt.executeUpdate();
            }

            response.sendRedirect("alta.xhtml?faces-redirect=true");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

