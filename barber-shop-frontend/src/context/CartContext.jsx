import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useState,
} from 'react';
import {
  addCartItem,
  clearCart as clearCartApi,
  fetchCart,
  removeCartItem,
  updateCartItem,
} from '../services/cartApi.js';

export const CartContext = createContext(null);

const emptyCart = { items: [], total: 0 };

export function CartProvider({ children }) {
  const [cart, setCart] = useState(emptyCart);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [actionLoading, setActionLoading] = useState(false);

  const loadCart = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchCart();
      setCart(data ?? emptyCart);
    } catch (err) {
      setCart(emptyCart);
      setError(err.message || 'Error al cargar el carrito');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    loadCart();
  }, [loadCart]);

  const addToCart = useCallback(
    async (product, quantity = 1) => {
      if (!product?.id) return;
      setActionLoading(true);
      setError(null);
      try {
        await addCartItem({
          productId: product.id,
          productName: product.name,
          price: product.price,
          quantity,
        });
        await loadCart();
      } catch (err) {
        setError(err.message || 'No se pudo agregar al carrito');
        throw err;
      } finally {
        setActionLoading(false);
      }
    },
    [loadCart]
  );

  const changeQuantity = useCallback(
    async (itemId, quantity) => {
      if (quantity < 1) return;
      setActionLoading(true);
      setError(null);
      try {
        await updateCartItem(itemId, quantity);
        await loadCart();
      } catch (err) {
        setError(err.message || 'No se pudo actualizar la cantidad');
        throw err;
      } finally {
        setActionLoading(false);
      }
    },
    [loadCart]
  );

  const removeItem = useCallback(
    async (itemId) => {
      setActionLoading(true);
      setError(null);
      try {
        await removeCartItem(itemId);
        await loadCart();
      } catch (err) {
        setError(err.message || 'No se pudo eliminar el ítem');
        throw err;
      } finally {
        setActionLoading(false);
      }
    },
    [loadCart]
  );

  const clearCart = useCallback(async () => {
    setActionLoading(true);
    setError(null);
    try {
      await clearCartApi();
      await loadCart();
    } catch (err) {
      setError(err.message || 'No se pudo vaciar el carrito');
      throw err;
    } finally {
      setActionLoading(false);
    }
  }, [loadCart]);

  const itemCount = useMemo(
    () => (cart.items ?? []).reduce((sum, item) => sum + item.quantity, 0),
    [cart.items]
  );

  const value = useMemo(
    () => ({
      cart,
      loading,
      error,
      actionLoading,
      itemCount,
      loadCart,
      addToCart,
      changeQuantity,
      removeItem,
      clearCart,
    }),
    [
      cart,
      loading,
      error,
      actionLoading,
      itemCount,
      loadCart,
      addToCart,
      changeQuantity,
      removeItem,
      clearCart,
    ]
  );

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
}

export function useCartContext() {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error('useCartContext debe usarse dentro de CartProvider');
  }
  return context;
}
