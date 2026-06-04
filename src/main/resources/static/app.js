const output = document.getElementById('output');
const apiBase = '/api/espagueti';

function showResult(result) {
    output.textContent = JSON.stringify(result, null, 2);
}

function showError(error) {
    output.textContent = 'ERROR: ' + error;
}

async function postJson(url, body) {
    const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });
    const text = await response.text();
    return response.ok ? text : Promise.reject(text);
}

async function putJson(url, body) {
    const response = await fetch(url, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });
    const text = await response.text();
    return response.ok ? text : Promise.reject(text);
}

async function getJson(url) {
    const response = await fetch(url);
    const text = await response.text();
    return response.ok ? JSON.parse(text) : Promise.reject(text);
}

const especialidadForm = document.getElementById('especialidad-form');
especialidadForm.addEventListener('submit', async event => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(especialidadForm));
    try {
        const result = await postJson(`${apiBase}/especialidades`, data);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

const pacienteForm = document.getElementById('paciente-form');
pacienteForm.addEventListener('submit', async event => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(pacienteForm));
    try {
        const result = await postJson(`${apiBase}/pacientes`, data);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

const medicoForm = document.getElementById('medico-form');
medicoForm.addEventListener('submit', async event => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(medicoForm));
    try {
        const result = await postJson(`${apiBase}/medicos`, data);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

const turnoForm = document.getElementById('turno-form');
turnoForm.addEventListener('submit', async event => {
    event.preventDefault();
    const formData = new FormData(turnoForm);
    const body = {
        paciente_id: parseInt(formData.get('paciente_id'), 10),
        medico_id: parseInt(formData.get('medico_id'), 10),
        fecha_hora: formData.get('fecha_hora').replace('T', ' '),
        motivo_consulta: formData.get('motivo_consulta') || ''
    };
    try {
        const result = await postJson(`${apiBase}/turnos`, body);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

const cancelarForm = document.getElementById('cancelar-form');
cancelarForm.addEventListener('submit', async event => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(cancelarForm));
    const turnoId = data.id;
    const body = { razon: data.razon || 'Sin especificar' };
    try {
        const result = await putJson(`${apiBase}/turnos/${turnoId}/cancelar`, body);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

const listarTurnos = document.getElementById('listar-turnos');
listarTurnos.addEventListener('click', async () => {
    try {
        const result = await getJson(`${apiBase}/turnos`);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

const listarPacientes = document.getElementById('listar-pacientes');
listarPacientes.addEventListener('click', async () => {
    try {
        const result = await getJson(`${apiBase}/pacientes`);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

const listarMedicos = document.getElementById('listar-medicos');
listarMedicos.addEventListener('click', async () => {
    try {
        const result = await getJson(`${apiBase}/medicos`);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});
