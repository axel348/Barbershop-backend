import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar.jsx';
import { AuthProvider } from './context/AuthContext.jsx';
import { CartProvider } from './context/CartContext.jsx';
import { ProductsProvider } from './context/ProductsContext.jsx';
import { useAuth } from './hooks/useAuth.js';
import AdminPage from './pages/AdminPage.jsx';
import CartPage from './pages/CartPage.jsx';
import HomePage from './pages/HomePage.jsx';
import LoginPage from './pages/LoginPage.jsx';
import ProductDetailPage from './pages/ProductDetailPage.jsx';
import ProductsPage from './pages/ProductsPage.jsx';
import RegisterPage from './pages/RegisterPage.jsx';

function AppRoutes() {
  const { user, logout } = useAuth();

  return (
    <div className="app-layout">
      <Navbar user={user} onLogout={logout} />
      <main className="app-main">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/productos" element={<ProductsPage />} />
          <Route path="/productos/:id" element={<ProductDetailPage />} />
          <Route path="/carrito" element={<CartPage />} />
          <Route path="/admin" element={<AdminPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/registro" element={<RegisterPage />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </main>
    </div>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <ProductsProvider>
        <CartProvider>
          <BrowserRouter>
            <AppRoutes />
          </BrowserRouter>
        </CartProvider>
      </ProductsProvider>
    </AuthProvider>
  );
}
