import { apiRequest } from './apiClient.js';

export async function createProduct(product) {
  const response = await apiRequest('/bff/admin/products', {
    method: 'POST',
    body: product,
  });
  return response?.data ?? null;
}

export async function updateProduct(id, product) {
  const response = await apiRequest(`/bff/admin/products/${id}`, {
    method: 'PUT',
    body: product,
  });
  return response?.data ?? null;
}

export async function deleteProduct(id) {
  await apiRequest(`/bff/admin/products/${id}`, { method: 'DELETE' });
}
