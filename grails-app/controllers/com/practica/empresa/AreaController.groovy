package com.practica.empresa


import grails.rest.*
import grails.converters.*

class AreaController {
	static responseFormats = ['json', 'xml']
    def AreaService, FuncionesService

    def paginar() {
        if( !params.sort ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("sort")] as JSON )
            return
        }
        if( params.sort != "nombre" && params.sort != "estatus" ) {
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
        
        render( AreaService.paginar( params ) as JSON )
    }
    def lista() {
        render( AreaService.lista( ) as JSON )
    }
    def gestionar() {
        def data = request.JSON
        if( !data.nombre ) {
            render( [success:false, mensaje: FuncionesService.getMsjObligatorio("nombre")] as JSON )
            return
        }
        if( data.nombre.size() < 2 ) {
            render( [success:false, mensaje: FuncionesService.getMsjInvalido("nombre")] as JSON )
            return
        }
        render( AreaService.gestionar( data, params.uuid ) as JSON )
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
        render( AreaService.actualizarEstatus( params.estatus.toInteger(), params.uuid ) as JSON )
    }
    def informacion() {
        render( AreaService.informacion( params.uuid ) as JSON ) 
    }
}
