window.addEventListener('load', function () {
    const tablaPacientes = document.querySelector('#pacienteTableBody');
    const updateForm = document.querySelector('#update_paciente_form');
    const divUpdating = document.querySelector('#div_paciente_updating');
    const btnCancelar = document.querySelector('#cancelarEdicion');

    //Cargar pacientes existentes
    fetch('/paciente')
        .then(response => response.json())
        .then(data => {
            data.forEach(paciente => {
                let row = tablaPacientes.insertRow();
                row.innerHTML = `
                    <td>${paciente.id}</td>
                    <td>${paciente.nombre}</td>
                    <td>${paciente.apellido}</td>
                    <td>${paciente.numeroContacto}</td>
                    <td>${paciente.fechaIngreso}</td>
                    <td>${paciente.email}</td>
                    <td><button class="btn btn-sm btn-warning" onclick="editarPaciente(${paciente.id})">Editar</button></td>
                `;
            });
        });

    //Mostrar paciente seleccionado en el formulario
    window.editarPaciente = function (id) {
        fetch(`/paciente/id/${id}`)
            .then(response => response.json())
            .then(paciente => {
                divUpdating.style.display = 'block';
                document.querySelector('#paciente_id').value = paciente.id;
                document.querySelector('#nombre').value = paciente.nombre;
                document.querySelector('#apellido').value = paciente.apellido;
                document.querySelector('#numero').value = paciente.numeroContacto;
                document.querySelector('#email').value = paciente.email;
            });
    };

    //Actualización
    updateForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const id = document.querySelector('#paciente_id').value;
        const formData = {
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            numeroContacto: document.querySelector('#numero').value,
            email: document.querySelector('#email').value
        };


        fetch(`/paciente/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                alert(data.mensaje || "Paciente actualizado correctamente");
                location.reload();
            })
            .catch(error => console.error('Error:', error));
    });
        //Botón Cancelar Edición
        btnCancelar.addEventListener('click', function () {
            divUpdating.style.display = 'none';
            updateForm.reset();
            document.querySelector('#paciente_id').value = '';
            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
});

