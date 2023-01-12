package com.practica.empresa

import grails.gorm.transactions.Transactional

@Transactional
class EmpleadoService {
    def paginar( data ) {
        try {
            def _max = data.max ? data.max.toInteger() : 5
            def offset = ( --data.page.toInteger() * _max.toInteger())
            def lista = []
            def total = Empleado.countByEstatusNotEqual(3)

            if( data.sort == "edad" ) {
                data.sort = "fechaNacimiento"
                data.order = ( data.order == "asc" ) ? "desc" : "asc"
            }
            if( data.sort == "direccion" ) data.sort = "calle"
            if( data.sort == "fechaIngreso" ) data.sort = "fechaRegistro"

            if( data.search ) {
                total = Empleado.withCriteria {
                    ne("estatus", 3)

                    if( data.search ) {
                        or {
                            ilike("noEmpleado", "%${data.search}%")
                            ilike("nombre", "%${data.search}%")
                            ilike("paterno", "%${data.search}%")
                            ilike("materno", "%${data.search}%")
                            ilike("calle", "%${data.search}%")
                            ilike("exterior", "%${data.search}%")
                            ilike("interior", "%${data.search}%")
                            ilike("cp", "%${data.search}%")
                            ilike("colonia", "%${data.search}%")
                            ilike("correo", "%${data.search}%")
                            ilike("telefono", "%${data.search}%")
                            ilike("curp", "%${data.search}%")
                        }
                    }

                    projections{
                        rowCount()
                    }
                }[0]
            }

            Empleado.withCriteria {
                ne("estatus", 3)

                if( data.search ) {
                    or {
                        ilike("noEmpleado", "%${data.search}%")
                        ilike("nombre", "%${data.search}%")
                        ilike("paterno", "%${data.search}%")
                        ilike("materno", "%${data.search}%")
                        ilike("calle", "%${data.search}%")
                        ilike("exterior", "%${data.search}%")
                        ilike("interior", "%${data.search}%")
                        ilike("cp", "%${data.search}%")
                        ilike("colonia", "%${data.search}%")
                        ilike("correo", "%${data.search}%")
                        ilike("telefono", "%${data.search}%")
                        ilike("curp", "%${data.search}%")
                    }
                }

                firstResult(offset)
                maxResults _max
                order( data.sort, data.order )
            }.each { _empleado ->
                def info = informacion_empleado(_empleado)
                lista.add( info )
            }
            return [ success: true, lista: lista, total: total ]
        } catch(e) {
            return [ success: false, mensaje: e.getMessage() ]
        }
    }
    def gestionar( data, uuid = null ) {
        Empleado.withTransaction { tStatus ->
            def nEmpleado
            try {
                nEmpleado = Empleado.findByUuid( uuid )
                if( !nEmpleado ) {
                    nEmpleado = new Empleado()
                }

                def areas = Area.findAllByUuidInList( data.areas )
                if( !areas ) return [ success: true, mensaje: 'No se encontro el Area' ] 
                nEmpleado.nombre = data.nombre
                nEmpleado.paterno = data.paterno
                nEmpleado.materno = data.materno

                nEmpleado.sexo = data.sexo
                nEmpleado.fechaNacimiento = data.fechaNacimiento
                
                nEmpleado.calle = data.calle
                nEmpleado.exterior = data.exterior
                nEmpleado.interior = data.interior
                nEmpleado.cp = data.cp
                nEmpleado.colonia = data.colonia

                nEmpleado.correo = data.correo
                nEmpleado.telefono = data.telefono

                nEmpleado.curp = data.curp
                nEmpleado.salario = data.salario

                def listaAreas = []
                nEmpleado.areas.each { _area ->
                    listaAreas.add( Area.get( _area.id ) )
                }

                nEmpleado.areas = []
                listaAreas.each { _area ->
                    nEmpleado.removeFromAreas( _area )
                }

                areas.each { obj ->
                    nEmpleado.addToAreas(obj)
                }
                
                nEmpleado.save( flush: true, failOnError: true )
                return [ success: true ]
            } catch(e) {
                println "${new Date()} | EmpleadoService | Gestionar | Error | ${e.getMessage()}"
                tStatus.setRollbackOnly()
                return [ success: false, mensaje: e.getMessage() ]   
            }            
        }
    }
    def actualizarEstatus( nEstatus, uuid ) {
        Empleado.withTransaction { tStatus ->
            def nEmpleado
            try {
                nEmpleado = Empleado.findByUuid( uuid )
                if( !nEmpleado ) {
                    return [ success: false, mensaje: "No se encontro el Empleado." ]
                }
                nEmpleado.estatus = nEstatus
                if( nEstatus == 3 ) {
                    def listaAreas = []
                    nEmpleado.areas.each { _area ->
                        listaAreas.add( Area.get( _area.id ) )
                    }

                    nEmpleado.areas = []
                    listaAreas.each { _area ->
                        nEmpleado.removeFromAreas( _area )
                    }
                }
                nEmpleado.save( flush: true, failOnError: true )
                return [ success: true ]
            } catch(e) {
                println "${new Date()} | EmpleadoService | actualizarEstatus | Error | ${e.getMessage()}"
                tStatus.setRollbackOnly()
                return [ success: false, mensaje: e.getMessage() ]   
            }            
        }
    }
    def informacion( uuid ) {
        def nEmpleado
        try {
            nEmpleado = Empleado.findByUuid( uuid )
            if( !nEmpleado ) {
                return [ success: false, mensaje: "No se encontro el Empleado." ]
            }

            def areas = []
            nEmpleado.areas.each { _area ->
                areas.add([
                    uuid: _area.uuid,
                    nombre: _area.nombre
                ])
            }

            def informacion =[
                uuid: nEmpleado.uuid,
                noEmpleado: nEmpleado.noEmpleado,
                nombre: nEmpleado.nombre,
                paterno: nEmpleado.paterno,
                materno: nEmpleado.materno,

                sexo: nEmpleado.sexo,
                fechaNacimiento: nEmpleado.fechaNacimiento,

                calle: nEmpleado.calle,
                exterior: nEmpleado.exterior,
                interior: nEmpleado.interior,
                cp: nEmpleado.cp,
                colonia: nEmpleado.colonia,

                correo: nEmpleado.correo,
                telefono: nEmpleado.telefono,

                estatus: nEmpleado.estatus,
                fechaRegistro: nEmpleado.fechaRegistro,
                areas: areas,

                curp: nEmpleado.curp,
                salario: nEmpleado.salario
            ]
            def salEmpleado = nEmpleado.salario

            return [ success: true, informacion: informacion, informacion: salEmpleado ]
        }catch(e) {
            println "${new Date()} | EmpleadoService | informacion | Error | ${e.getMessage()}"
            return [ success: false, mensaje: e.getMessage() ]
        }
    }

    private informacion_empleado = { _empleado ->
        def areas = []
        _empleado.areas.each { _area ->
            areas.add([
                uuid: _area.uuid,
                nombre: _area.nombre
            ])
        }
        return _empleado ? [
            uuid: _empleado.uuid,
            noEmpleado: _empleado.noEmpleado,
            nombre: "${_empleado.nombre} ${_empleado.paterno} ${_empleado.materno ?: ''}",
            direccion: "${_empleado.calle} ${_empleado.exterior} ${_empleado.interior ?: ''}, ${_empleado.colonia} ${_empleado.cp}",
            edad: (new Date() - _empleado.fechaNacimiento) / 365,
            areas: areas,
            fechaIngreso: _empleado.fechaRegistro,
            estatus: _empleado.estatus,
            curp: _empleado.curp,
            salario: _empleado.salario
        ] : [:]
    }
}
