import { useParams } from 'react-router-dom';
import ProductDetail from '../components/ProductDetail.jsx';
import { useCartContext } from '../context/CartContext.jsx';
import { useProduct } from '../hooks/useProduct.js';

export default function ProductDetailPage() {
  const { id } = useParams();
  const { product, loading, error, reload } = useProduct(id);
  const { addToCart, actionLoading } = useCartContext();

  return (
    <>
      <h1 className="page-title">Detalle del producto</h1>
      <ProductDetail
        product={product}
        loading={loading}
        error={error}
        onRetry={reload}
        onAddToCart={addToCart}
        addingToCart={actionLoading}
      />
    </>
  );
}
