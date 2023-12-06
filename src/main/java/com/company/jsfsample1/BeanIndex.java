/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.company.jsfsample1;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author zurisaddairj
 */
@Named(value = "beanIndex")
@SessionScoped
public class BeanIndex implements Serializable {

    /**
     * Creates a new instance of BeanIndex
     */
    private String clave;
    private String nombre;
    private String direccion;
    private String telefono;

    private int claveInt = 0;

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

    public BeanIndex() {
    }

    public void incrementar() {
        claveInt++;
        clave = String.valueOf(claveInt);
    }

    public void guardar() {
        addMessage(FacesMessage.SEVERITY_INFO, "Atenciòn...", "Se guardo...");
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

}
