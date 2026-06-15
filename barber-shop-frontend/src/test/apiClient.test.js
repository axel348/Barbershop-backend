import { describe, expect, it, vi, beforeEach } from 'vitest';
import { apiRequest, ApiError } from '../services/apiClient.js';

describe('apiClient', () => {
  beforeEach(() => {
    vi.restoreAllMocks();
  });

  it('retorna JSON cuando la petición es exitosa', async () => {
    global.fetch = vi.fn().mockResolvedValue({
      ok: true,
      headers: { get: () => 'application/json' },
      json: () => Promise.resolve({ success: true, data: [1, 2] }),
    });

    const result = await apiRequest('/bff/products');
    expect(result.success).toBe(true);
  });

  it('lanza ApiError cuando success es false', async () => {
    global.fetch = vi.fn().mockResolvedValue({
      ok: true,
      headers: { get: () => 'application/json' },
      json: () => Promise.resolve({ success: false, message: 'Error lógico' }),
    });

    await expect(apiRequest('/bff/products')).rejects.toThrow(ApiError);
  });

  it('lanza ApiError en error HTTP', async () => {
    global.fetch = vi.fn().mockResolvedValue({
      ok: false,
      status: 404,
      headers: { get: () => 'application/json' },
      json: () => Promise.resolve({ message: 'No encontrado' }),
    });

    await expect(apiRequest('/bff/products/9')).rejects.toThrow('No encontrado');
  });

  it('lanza ApiError si no hay conexión', async () => {
    global.fetch = vi.fn().mockRejectedValue(new Error('Network'));

    await expect(apiRequest('/bff/products')).rejects.toThrow(/BFF/);
  });
});
