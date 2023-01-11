package empresa

class UrlMappings {

    static mappings = {

        group "/ordenaris/api", {
            group "/area", {
                "/paginar"(controller: "area", action: "paginar", method: "GET")
                "/lista"(controller: "area", action: "lista", method: "GET")
                "/registrar"(controller: "area", action: "gestionar", method: "POST")
                "/$uuid/modificar"(controller: "area", action: "gestionar", method: "PUT")
                "/$uuid/actualizar-estatus"(controller: "area", action: "actualizarEstatus", method: "PATCH")
                "/$uuid/informacion"(controller: "area", action: "informacion", method: "GET")
            }
            group "/empleado", {
                "/paginar"(controller: "empleado", action: "paginar", method: "GET")
                "/registrar"(controller: "empleado", action: "gestionar", method: "POST")
                "/$uuid/modificar"(controller: "empleado", action: "gestionar", method: "PUT")
                "/$uuid/actualizar-estatus"(controller: "empleado", action: "actualizarEstatus", method: "PATCH")
                "/$uuid/informacion"(controller: "empleado", action: "informacion", method: "GET")
            }
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
