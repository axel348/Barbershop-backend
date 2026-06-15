import { describe, expect, it, vi, beforeEach } from 'vitest';
import { fetchProducts, fetchProductById } from '../services/productApi.js';

vi.mock('../services/apiClient.js', () => ({
  apiRequest: vi.fn(),
}));

import { apiRequest } from '../services/apiClient.js';

describe('productApi', () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('fetchProducts retorna data del BFF', async () => {
    apiRequest.mockResolvedValue({ success: true, data: [{ id: 1, name: 'Aceite' }] });

    const products = await fetchProducts();

    expect(apiRequest).toHaveBeenCalledWith('/bff/products');
    expect(products).toHaveLength(1);
  });

  it('fetchProductById retorna un producto', async () => {
    apiRequest.mockResolvedValue({ success: true, data: { id: 2, name: 'Pomada' } });

    const product = await fetchProductById(2);

    expect(apiRequest).toHaveBeenCalledWith('/bff/products/2');
    expect(product.name).toBe('Pomada');
  });
});
