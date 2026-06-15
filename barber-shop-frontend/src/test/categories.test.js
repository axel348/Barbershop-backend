import { describe, expect, it } from 'vitest';
import { formatCategory, formatPrice } from '../constants/categories.js';

describe('categories utils', () => {
  it('formatPrice formatea en CLP', () => {
    expect(formatPrice(18990)).toContain('18');
  });

  it('formatCategory devuelve etiqueta legible', () => {
    expect(formatCategory('CUIDADO_BARBA')).toBe('Cuidado de barba');
  });

  it('formatPrice retorna guion si es null', () => {
    expect(formatPrice(null)).toBe('—');
  });
});
