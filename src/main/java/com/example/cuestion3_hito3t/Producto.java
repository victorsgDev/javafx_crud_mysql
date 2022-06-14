package com.example.cuestion3_hito3t;


public class Producto {
    private int idproducto;
    private String nombre;
    private String fecha_envasado;
    private int unidades;
        private double precio;
    private boolean disponible;



    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_envasado() {
        return fecha_envasado;
    }

    public void setFecha_envasado(String fecha_envasado) {
        this.fecha_envasado = fecha_envasado;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
