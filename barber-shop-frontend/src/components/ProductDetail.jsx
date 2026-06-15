import { Link } from 'react-router-dom';
import { formatCategory, formatPrice } from '../constants/categories.js';

function LoadingState() {
  return (
    <div className="loading-block" role="status">
      <span className="spinner" aria-hidden="true" />
      Cargando producto…
    </div>
  );
}

export default function ProductDetail({
  product,
  loading,
  error,
  onRetry,
  onAddToCart,
  addingToCart = false,
}) {
  if (loading) {
    return <LoadingState />;
  }

  if (error) {
    return (
      <div>
        <div className="alert alert-error" role="alert">
          {error}
        </div>
        {onRetry && (
          <button type="button" className="btn btn-ghost" onClick={onRetry}>
            Reintentar
          </button>
        )}
        <p style={{ marginTop: '1rem' }}>
          <Link to="/productos">← Volver al catálogo</Link>
        </p>
      </div>
    );
  }

  if (!product) {
    return (
      <p className="empty-state">
        Producto no encontrado.{' '}
        <Link to="/productos">Volver al catálogo</Link>
      </p>
    );
  }

  const { name, description, brand, category, price, stock } = product;
  const canAdd = stock > 0;

  return (
    <article className="product-detail">
      <span className="product-detail__category">{formatCategory(category)}</span>
      <h1 className="product-detail__title">{name}</h1>
      {description && <p>{description}</p>}

      <div className="product-detail__meta">
        {brand && (
          <p>
            <strong>Marca:</strong> {brand}
          </p>
        )}
        <p className="product-detail__price">{formatPrice(price)}</p>
        <p>
          <strong>Stock:</strong> {stock > 0 ? stock : 'Agotado'}
        </p>
      </div>

      <div className="product-detail__actions">
        <button
          type="button"
          className="btn btn-primary"
          disabled={!canAdd || addingToCart}
          onClick={() => onAddToCart?.(product)}
        >
          {addingToCart ? 'Agregando…' : 'Agregar al carrito'}
        </button>
        <Link to="/productos" className="btn btn-ghost">
          ← Volver al catálogo
        </Link>
      </div>
    </article>
  );
}
