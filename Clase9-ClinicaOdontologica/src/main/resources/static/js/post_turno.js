window.addEventListener('load', function () {
  const form = document.getElementById('form_turno');
  form.addEventListener('submit', function (event) {
    event.preventDefault();

    const formData = {
      paciente: { id: parseInt(document.getElementById('paciente').value) },
      odontologo: { id: parseInt(document.getElementById('odontologo').value) },
      fecha: document.getElementById('fecha').value
    };

    const url = '/turno';
    const settings = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formData)
    };

    // Setea la respuesta
    fetch(url, settings)
      .then(async response => {
        const data = await response.json(); // intentamos leer el JSON siempre

        if (response.ok) {
          // Caso exitoso (HTTP 200 o 201)
          return data;
        } else {
          // Caso de error
          const errorMsg = data.mensaje || data.error || 'Error al registrar el turno';
          throw new Error(errorMsg);
        }
      })
      .then(data => {
        const responseDiv = document.getElementById('response');
        responseDiv.style.display = 'block';
        responseDiv.className = 'alert alert-success';
        responseDiv.innerText = data.mensaje || 'Turno registrado correctamente.';

        form.reset();
      })
      .catch(error => {
        const responseDiv = document.getElementById('response');
        responseDiv.style.display = 'block';
        responseDiv.className = 'alert alert-danger';
        responseDiv.innerText = error.message;
        console.error('Error:', error.message);
      });
  });
});
