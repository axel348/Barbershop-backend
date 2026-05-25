import { Link } from 'react-router-dom';
import { formatCategory, formatPrice } from '../constants/categories.js';

export default function ProductCard({ product }) {
  if (!product) return null;

  const { id, name, brand, category, price, stock } = product;

  return (
    <article className="product-card">
      <span className="product-card__category">{formatCategory(category)}</span>
      <h3 className="product-card__name">{name}</h3>
      {brand && <p className="product-card__brand">{brand}</p>}
      <p className="product-card__price">{formatPrice(price)}</p>
      <span className="product-card__stock">
        {stock > 0 ? `${stock} en stock` : 'Sin stock'}
      </span>
      <Link to={`/productos/${id}`} className="product-card__link">
        Ver detalle →
      </Link>
    </article>
  );
}
