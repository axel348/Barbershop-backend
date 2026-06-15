import { Link } from 'react-router-dom';
import { formatCategory, formatPrice } from '../constants/categories.js';

export default function ProductCard({ product, onAddToCart, addingToCart = false }) {
  if (!product) return null;

  const { id, name, brand, category, price, stock } = product;
  const canAdd = stock > 0;

  const handleAdd = () => {
    if (canAdd) onAddToCart?.(product);
  };

  return (
    <article className="product-card">
      <span className="product-card__category">{formatCategory(category)}</span>
      <h3 className="product-card__name">{name}</h3>
      {brand && <p className="product-card__brand">{brand}</p>}
      <p className="product-card__price">{formatPrice(price)}</p>
      <span className="product-card__stock">
        {stock > 0 ? `${stock} en stock` : 'Sin stock'}
      </span>
      <div className="product-card__actions">
        <button
          type="button"
          className="btn btn-primary product-card__cart-btn"
          disabled={!canAdd || addingToCart}
          onClick={handleAdd}
        >
          {addingToCart ? 'Agregando…' : 'Agregar al carrito'}
        </button>
        <Link to={`/productos/${id}`} className="product-card__link">
          Ver detalle →
        </Link>
      </div>
    </article>
  );
}
