import { useProductsContext } from '../context/ProductsContext.jsx';

export function useProducts() {
  return useProductsContext();
}
