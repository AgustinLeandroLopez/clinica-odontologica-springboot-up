window.addEventListener('load', function () {
  const form = document.getElementById('form_paciente');
  form.addEventListener('submit', function (event) {
    event.preventDefault();

    const formData = {
      nombre: document.getElementById('nombre').value,
      apellido: document.getElementById('apellido').value,
      numeroContacto: parseInt(document.getElementById('numero').value),
      fechaIngreso: document.getElementById('fecha').value,
      email: document.getElementById('email').value,
        domicilio: {
          calle: document.getElementById('calle').value,
          numero: parseInt(document.getElementById('numDomicilio').value),
          localidad: document.getElementById('localidad').value,
          provincia: document.getElementById('provincia').value
        }
    };

    const url = '/paciente';
    const settings = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formData)
    };

    fetch(url, settings)
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error('Error al registrar paciente');
        }
      })
      .then(data => {
        const responseDiv = document.getElementById('response');
        responseDiv.style.display = 'block';
        responseDiv.className = 'alert alert-success';
        responseDiv.innerText = `Paciente registrado correctamente`;
        form.reset();
      })
      .catch(error => {
        const responseDiv = document.getElementById('response');
        responseDiv.style.display = 'block';
        responseDiv.className = 'alert alert-danger';
        responseDiv.innerText = 'Error al registrar el paciente.';
        console.error(error);
      });
  });
});
