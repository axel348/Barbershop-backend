import { describe, expect, it, vi, beforeEach } from 'vitest';
import { login, getStoredUser, clearStoredUser } from '../services/authApi.js';

vi.mock('../services/apiClient.js', () => ({
  apiRequest: vi.fn(),
}));

import { apiRequest } from '../services/apiClient.js';

describe('authApi', () => {
  beforeEach(() => {
    vi.clearAllMocks();
    localStorage.clear();
  });

  it('login guarda usuario en localStorage', async () => {
    const user = { id: 1, name: 'Juan', email: 'juan@email.com', role: 'CLIENT' };
    apiRequest.mockResolvedValue({ success: true, data: user });

    const result = await login({ email: 'juan@email.com', password: 'cliente123' });

    expect(result.user).toEqual(user);
    expect(getStoredUser()).toEqual(user);
  });

  it('clearStoredUser elimina la sesión', () => {
    localStorage.setItem('barbershop_user', JSON.stringify({ id: 1 }));
    clearStoredUser();
    expect(getStoredUser()).toBeNull();
  });
});
