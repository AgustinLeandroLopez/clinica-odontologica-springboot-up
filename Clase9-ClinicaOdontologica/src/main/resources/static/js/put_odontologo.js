window.addEventListener('load', function () {
    const tablaOdontologos = document.querySelector('#odontologoTableBody');
    const updateForm = document.querySelector('#update_odontologo_form');
    const divUpdating = document.querySelector('#div_odontologo_updating');
    const btnCancelar = document.querySelector('#cancelarEdicion');

    //Cargar odontologos existentes
    fetch('/odontologo')
        .then(response => response.json())
        .then(data => {
            data.forEach(odontologo => {
                let row = tablaOdontologos.insertRow();
                row.innerHTML = `
                    <td>${odontologo.id}</td>
                    <td>${odontologo.nombre}</td>
                    <td>${odontologo.apellido}</td>
                    <td>${odontologo.matricula}</td>
                    <td><button class="btn btn-sm btn-warning" onclick="editarOdontologo(${odontologo.id})">Editar</button></td>
                `;
            });
        });

    //Mostrar odontologo seleccionado en el formulario
    window.editarOdontologo = function (id) {
        fetch(`/odontologo/${id}`)
            .then(response => response.json())
            .then(odontologo => {
                divUpdating.style.display = 'block';
                document.querySelector('#odontologo_id').value = odontologo.id;
                document.querySelector('#nombre').value = odontologo.nombre;
                document.querySelector('#apellido').value = odontologo.apellido;
            });
    };

    //Actualización
    updateForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const id = document.querySelector('#odontologo_id').value;
        const formData = {
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
        };


        fetch(`/odontologo/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                alert(data.mensaje || "Odontologo actualizado correctamente");
                location.reload();
            })
            .catch(error => console.error('Error:', error));
    });
        //Botón Cancelar Edición
        btnCancelar.addEventListener('click', function () {
            divUpdating.style.display = 'none';
            updateForm.reset();
            document.querySelector('#odontologo_id').value = '';
            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
});

