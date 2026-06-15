import { apiRequest } from './apiClient.js';

export async function fetchCart() {
  const response = await apiRequest('/bff/cart');
  return response?.data ?? { items: [], total: 0 };
}

export async function addCartItem(item) {
  const response = await apiRequest('/bff/cart/items', {
    method: 'POST',
    body: item,
  });
  return response?.data ?? null;
}

export async function updateCartItem(id, quantity) {
  const response = await apiRequest(`/bff/cart/items/${id}`, {
    method: 'PUT',
    body: { quantity },
  });
  return response?.data ?? null;
}

export async function removeCartItem(id) {
  await apiRequest(`/bff/cart/items/${id}`, { method: 'DELETE' });
}

export async function clearCart() {
  await apiRequest('/bff/cart/clear', { method: 'DELETE' });
}
