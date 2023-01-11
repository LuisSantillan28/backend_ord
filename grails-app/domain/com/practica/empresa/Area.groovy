package com.practica.empresa
import java.util.UUID

class Area {
    String uuid = UUID.randomUUID().toString().replaceAll('\\-','')
    static hasMany = [ empleados: Empleado ]

    String nombre
    int estatus = 1 // 1-activo 2-inactivo 3-eliminado

    static constraints = {
        uuid unique:true
        empleados nullable: true, blank: true
    }

    static mapping = {
        version false
    }
}
