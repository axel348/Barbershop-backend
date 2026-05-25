export const PRODUCT_CATEGORIES = [
  { value: '', label: 'Todas las categorías' },
  { value: 'CUIDADO_BARBA', label: 'Cuidado de barba' },
  { value: 'ESTILO_CABELLO', label: 'Estilo de cabello' },
  { value: 'HERRAMIENTAS', label: 'Herramientas' },
  { value: 'CUIDADO_PIEL', label: 'Cuidado de piel' },
];

export function formatCategory(category) {
  const found = PRODUCT_CATEGORIES.find((c) => c.value === category);
  return found?.label ?? category?.replace(/_/g, ' ') ?? '—';
}

export function formatPrice(price) {
  if (price == null) return '—';
  return new Intl.NumberFormat('es-CL', {
    style: 'currency',
    currency: 'CLP',
    maximumFractionDigits: 0,
  }).format(price);
}
