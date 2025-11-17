window.addEventListener('load', function () {
    const tablaTurnos = document.querySelector('#turnoTableBody');
    const updateForm = document.querySelector('#update_turno_form');
    const divUpdating = document.querySelector('#div_turno_updating');
    const btnCancelar = document.querySelector('#cancelarEdicion');

    //Cargar turnos existentes
    fetch('/turno')
        .then(response => response.json())
        .then(data => {
            const turnos = Array.isArray(data) ? data : (data ? [data] : []);
            turnos.forEach(turno => {
                let row = tablaTurnos.insertRow();
                row.innerHTML = `
                    <td>${turno.id ?? ''}</td>
                    <td>${turno.fecha ?? ''}</td>
                    <td>${turno.pacienteId ?? ''}</td>
                    <td>${turno.odontologoId ?? ''}</td>
                    <td>
                        <button class="btn btn-sm btn-warning" onclick="editarTurno(${turno.id})">Editar</button>
                    </td>
                `;
            });
        });

    // Mostrar turno seleccionado
    window.editarTurno = function (id) {
        fetch(`/turno/id/${id}`)
            .then(response => response.json())
            .then(turno => {
                divUpdating.style.display = 'block';
                document.querySelector('#turno_id').value = turno.id ?? '';
                document.querySelector('#fecha').value = turno.fecha ?? '';
                document.querySelector('#paciente_id').value = turno.pacienteId ?? '';
                document.querySelector('#odontologo_id').value = turno.odontologoId ?? '';
            });
    };

    //Actualización
    updateForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const id = document.querySelector('#turno_id').value;
        const formData = {
            fecha: document.querySelector('#fecha').value,
            odontologoId: parseInt(document.querySelector('#odontologo_id').value)
        };
        console.log(" Enviando PUT:", formData);

        fetch(`/turno/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                alert(data.mensaje || "Turno actualizado correctamente");
                location.reload();
            })
            .catch(error => console.error('Error al actualizar:', error));
    });

    // 🔹 Botón Cancelar
    btnCancelar.addEventListener('click', function () {
        divUpdating.style.display = 'none';
        updateForm.reset();
        document.querySelector('#turno_id').value = '';
        window.scrollTo({ top: 0, behavior: 'smooth' });
    });
});
