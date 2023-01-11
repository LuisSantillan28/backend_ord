package com.practica.empresa

import java.util.UUID
class Empleado {
    String uuid = UUID.randomUUID().toString().replaceAll('\\-','')
    static hasMany = [ areas: Area ]
    static belongsTo = Area
    String noEmpleado
    String nombre
    String paterno
    String materno

    Boolean sexo // true - M   false - F 
    Date fechaNacimiento

    String calle
    String exterior
    String interior
    String colonia
    String cp

    String correo
    String telefono

    int estatus = 1 // 1-activo 2-inactivo 3-eliminado
    Date fechaRegistro = new Date()

    String curp
    int salario

    static constraints = {
        uuid unique: true
        noEmpleado nullable: true, blank: true, unique: true
        materno nullable: true, blank: true
        interior nullable: true, blank: true
        areas nullable: true, blank: true
        correo email: true
        curp unique: true
    }

    static mapping = {
        version false
    }

    def afterInsert(){
        Empleado.withNewSession {
            this.noEmpleado = getNoEmpleado(this.fechaNacimiento, this.sexo, this.nombre, this.paterno)
        }
    }

    def getNoEmpleado(fechaNacimiento, sexo, nombre, paterno) {
        def totalRegistros = Empleado.count()+1
        def _letraSexo = ( sexo ) ? "M" : "F"
        def codigo = "${nombre[0].toUpperCase()}${paterno[0].toUpperCase()}${fechaNacimiento.format("ddMMyy")}${_letraSexo}"
        def serie = totalRegistros.toString().padLeft(5, "0")
        return "${codigo}-${serie}"
    }
}
