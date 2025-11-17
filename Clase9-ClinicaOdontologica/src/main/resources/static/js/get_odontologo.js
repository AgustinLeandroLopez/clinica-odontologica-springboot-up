window.addEventListener('load', function () {
  const url = '/odontologo';

  fetch(url)
    .then(response => {
      if (!response.ok) throw new Error('Network response was not ok: ' + response.status);
      return response.json();
    })
    .then(data => {
      // Asegurarnos de trabajar sobre el tbody
      const tableBody = document.getElementById('odontologoTableBody');
      tableBody.innerHTML = ''; // limpiar contenido previo

      // Si el endpoint devuelve un objeto y no un array, manejarlo:
      const odontologos = Array.isArray(data) ? data : (data ? [data] : []);

      odontologos.forEach(odontologo => {
        const tr = document.createElement('tr');
        tr.id = 'tr_' + (odontologo.id ?? '');

        // Usar nombres de atributos correctos (según tu modelo: numeroContacto, fechaIngreso)
        tr.innerHTML = `
          <td>${odontologo.id ?? ''}</td>
          <td class="td_nombre">${(odontologo.nombre ?? '').toString().toUpperCase()}</td>
          <td class="td_apellido">${(odontologo.apellido ?? '').toString().toUpperCase()}</td>
          <td class="td_matricula">${odontologo.matricula ?? ''}</td>
        `;
        tableBody.appendChild(tr);
      });
    })
    .catch(error => {
      console.error('Error cargando odontologos:', error);
      // Aquí puedes mostrar un alert o mensaje en la UI
    });
});
