package com.practica.empresa


import grails.rest.*
import grails.converters.*

import java.util.regex.Matcher
import java.util.regex.Pattern

class EmpleadoController {
	static responseFormats = ['json', 'xml']
	def EmpleadoService, FuncionesService

    def paginar() {
        if( !params.sort ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("sort")] as JSON )
            return
        }
        if( params.sort != "nombre" && params.sort != "noEmpleado" && params.sort != "edad" && params.sort != "direccion" && params.sort != "fechaIngreso" && params.sort != "estatus" ) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("sort")] as JSON )
            return
        }

        if( !params.order ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("order")] as JSON )
            return
        }
        if( params.order != "asc" && params.order != "desc" ) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("order")] as JSON )
            return
        }

        if( !params.page ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("page")] as JSON )
            return
        }
        try {
            if( params.page.toInteger() < 1 ) {
                render( [success:false, mensaje: FuncionesService.getMsjInvalido("page")] as JSON )
                return
            }
        }catch(e) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("page")] as JSON )
            return
        }

        if( params.max ) {
            try {
                if( params.max.toInteger() < 1 ) {
                    render( [success:false, mensaje: FuncionesService.getMsjInvalido("max")] as JSON )
                    return
                }
            } catch(e) {
                render( [success:false, mensaje: FuncionesService.getMsjInvalido("max")] as JSON )
                return
            }
        }
        
        render( EmpleadoService.paginar( params ) as JSON )
    }
    def gestionar() {
        def data = request.JSON
        if( !data.areas ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("areas")] as JSON )
            return
        }
        if( !data.nombre ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("nombre")] as JSON )
            return
        }
        if( data.nombre.size() < 3 ) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("nombre")] as JSON )
            return
        }

        if( !data.paterno ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("paterno")] as JSON )
            return
        }
        if( data.paterno.size() < 3 ) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("paterno")] as JSON )
            return
        }

        if( data.materno && data.materno.size() < 3 ) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("materno")] as JSON )
            return
        }

        try {
            if( !data.fechaNacimiento ) {
                render( [success:false, mensaje: FuncionesService.getMsjObligatorio("fechaNacimiento")] as JSON )
                return
            }else{
                data.fechaNacimiento = new Date(data.fechaNacimiento)
            }
        }catch(e) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("fechaNacimiento")] as JSON )
            return
        }
        

        if( !data.calle ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("calle")] as JSON )
            return
        }
        if( data.calle.size() < 3 ) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("calle")] as JSON )
            return
        }

        if( !data.exterior ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("exterior")] as JSON )
            return
        }

        if( !data.cp ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("cp")] as JSON )
            return
        }
        if( !FuncionesService.esCodigoPostal(data.cp) ) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("cp")] as JSON )
            return
        }

        if( !data.colonia ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("colonia")] as JSON )
            return
        }

        if( !data.telefono ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("telefono")] as JSON )
            return
        }
        if( !FuncionesService.esTelefono(data.telefono) ) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("telefono")] as JSON )
            return
        }
        if( !data.correo ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("correo")] as JSON )
            return
        }
        if( !FuncionesService.esEmail(data.correo) ) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("correo")] as JSON )
            return
        }
        if( !data.curp ){
            render( [ success:false, mensaje: FuncionesService.getMsjObligatorio("curp")] as JSON )
        }
        if( !FuncionesService.esCurp(data.curp) ){
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("curp")] as JSON )
        }
        render( EmpleadoService.gestionar( data, params.uuid ) as JSON )
        if(!data.salario){
            render( [success: false, mensaje: FuncionesService.getMsjObligatorio("salario")] as JSON )
        }
    }
    def actualizarEstatus() {
        if( !params.estatus ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("estatus")] as JSON )
            return
        }
        try {
            if( params.estatus.toInteger() < 1 || params.estatus.toInteger() > 3 ) {
                render( [success:false, mensaje: FuncionesService.getMsjInvalido("estatus")] as JSON )
                return
            }
        }catch(e) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("estatus")] as JSON )
            return
        }
        render( EmpleadoService.actualizarEstatus( params.estatus.toInteger(), params.uuid ) as JSON )
    }
    def informacion() {
        render( EmpleadoService.informacion( params.uuid ) as JSON ) 
    }
}
