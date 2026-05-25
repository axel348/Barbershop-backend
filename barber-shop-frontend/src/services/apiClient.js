const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

export class ApiError extends Error {
  constructor(message, status, payload = null) {
    super(message);
    this.name = 'ApiError';
    this.status = status;
    this.payload = payload;
  }
}

/**
 * Cliente HTTP centralizado para el BFF.
 * Todas las peticiones pasan por aquí; los servicios de dominio no usan fetch directo.
 */
export async function apiRequest(path, options = {}) {
  const url = `${BASE_URL}${path}`;
  const headers = {
    'Content-Type': 'application/json',
    Accept: 'application/json',
    ...options.headers,
  };

  const config = {
    ...options,
    headers,
  };

  if (config.body && typeof config.body === 'object') {
    config.body = JSON.stringify(config.body);
  }

  let response;
  try {
    response = await fetch(url, config);
  } catch {
    throw new ApiError(
      'No se pudo conectar con el BFF. Verifica que barber-shop-bff esté en ejecución.',
      0
    );
  }

  let payload = null;
  const contentType = response.headers.get('content-type');
  if (contentType?.includes('application/json')) {
    payload = await response.json();
  }

  if (!response.ok) {
    const message =
      payload?.message ||
      payload?.error ||
      `Error ${response.status} en ${path}`;
    throw new ApiError(message, response.status, payload);
  }

  if (payload && payload.success === false) {
    throw new ApiError(
      payload.message || 'La operación no fue exitosa',
      response.status,
      payload
    );
  }

  return payload;
}

export function getApiBaseUrl() {
  return BASE_URL;
}
