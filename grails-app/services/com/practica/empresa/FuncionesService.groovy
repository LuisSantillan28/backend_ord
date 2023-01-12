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

    def esCodigoPostal( text ) {
        def pattern = /^[0-9]{5}$/
        return text ==~ pattern
    }
    def esEmail( text ) {
        def pattern = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/
        return text ==~ pattern
    }
    def esTelefono( text ){
        def pattern = /^[0-9]{10}$/
        return text ==~ pattern
    }
    def esCurp( text ) {
        def pattern = /^[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[HM]{1}(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}[0-9A-Z]{1}[0-9]{1}$/
        return text ==~ pattern
    }
}
