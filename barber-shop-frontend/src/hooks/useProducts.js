import { useCallback, useEffect, useState } from 'react';
import {
  fetchProducts,
  fetchProductsByCategory,
} from '../services/productApi.js';

export function useProducts(initialCategory = '') {
  const [products, setProducts] = useState([]);
  const [category, setCategory] = useState(initialCategory);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const load = useCallback(async (selectedCategory) => {
    setLoading(true);
    setError(null);
    try {
      const data = selectedCategory
        ? await fetchProductsByCategory(selectedCategory)
        : await fetchProducts();
      setProducts(data);
    } catch (err) {
      setProducts([]);
      setError(err.message || 'Error al cargar productos');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    load(category);
  }, [category, load]);

  const changeCategory = (newCategory) => {
    setCategory(newCategory);
  };

  const reload = () => load(category);

  return { products, category, setCategory: changeCategory, loading, error, reload };
}
