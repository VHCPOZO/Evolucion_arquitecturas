const output = document.getElementById('output');
const apiBase = '/api/v1';

function showResult(result) {
    if (output) {
        output.textContent = JSON.stringify(result, null, 2);
    }
}

function showError(error) {
    if (output) {
        output.textContent = 'ERROR: ' + error;
    }
}

async function postJson(url, body) {
    const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });
    const text = await response.text();
    if (!response.ok) return Promise.reject(text);
    try {
        return JSON.parse(text);
    } catch {
        return text;
    }
}

async function putJson(url, body) {
    const response = await fetch(url, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    });
    const text = await response.text();
    if (!response.ok) return Promise.reject(text);
    try {
        return JSON.parse(text);
    } catch {
        return text;
    }
}

async function getJson(url) {
    const response = await fetch(url);
    const text = await response.text();
    return response.ok ? JSON.parse(text) : Promise.reject(text);
}

function bindForm(formId, handler) {
    const form = document.getElementById(formId);
    if (!form) return;
    form.addEventListener('submit', handler);
}

bindForm('especialidad-form', async event => {
    event.preventDefault();
    const form = event.currentTarget;
    const data = Object.fromEntries(new FormData(form));
    try {
        const result = await postJson(`${apiBase}/especialidades`, data);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

bindForm('paciente-form', async event => {
    event.preventDefault();
    const form = event.currentTarget;
    const data = Object.fromEntries(new FormData(form));
    try {
        const result = await postJson(`${apiBase}/pacientes`, data);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

bindForm('medico-form', async event => {
    event.preventDefault();
    const form = event.currentTarget;
    const data = Object.fromEntries(new FormData(form));
    try {
        const result = await postJson(`${apiBase}/medicos`, data);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

bindForm('turno-form', async event => {
    event.preventDefault();
    const form = event.currentTarget;
    const formData = new FormData(form);
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

bindForm('cancelar-form', async event => {
    event.preventDefault();
    const form = event.currentTarget;
    const data = Object.fromEntries(new FormData(form));
    const turnoId = data.id;
    const body = { razon: data.razon || 'Sin especificar' };
    try {
        const result = await putJson(`${apiBase}/turnos/${turnoId}/cancelar`, body);
        showResult(result);
    } catch (error) {
        showError(error);
    }
});

const botonTurnos = document.getElementById('listar-turnos');
if (botonTurnos) {
    botonTurnos.addEventListener('click', async () => {
        try {
            const result = await getJson(`${apiBase}/turnos`);
            showResult(result);
        } catch (error) {
            showError(error);
        }
    });
}

const botonPacientes = document.getElementById('listar-pacientes');
if (botonPacientes) {
    botonPacientes.addEventListener('click', async () => {
        try {
            const result = await getJson(`${apiBase}/pacientes`);
            showResult(result);
        } catch (error) {
            showError(error);
        }
    });
}

const botonMedicos = document.getElementById('listar-medicos');
if (botonMedicos) {
    botonMedicos.addEventListener('click', async () => {
        try {
            const result = await getJson(`${apiBase}/medicos`);
            showResult(result);
        } catch (error) {
            showError(error);
        }
    });
}

