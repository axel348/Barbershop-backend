import ProductCard from './ProductCard.jsx';
import { PRODUCT_CATEGORIES } from '../constants/categories.js';

function LoadingState() {
  return (
    <div className="loading-block" role="status" aria-live="polite">
      <span className="spinner" aria-hidden="true" />
      Cargando productos…
    </div>
  );
}

export default function ProductList({
  products = [],
  category = '',
  onCategoryChange,
  loading = false,
  error = null,
  onRetry,
}) {
  if (loading) {
    return <LoadingState />;
  }

  return (
    <section aria-label="Catálogo de productos">
      <div className="product-filters">
        <label htmlFor="category-filter">Filtrar por categoría</label>
        <select
          id="category-filter"
          value={category}
          onChange={(e) => onCategoryChange?.(e.target.value)}
        >
          {PRODUCT_CATEGORIES.map((cat) => (
            <option key={cat.value || 'all'} value={cat.value}>
              {cat.label}
            </option>
          ))}
        </select>
        {onRetry && (
          <button type="button" className="btn btn-ghost" onClick={onRetry}>
            Actualizar
          </button>
        )}
      </div>

      {error && (
        <div className="alert alert-error" role="alert">
          {error}
          {onRetry && (
            <div style={{ marginTop: '0.5rem' }}>
              <button type="button" className="btn btn-ghost" onClick={onRetry}>
                Reintentar
              </button>
            </div>
          )}
        </div>
      )}

      {!error && products.length === 0 && (
        <p className="empty-state">No hay productos para mostrar.</p>
      )}

      {!error && products.length > 0 && (
        <div className="product-grid">
          {products.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
        </div>
      )}
    </section>
  );
}
