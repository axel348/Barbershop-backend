import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
} from 'react';
import {
  fetchProducts,
  fetchProductsByCategory,
} from '../services/productApi.js';

export const ProductsContext = createContext(null);

export function ProductsProvider({ children }) {
  const [products, setProducts] = useState([]);
  const [category, setCategory] = useState('');
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

  const changeCategory = useCallback((newCategory) => {
    setCategory(newCategory);
  }, []);

  const reload = useCallback(() => load(category), [category, load]);

  const value = useMemo(
    () => ({
      products,
      category,
      setCategory: changeCategory,
      loading,
      error,
      reload,
    }),
    [products, category, changeCategory, loading, error, reload]
  );

  return (
    <ProductsContext.Provider value={value}>{children}</ProductsContext.Provider>
  );
}

export function useProductsContext() {
  const context = useContext(ProductsContext);
  if (!context) {
    throw new Error('useProductsContext debe usarse dentro de ProductsProvider');
  }
  return context;
}
