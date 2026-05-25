import { apiRequest } from './apiClient.js';

/**
 * GET /bff/products
 */
export async function fetchProducts() {
  const response = await apiRequest('/bff/products');
  return response?.data ?? [];
}

/**
 * GET /bff/products/{id}
 */
export async function fetchProductById(id) {
  const response = await apiRequest(`/bff/products/${id}`);
  return response?.data ?? null;
}

/**
 * GET /bff/products/category/{category}
 */
export async function fetchProductsByCategory(category) {
  const response = await apiRequest(
    `/bff/products/category/${encodeURIComponent(category)}`
  );
  return response?.data ?? [];
}
