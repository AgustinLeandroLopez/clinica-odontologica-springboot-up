window.addEventListener('load', function () {
  const url = '/turno';

  fetch(url)
    .then(response => {
      if (!response.ok) throw new Error('Network response was not ok: ' + response.status);
      return response.json();
    })
    .then(data => {
      // Asegurarnos de trabajar sobre el tbody
      const tableBody = document.getElementById('turnoTableBody');
      tableBody.innerHTML = ''; // limpiar contenido previo

      // Si el endpoint devuelve un objeto y no un array, manejarlo:
      const turnos = Array.isArray(data) ? data : (data ? [data] : []);

      turnos.forEach(turno => {
        const tr = document.createElement('tr');
        tr.id = 'tr_' + (turno.id ?? '');

        tr.innerHTML = `
          <td>${turno.id ?? ''}</td>
          <td class="td_fecha">${turno.fecha ?? ''}</td>
          <td class="td_pacienteId">${turno.pacienteId ?? ''}</td>
          <td class="td_odontologoId">${turno.odontologoId ?? ''}</td>
        `;
        tableBody.appendChild(tr);
      });
    })
    .catch(error => {
      console.error('Error cargando Turnos:', error);
    });
});