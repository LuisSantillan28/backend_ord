package com.practica.empresa

import grails.gorm.transactions.Transactional

@Transactional
class FuncionesService {

    def mensajeParametroObligatorio = "Falta el parámetro '#'."
    def mensajeParametroInvalido = "El parámetro '#' es inválido."
    
    def getMsjObligatorio( campo ){
        return mensajeParametroObligatorio.replace("#", campo)
    }
    def getMsjInvalido( campo ){
        return mensajeParametroInvalido.replace("#", campo)
    }

    def esEmail( text ) {
        def pattern = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/
        return text ==~ pattern
    }
    def esTelefono( text ){
        def pattern = /^[0-9]{10}$/
        return text ==~ pattern
    }
    def esCodigoPostal( text ) {
        def pattern = /^[0-9]{5}$/
        return text ==~ pattern
    }
    def esCurp( text ) {
        def pattern = /^[a-zA-Z0-9]{18}$/
    }
}
