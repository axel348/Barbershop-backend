import AdminPanel from '../components/AdminPanel.jsx';
import { useProductsContext } from '../context/ProductsContext.jsx';

export default function AdminPage() {
  const { products, loading, error, reload } = useProductsContext();

  return (
    <>
      <h1 className="page-title">Panel de administración</h1>
      <p className="page-subtitle">
        CRUD de productos vía <code>/bff/admin/products</code>
      </p>
      <AdminPanel
        products={products}
        loading={loading}
        error={error}
        onProductsChanged={reload}
      />
    </>
  );
}
