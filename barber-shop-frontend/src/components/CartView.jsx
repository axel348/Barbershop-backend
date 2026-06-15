import { formatPrice } from '../constants/categories.js';

export default function CartView({
  cart,
  loading,
  error,
  actionLoading,
  onIncrease,
  onDecrease,
  onRemove,
  onClear,
  onRetry,
}) {
  if (loading) {
    return (
      <div className="loading-block" role="status">
        <span className="spinner" aria-hidden="true" />
        Cargando carrito…
      </div>
    );
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
      </div>
    );
  }

  const items = cart?.items ?? [];

  if (items.length === 0) {
    return (
      <p className="empty-state">
        Tu carrito está vacío. Agrega productos desde el catálogo.
      </p>
    );
  }

  return (
    <section className="cart-view" aria-label="Carrito de compras">
      <ul className="cart-list">
        {items.map((item) => (
          <li key={item.id} className="cart-item">
            <div className="cart-item__info">
              <h3 className="cart-item__name">{item.productName}</h3>
              <p className="cart-item__price">{formatPrice(item.price)} c/u</p>
            </div>

            <div className="cart-item__controls">
              <button
                type="button"
                className="btn btn-ghost cart-item__qty-btn"
                disabled={actionLoading || item.quantity <= 1}
                onClick={() => onDecrease?.(item)}
                aria-label="Disminuir cantidad"
              >
                −
              </button>
              <span className="cart-item__quantity">{item.quantity}</span>
              <button
                type="button"
                className="btn btn-ghost cart-item__qty-btn"
                disabled={actionLoading}
                onClick={() => onIncrease?.(item)}
                aria-label="Aumentar cantidad"
              >
                +
              </button>
            </div>

            <p className="cart-item__subtotal">{formatPrice(item.subtotal)}</p>

            <button
              type="button"
              className="btn btn-ghost cart-item__remove"
              disabled={actionLoading}
              onClick={() => onRemove?.(item.id)}
            >
              Eliminar
            </button>
          </li>
        ))}
      </ul>

      <footer className="cart-footer">
        <p className="cart-total">
          Total: <strong>{formatPrice(cart.total)}</strong>
        </p>
        <button
          type="button"
          className="btn btn-ghost"
          disabled={actionLoading}
          onClick={onClear}
        >
          Vaciar carrito
        </button>
      </footer>
    </section>
  );
}
