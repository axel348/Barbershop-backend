import { describe, expect, it } from 'vitest';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import ProductCard from '../components/ProductCard.jsx';

describe('ProductCard', () => {
  it('renderiza nombre y precio del producto', () => {
    render(
      <MemoryRouter>
        <ProductCard
          product={{
            id: 1,
            name: 'Aceite para barba',
            brand: 'BarberPro',
            category: 'CUIDADO_BARBA',
            price: 18990,
            stock: 10,
          }}
        />
      </MemoryRouter>
    );

    expect(screen.getByText('Aceite para barba')).toBeInTheDocument();
    expect(screen.getByText(/18/)).toBeInTheDocument();
  });
});
