import { useCallback, useEffect, useState } from 'react';
import { fetchProductById } from '../services/productApi.js';

export function useProduct(productId) {
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const load = useCallback(async () => {
    if (!productId) {
      setError('ID de producto no válido');
      setLoading(false);
      return;
    }
    setLoading(true);
    setError(null);
    try {
      const data = await fetchProductById(productId);
      if (!data) {
        setError('Producto no encontrado');
        setProduct(null);
      } else {
        setProduct(data);
      }
    } catch (err) {
      setProduct(null);
      setError(err.message || 'Error al cargar el producto');
    } finally {
      setLoading(false);
    }
  }, [productId]);

  useEffect(() => {
    load();
  }, [load]);

  return { product, loading, error, reload: load };
}
