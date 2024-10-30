const url = "/v1/denuncias"; // Adjusted to match complaints context

function ajaxRequest(type, endpoint, data = null) {
    return $.ajax({
        type,
        url: endpoint,
        data: data ? JSON.stringify(data) : null,
        dataType: "json",
        contentType: data ? "application/json" : undefined,
        cache: false,
        timeout: 600000,
    });
}

function save(bandera) {
    const id = $("#guardar").data("id");
    const registro = {
        id,
        titulo: $("#titulo").val(), // Changed to match the modal field
        descripcion: $("#descripcion").val(), // Corrected spelling
        ubicacion: $("#ubicacion").val(), // Added for location
        ciudadano: $("#ciudadano").val(), // Added for citizen's name
        telefono: $("#telefono").val(), // Added for citizen's phone
        fecha: $("#fecha").val(), // Added for date
        estado: $("#estado").val() // Added for status
    };

    const type = bandera === 1 ? "POST" : "PUT";
    const endpoint = bandera === 1 ? url : `${url}/${id}`;

    ajaxRequest(type, endpoint, registro)
        .done((data) => {
            if (data.ok) {
                $("#modal-update").modal("hide");
                getTabla();
                $("#error-message").addClass("d-none");
                Swal.fire({
                    icon: 'success',
                    title: `Se ha ${bandera === 1 ? 'guardado' : 'actualizado'} la denuncia`,
                    showConfirmButton: false,
                    timer: 1500
                });
                clear();
            } else {
                showError(data.message);
            }
        }).fail(function (jqXHR) {
            let errorMessage = jqXHR.responseJSON && jqXHR.responseJSON.message ? jqXHR.responseJSON.message : "Error inesperado. Código: " + jqXHR.status;
            showError(errorMessage);
        });
}

function showError(message) {
    $("#error-message").text(message).removeClass("d-none");
}

function deleteFila(id) {
    ajaxRequest("DELETE", `${url}/${id}`)
        .done((data) => {
            if (data.ok) {
                Swal.fire({
                    icon: 'success',
                    title: 'Se ha eliminado la denuncia',
                    showConfirmButton: false,
                    timer: 1500
                });
                getTabla();
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: data.message,
                    confirmButtonText: 'Aceptar'
                });
            }
        })
        .fail(handleError);
}

function formatFecha(fecha) {
    if (!fecha) return ''; 
    const date = new Date(fecha);
    const timezoneOffset = date.getTimezoneOffset() * 60000; 
    const limaDate = new Date(date.getTime() + timezoneOffset + (5 * 60 * 60 * 1000)); 
    const year = limaDate.getFullYear();
    const month = String(limaDate.getMonth() + 1).padStart(2, '0'); 
    const day = String(limaDate.getDate()).padStart(2, '0');
    const hours = String(limaDate.getHours()).padStart(2, '0');
    const minutes = String(limaDate.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}


function getTabla() {
    ajaxRequest("GET", url)
        .done((data) => {
            const t = $("#tablaRegistros").DataTable();
            t.clear().draw(false);

            if (data.ok) {
                $.each(data.body, (index, denuncia) => {
                    const botonera = `
                        <button type="button" class="btn btn-warning btn-sm editar">
                            <i class="fas fa-edit"></i>
                        </button>
                        <button type="button" class="btn btn-danger btn-sm eliminar">
                            <i class="fas fa-trash"></i>
                        </button>`;
                    
                    // Formatear la fecha antes de agregarla a la tabla
                    const fechaFormateada = formatFecha(denuncia.fecha);
                    
                    t.row.add([botonera, denuncia.id, denuncia.titulo, denuncia.descripcion, denuncia.ubicacion, denuncia.ciudadano, denuncia.telefono, fechaFormateada, denuncia.estado]);
                });
                t.draw(false);
            } else {
                console.error("Error en la respuesta: ", data.message);
            }
        })
        .fail(handleError);
}

function setFecha(value) {
    if (value) {
        const date = new Date(value);
        const utcOffset = date.getTimezoneOffset(); // Desfase horario en minutos
        const adjustedTime = new Date(date.getTime() + (utcOffset * 60 * 1000));
        const year = adjustedTime.getFullYear();
        const month = String(adjustedTime.getMonth() + 1).padStart(2, '0'); // Meses son 0-indexados
        const day = String(adjustedTime.getDate()).padStart(2, '0');
        const hours = String(adjustedTime.getHours()).padStart(2, '0');
        const minutes = String(adjustedTime.getMinutes()).padStart(2, '0');
        const inputDateTime = `${year}-${month}-${day}T${hours}:${minutes}`;
        $("#fecha").val(inputDateTime); // Asigna el valor al campo de entrada
    }
}




function getFila(id) {
    ajaxRequest("GET", `${url}/${id}`)
        .done((data) => {
            if (data.ok) {
                $("#modal-title").text("Editar denuncia");
                $("#titulo").val(data.body.titulo);
                $("#descripcion").val(data.body.descripcion);
                $("#ubicacion").val(data.body.ubicacion);
                $("#ciudadano").val(data.body.ciudadano);
                $("#telefono").val(data.body.telefono);
                setFecha(data.body.fecha); // Aquí se establece la fecha específica, ya en el formato correcto
                $("#estado").val(data.body.estado);
                $("#guardar").data("id", data.body.id).data("bandera", 0);
                $("#modal-update").modal("show");
            } else {
                showError(data.message);
            }
        })
        .fail(handleError);
}

function clear() {
    $("#modal-title").text("Nueva denuncia");
    $("#titulo").val("");
    $("#descripcion").val("");
    $("#ubicacion").val("");
    $("#ciudadano").val("");
    $("#telefono").val("");
    $("#fecha").val("");
    $("#estado").val("");
    $("#guardar").data("id", 0).data("bandera", 1);
}

function handleError(jqXHR) {
    const errorMessage = jqXHR.responseJSON?.message || `Error inesperado. Código: ${jqXHR.status}`;
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: errorMessage,
        confirmButtonText: 'Aceptar'
    });
}

$(document).ready(function () {
    $("#tablaRegistros").DataTable({
        language: {
            lengthMenu: "Mostrar _MENU_ registros",
            zeroRecords: "No se encontraron coincidencias",
            info: "Mostrando del _START_ al _END_ de _TOTAL_ registros",
            infoEmpty: "Sin resultados",
            search: "Buscar: ",
            paginate: {
                first: "Primero",
                last: "Último",
                next: "Siguiente",
                previous: "Anterior",
            },
        },
        columnDefs: [
            { targets: 0, orderable: false }
        ],
    });

    clear();

    $("#nuevo").click(clear);
    
    $("#guardar").click(() => save($("#guardar").data("bandera")));

    $(document).on('click', '.eliminar', function () {
        const id = $(this).closest('tr').find('td:eq(1)').text();
        Swal.fire({
            title: 'Eliminar denuncia',
            text: "¿Está seguro de querer eliminar esta denuncia?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Si'
        }).then((result) => {
            if (result.isConfirmed) {
                deleteFila(id);
            }
        });
    });

    $(document).on('click', '.editar', function () {
        const id = $(this).closest('tr').find('td:eq(1)').text();
        getFila(id);
    });

    getTabla();
	
	$('#liComisaria').addClass("menu-open"); // Adjusted to reflect the correct menu
	$('#liDenucncia').addClass("active"); // Adjusted for active state
});
