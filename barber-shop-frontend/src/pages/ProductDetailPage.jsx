import { useParams } from 'react-router-dom';
import ProductDetail from '../components/ProductDetail.jsx';
import { useProduct } from '../hooks/useProduct.js';

export default function ProductDetailPage() {
  const { id } = useParams();
  const { product, loading, error, reload } = useProduct(id);

  return (
    <>
      <h1 className="page-title">Detalle del producto</h1>
      <ProductDetail
        product={product}
        loading={loading}
        error={error}
        onRetry={reload}
      />
    </>
  );
}
