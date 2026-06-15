import CartView from '../components/CartView.jsx';
import { useCartContext } from '../context/CartContext.jsx';

export default function CartPage() {
  const {
    cart,
    loading,
    error,
    actionLoading,
    loadCart,
    changeQuantity,
    removeItem,
    clearCart,
  } = useCartContext();

  const handleIncrease = (item) => changeQuantity(item.id, item.quantity + 1);
  const handleDecrease = (item) => changeQuantity(item.id, item.quantity - 1);

  return (
    <>
      <h1 className="page-title">Carrito de compras</h1>
      <p className="page-subtitle">
        Gestión del carrito vía <code>GET /bff/cart</code>
      </p>
      <CartView
        cart={cart}
        loading={loading}
        error={error}
        actionLoading={actionLoading}
        onIncrease={handleIncrease}
        onDecrease={handleDecrease}
        onRemove={removeItem}
        onClear={clearCart}
        onRetry={loadCart}
      />
    </>
  );
}
