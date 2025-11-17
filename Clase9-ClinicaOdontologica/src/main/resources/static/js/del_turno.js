window.addEventListener('load', function () {
  const form = document.getElementById('form_delete_turno');

  form.addEventListener('submit', function (event) {
    event.preventDefault();

    const id = document.getElementById('id_turno').value;

    if (!id) {
      alert('Por favor, ingresá un ID de turno.');
      return;
    }

    const url = `/turno/${id}`;
    const settings = {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' }
    };

    fetch(url, settings)
      .then(async response => {
        let data;
        try {
          data = await response.json();
        } catch (e) {
          data = await response.text();
        }

        if (response.ok) {
          return data;
        } else {
          const errorMsg = data.mensaje || data || 'Error al eliminar el turno';
          throw new Error(errorMsg);
        }
      })
      .then(data => {
        const responseDiv = document.getElementById('response');
        responseDiv.style.display = 'block';
        responseDiv.className = 'alert alert-success';
        responseDiv.innerText = data.mensaje;

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
