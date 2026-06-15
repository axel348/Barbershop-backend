# Desplegar en Netlify — Barber Shop Frontend

## Si el BFF no funciona (error de conexión)

Netlify solo publica el **frontend**. `localhost:8080` no es accesible desde internet.

### Solución rápida con ngrok

1. Levanta backend (product 8081, user 8082, bff 8080).
2. Ejecuta: `ngrok http 8080` → copia la URL `https://xxxx.ngrok-free.app`
3. Netlify → **Environment variables** → `VITE_API_BASE_URL` = esa URL
4. **Trigger deploy** (rebuild obligatorio)
5. Prueba en navegador: `https://xxxx.ngrok-free.app/bff/products`

El BFF ya acepta CORS desde `https://*.netlify.app`.

---

El proyecto ya está configurado (`netlify.toml`, `_redirects`, build en `dist-app/`).

## Paso 1 — Iniciar sesión en Netlify

En PowerShell, dentro de `barber-shop-frontend`:

```powershell
npm run netlify:login
```

Se abrirá el navegador. Autoriza con tu cuenta Netlify (gratis).

## Paso 2 — Desplegar

```powershell
npm run netlify:deploy
```

La primera vez te preguntará:
- **Create & configure a new site** → Sí
- **Team** → tu equipo personal
- **Site name** (opcional) → ej. `barber-shop-tienda`

Al terminar verás una URL como: `https://barber-shop-tienda.netlify.app`

## Paso 3 — Variable de entorno (cuando tengas BFF en internet)

En Netlify → **Site configuration** → **Environment variables**:

| Variable | Valor |
|----------|--------|
| `VITE_API_BASE_URL` | `https://tu-bff.onrender.com` |

Luego **Trigger deploy** → **Deploy site** (rebuild necesario porque Vite embebe la variable en build).

## Solo subir la UI (sin API aún)

Puedes desplegar ya: la página cargará, pero productos/login fallarán hasta que el BFF esté público.

## Despliegue desde GitHub (automático)

1. Push a GitHub
2. Netlify → Import project
3. Base directory: `barber-shop-frontend`
4. Build: `npm run build`
5. Publish: `dist-app`

## Vista previa local del build

```powershell
npm run build
npm run preview:netlify
```

Abre http://localhost:4173
