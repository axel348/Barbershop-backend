/**
 * Punto de entrada del paquete NPM @barbershop/barber-shop-ui
 * Importar estilos en la app consumidora:
 *   import '@barbershop/barber-shop-ui/styles.css';
 */

import './styles/index.css';
import './styles/components.css';

export { default as Navbar } from './components/Navbar.jsx';
export { default as ProductList } from './components/ProductList.jsx';
export { default as ProductCard } from './components/ProductCard.jsx';
export { default as ProductDetail } from './components/ProductDetail.jsx';
export { default as LoginForm } from './components/LoginForm.jsx';
export { default as RegisterForm } from './components/RegisterForm.jsx';

export { default as App } from './App.jsx';

export * from './services/productApi.js';
export * from './services/authApi.js';
export { apiRequest, ApiError, getApiBaseUrl } from './services/apiClient.js';

export { AuthProvider } from './context/AuthContext.jsx';
export { useProducts } from './hooks/useProducts.js';
export { useProduct } from './hooks/useProduct.js';
export { useAuth } from './hooks/useAuth.js';

export {
  PRODUCT_CATEGORIES,
  formatCategory,
  formatPrice,
} from './constants/categories.js';
