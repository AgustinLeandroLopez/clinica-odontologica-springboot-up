window.addEventListener('load', function () {
  const form = document.getElementById('form_odontologo');
  form.addEventListener('submit', function (event) {
    event.preventDefault();

    const formData = {
      nombre: document.getElementById('nombre').value,
      apellido: document.getElementById('apellido').value,
      matricula: parseInt(document.getElementById('matricula').value)
    };

    const url = '/odontologo';
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
          throw new Error('Error al registrar odontologo');
        }
      })
      .then(data => {
        const responseDiv = document.getElementById('response');
        responseDiv.style.display = 'block';
        responseDiv.className = 'alert alert-success';
        responseDiv.innerText = `Odontologo registrado correctamente`;
        form.reset();
      })
      .catch(error => {
        const responseDiv = document.getElementById('response');
        responseDiv.style.display = 'block';
        responseDiv.className = 'alert alert-danger';
        responseDiv.innerText = 'Error al registrar el odontologo.';
        console.error(error);
      });
  });
});
