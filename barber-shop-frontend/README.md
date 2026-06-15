# Barber Shop Frontend (`@barbershop/barber-shop-ui`)

Frontend en **React + Vite** para la tienda de productos de barbería. Consume **únicamente** el BFF `barber-shop-bff` en `http://localhost:8080`; no llama a `product-service` ni `user-service` directamente.

## Tecnologías

- React 18
- Vite 5
- React Router DOM 6
- Fetch API con cliente centralizado (`apiClient.js`)

## Requisitos previos

1. **BFF en ejecución** (`barber-shop-bff`, puerto **8080**).
2. Microservicios detrás del BFF (product-service, user-service) según la documentación del monorepo.
3. Node.js 18+ y npm.

El BFF permite CORS desde `http://localhost:3000`. Este proyecto arranca Vite en el puerto **3000** para coincidir con esa configuración.

## Instalación

```bash
cd barber-shop-frontend
npm install
```

Opcional: copia variables de entorno.

```bash
cp .env.example .env
```

## Ejecución (app de desarrollo)

```bash
npm run dev
```

Abre [http://localhost:3000](http://localhost:3000).

## Build

| Comando | Uso |
|---------|-----|
| `npm run build` | App standalone → carpeta `dist-app/` |
| `npm run build:lib` | Paquete NPM → carpeta `dist/` |
| `npm run preview` | Vista previa del build de app |
| `npm run preview:netlify` | Vista previa del build `dist-app/` |

## Despliegue en Netlify

### Opción A — Desde GitHub (recomendado)

1. Sube el repo a GitHub.
2. En [app.netlify.com](https://app.netlify.com) → **Add new site** → **Import from Git**.
3. Configuración:
   - **Base directory:** `barber-shop-frontend`
   - **Build command:** `npm run build`
   - **Publish directory:** `barber-shop-frontend/dist-app`
4. **Environment variables** (Site settings → Environment variables):
   - `VITE_API_BASE_URL` = URL pública de tu BFF (ej. Render/Railway)
5. Deploy.

### Opción B — CLI (manual)

```powershell
cd barber-shop-frontend
npm install
npm run build
npx netlify-cli login
npx netlify-cli init
npx netlify-cli deploy --prod --dir=dist-app
```

### Importante: backend en producción

Netlify solo hospeda el **frontend estático**. Para que productos y login funcionen en internet, el **BFF** (y microservicios) deben estar desplegados en otro servicio (Render, Railway, etc.) y la variable `VITE_API_BASE_URL` debe apuntar a esa URL.

El archivo `netlify.toml` y `public/_redirects` ya configuran las rutas de React Router.

## Endpoints consumidos (BFF)

| Método | Ruta BFF | Servicio frontend |
|--------|----------|-------------------|
| GET | `/bff/products` | `productApi.fetchProducts()` |
| GET | `/bff/products/{id}` | `productApi.fetchProductById(id)` |
| GET | `/bff/products/category/{category}` | `productApi.fetchProductsByCategory(category)` |
| POST | `/bff/auth/login` | `authApi.login({ email, password })` |
| POST | `/bff/auth/register` | `authApi.register({ name, email, password, role })` |

Todas las respuestas exitosas siguen el envoltorio del BFF:

```json
{
  "success": true,
  "message": "…",
  "data": { }
}
```

El cliente en `src/services/apiClient.js` valida `success` y lanza `ApiError` con el mensaje del BFF en errores HTTP o lógicos.

## Conexión con el BFF

```
┌─────────────────┐     HTTP JSON      ┌──────────────────┐     RestTemplate    ┌─────────────────┐
│  React (3000)   │ ────────────────► │ barber-shop-bff  │ ─────────────────► │ product-service │
│  barber-shop-   │  /bff/products    │   (8080)         │                    │ user-service    │
│  frontend       │  /bff/auth/*      │                  │                    │                 │
└─────────────────┘                    └──────────────────┘                    └─────────────────┘
```

- **Base URL:** `VITE_API_BASE_URL` (por defecto `http://localhost:8080`).
- **Sin tokens en catálogo:** GET de productos es público en el BFF.
- **Sesión:** tras login/registro, el usuario (`id`, `name`, `email`, `role`) se guarda en `localStorage` bajo la clave `barbershop_user`.

## Estructura del proyecto

```
barber-shop-frontend/
├── package.json
├── vite.config.js
├── index.html
├── .env.example
├── public/
│   └── favicon.svg
└── src/
    ├── main.jsx              # Entrada de la app (npm run dev)
    ├── index.js              # Entrada del paquete NPM (npm run build:lib)
    ├── App.jsx               # Rutas y layout
    ├── assets/               # Imágenes estáticas (vacío por defecto)
    ├── components/
    │   ├── Navbar.jsx
    │   ├── ProductList.jsx
    │   ├── ProductCard.jsx
    │   ├── ProductDetail.jsx
    │   ├── LoginForm.jsx
    │   └── RegisterForm.jsx
    ├── constants/
    │   └── categories.js     # Categorías y formateo de precio
    ├── context/
    │   └── AuthContext.jsx   # Estado global de sesión
    ├── hooks/
    │   ├── useProducts.js
    │   ├── useProduct.js
    │   └── useAuth.js
    ├── pages/
    │   ├── HomePage.jsx
    │   ├── ProductsPage.jsx
    │   ├── ProductDetailPage.jsx
    │   ├── LoginPage.jsx
    │   └── RegisterPage.jsx
    ├── services/
    │   ├── apiClient.js      # Cliente HTTP centralizado
    │   ├── productApi.js
    │   └── authApi.js
    └── styles/
        ├── variables.css
        ├── index.css
        └── components.css
```

## Uso como paquete NPM

Tras `npm run build:lib`, publica o enlaza el paquete localmente:

```bash
npm link
# En otro proyecto React:
npm link @barbershop/barber-shop-ui
```

Ejemplo de consumo:

```jsx
import { BrowserRouter } from 'react-router-dom';
import {
  App,
  AuthProvider,
  ProductList,
  useProducts,
} from '@barbershop/barber-shop-ui';
import '@barbershop/barber-shop-ui/styles.css';

// App completa con rutas (incluye AuthProvider):
export function BarberShopApp() {
  return <App />;
}

// Componentes sueltos (envuelve con AuthProvider si usas login):
function Catalog() {
  const { products, category, setCategory, loading, error, reload } = useProducts();
  return (
    <ProductList
      products={products}
      category={category}
      onCategoryChange={setCategory}
      loading={loading}
      error={error}
      onRetry={reload}
    />
  );
}
```

**peerDependencies:** `react`, `react-dom`, `react-router-dom`.

## Credenciales de prueba

Si el `user-service` tiene datos de ejemplo (ver `data.sql` del backend):

- Email: `juan@email.com`
- Contraseña: `cliente123`

## Pruebas (Evaluación Parcial 3)

```powershell
npm run test
npm run test:coverage
```

Reporte HTML: `coverage/index.html` (umbral mínimo 60%).

## Publicar en NPM

1. Ajusta `name` y `repository` en `package.json`.
2. `npm run build:lib`
3. `npm publish --access public` (o registro privado).

El script `prepublishOnly` ejecuta el build de librería automáticamente.

## Solución de problemas

| Problema | Solución |
|----------|----------|
| CORS bloqueado | Usa puerto 3000 (`npm run dev`) o añade tu origen en `app.cors.allowed-origins` del BFF |
| Error de conexión | Verifica que el BFF esté en `http://localhost:8080` |
| Lista vacía | Confirma que `product-service` esté arriba y el BFF responda `GET /bff/products` |
