import { apiRequest } from './apiClient.js';

const USER_STORAGE_KEY = 'barbershop_user';

/**
 * POST /bff/auth/login
 */
export async function login(credentials) {
  const response = await apiRequest('/bff/auth/login', {
    method: 'POST',
    body: credentials,
  });
  const user = response?.data;
  if (user) {
    saveUser(user);
  }
  return { user, message: response?.message };
}

/**
 * POST /bff/auth/register
 */
export async function register(userData) {
  const response = await apiRequest('/bff/auth/register', {
    method: 'POST',
    body: userData,
  });
  const user = response?.data;
  if (user) {
    saveUser(user);
  }
  return { user, message: response?.message };
}

export function saveUser(user) {
  localStorage.setItem(USER_STORAGE_KEY, JSON.stringify(user));
}

export function getStoredUser() {
  const raw = localStorage.getItem(USER_STORAGE_KEY);
  if (!raw) return null;
  try {
    return JSON.parse(raw);
  } catch {
    localStorage.removeItem(USER_STORAGE_KEY);
    return null;
  }
}

export function clearStoredUser() {
  localStorage.removeItem(USER_STORAGE_KEY);
}

export function logout() {
  clearStoredUser();
}
