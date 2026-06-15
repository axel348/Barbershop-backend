import ProductList from '../components/ProductList.jsx';
import { useCartContext } from '../context/CartContext.jsx';
import { useProducts } from '../hooks/useProducts.js';

export default function ProductsPage() {
  const { products, category, setCategory, loading, error, reload } = useProducts();
  const { addToCart, actionLoading: cartActionLoading } = useCartContext();

  return (
    <>
      <h1 className="page-title">Productos</h1>
      <p className="page-subtitle">
        Listado y filtro por categoría vía <code>GET /bff/products</code>
      </p>
      <ProductList
        products={products}
        category={category}
        onCategoryChange={setCategory}
        loading={loading}
        error={error}
        onRetry={reload}
        onAddToCart={addToCart}
        addingToCart={cartActionLoading}
      />
    </>
  );
}
