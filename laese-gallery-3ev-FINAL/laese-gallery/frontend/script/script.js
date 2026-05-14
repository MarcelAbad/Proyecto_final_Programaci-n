/**
 * LÆSE Gallery — script.js
 * Obtiene los relojes desde la API REST de Spring Boot
 * en lugar de leer un XML local.
 *
 * Endpoint base: http://localhost:8080/api/relojes
 */

document.addEventListener('DOMContentLoaded', () => {

    // ── Configuración ──────────────────────────────────────────
    const API_BASE = 'http://localhost:8080/api/relojes';

    // ── Referencias DOM ────────────────────────────────────────
    const contenedor      = document.getElementById('contenedorTarjetas');
    const spinner         = document.getElementById('loadingSpinner');
    const inputBusqueda   = document.getElementById('inputBusqueda');
    const btnBuscar       = document.getElementById('btnBuscar');
    const selectorTema    = document.getElementById('selectorTema');
    const btnAñadir       = document.getElementById('btnAñadir');
    const formNuevoReloj  = document.getElementById('formNuevoReloj');
    const formColores     = document.getElementById('formColores');

    // Modales Bootstrap
    const modalAñadirEl     = document.getElementById('modalAñadir');
    const modalPersonalizarEl = document.getElementById('modalPersonalizar');
    const bsModalAñadir     = new bootstrap.Modal(modalAñadirEl);
    const bsModalPersonalizar = new bootstrap.Modal(modalPersonalizarEl);

    // ── Carga inicial ──────────────────────────────────────────
    cargarRelojesDesdeAPI();

    // ── Funciones principales ──────────────────────────────────

    /**
     * Llama a GET /api/relojes y renderiza las tarjetas.
     */
    async function cargarRelojesDesdeAPI() {
        spinner.style.display = 'block';
        contenedor.innerHTML  = '';

        try {
            const response = await fetch(API_BASE);
            if (!response.ok) throw new Error(`HTTP ${response.status}: ${response.statusText}`);

            const relojes = await response.json();
            spinner.style.display = 'none';

            if (relojes.length === 0) {
                contenedor.innerHTML = '<p class="text-secondary text-center w-100">No hay relojes en la colección.</p>';
                return;
            }

            relojes.forEach(reloj => crearTarjetaHTML(reloj));

        } catch (error) {
            spinner.style.display = 'none';
            console.error('Error cargando la API:', error);
            contenedor.innerHTML = `
                <div class="col-12 text-center">
                    <p class="text-danger">
                        ⚠️ No se pudo conectar con la API.<br>
                        Asegúrate de que Spring Boot está corriendo en
                        <code>http://localhost:8080</code>
                    </p>
                </div>`;
        }
    }

    /**
     * Crea y añade una tarjeta Bootstrap al grid.
     * @param {Object} reloj - { id, titulo, texto, imagen }
     */
    function crearTarjetaHTML(reloj) {
        const col = document.createElement('div');
        col.classList.add('col');

        const imagenSrc = reloj.imagen
            ? (reloj.imagen.startsWith('http') ? reloj.imagen : `http://localhost:8080/${reloj.imagen}`)
            : 'img/reloj-placeholder.png';

        col.innerHTML = `
            <div class="tarjeta-reloj h-100">
                <img src="${imagenSrc}"
                     alt="${reloj.titulo}"
                     onerror="this.src='https://placehold.co/300x300/181818/d4af37?text=LÆSE'">
                <h2>${reloj.titulo}</h2>
                <p>${reloj.texto}</p>
            </div>`;

        contenedor.appendChild(col);
    }

    // ── Búsqueda (filtra las tarjetas ya renderizadas) ─────────

    function filtrarTarjetas() {
        const query = inputBusqueda.value.toLowerCase().trim();
        document.querySelectorAll('#contenedorTarjetas .col').forEach(col => {
            const titulo = col.querySelector('h2')?.textContent.toLowerCase() ?? '';
            col.style.display = titulo.includes(query) ? '' : 'none';
        });
    }

    btnBuscar.addEventListener('click', filtrarTarjetas);
    inputBusqueda.addEventListener('input', filtrarTarjetas);
    inputBusqueda.addEventListener('keydown', e => { if (e.key === 'Enter') filtrarTarjetas(); });

    // ── Cambio de tema ─────────────────────────────────────────

    selectorTema.addEventListener('change', e => {
        const tema = e.target.value;
        document.body.classList.remove('tema-claro');

        ['--bg-body','--bg-header','--bg-footer','--text-main','--text-secondary','--accent-color']
            .forEach(prop => document.body.style.removeProperty(prop));

        if (tema === 'claro') {
            document.body.classList.add('tema-claro');
        } else if (tema === 'personalizado') {
            bsModalPersonalizar.show();
            selectorTema.value = 'oscuro';
        }
    });

    formColores.addEventListener('submit', e => {
        e.preventDefault();
        const body = document.body;
        body.style.setProperty('--bg-header', document.getElementById('colorHeader').value);
        body.style.setProperty('--bg-body',   document.getElementById('colorMain').value);
        body.style.setProperty('--bg-footer', document.getElementById('colorFooter').value);
        body.style.setProperty('--text-main', '#f5f5f5');
        body.style.setProperty('--text-secondary', '#ddd');
        body.style.setProperty('--accent-color', '#d4af37');
        bsModalPersonalizar.hide();
    });

    // ── Añadir nuevo reloj via POST /api/relojes ───────────────

    btnAñadir.addEventListener('click', () => bsModalAñadir.show());

    formNuevoReloj.addEventListener('submit', async e => {
        e.preventDefault();

        const titulo = document.getElementById('nuevoTitulo').value.trim();
        const texto  = document.getElementById('nuevoTexto').value.trim();
        const urlImg = document.getElementById('nuevaImagenUrl').value.trim();
        const fileInput = document.getElementById('nuevaImagen');

        // Determinar URL o base64 de imagen
        let imagen = urlImg;
        if (!imagen && fileInput.files && fileInput.files[0]) {
            imagen = await leerArchivoComoBase64(fileInput.files[0]);
        }

        const nuevoReloj = { titulo, texto, imagen };

        try {
            const response = await fetch(API_BASE, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevoReloj)
            });

            if (!response.ok) throw new Error(`HTTP ${response.status}`);

            const relojCreado = await response.json();
            crearTarjetaHTML(relojCreado);
            bsModalAñadir.hide();
            formNuevoReloj.reset();

            mostrarToast('Reloj añadido correctamente ✓');

        } catch (error) {
            console.error('Error al añadir reloj:', error);
            alert('No se pudo añadir el reloj. Comprueba que la API está activa.');
        }
    });

    // ── Helpers ────────────────────────────────────────────────

    function leerArchivoComoBase64(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload  = () => resolve(reader.result);
            reader.onerror = () => reject(new Error('No se pudo leer el archivo'));
            reader.readAsDataURL(file);
        });
    }

    function mostrarToast(mensaje) {
        // Toast Bootstrap dinámico
        const toastEl = document.createElement('div');
        toastEl.className = 'toast align-items-center text-bg-warning border-0 position-fixed bottom-0 end-0 m-3';
        toastEl.setAttribute('role', 'alert');
        toastEl.innerHTML = `
            <div class="d-flex">
                <div class="toast-body fw-bold">${mensaje}</div>
                <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>`;
        document.body.appendChild(toastEl);
        new bootstrap.Toast(toastEl, { delay: 3000 }).show();
        toastEl.addEventListener('hidden.bs.toast', () => toastEl.remove());
    }
});
