import { useState } from 'react';
import { PRODUCT_CATEGORIES, formatCategory, formatPrice } from '../constants/categories.js';
import {
  createProduct,
  deleteProduct,
  updateProduct,
} from '../services/adminProductApi.js';

const emptyForm = {
  name: '',
  description: '',
  category: 'ESTILO_CABELLO',
  brand: '',
  price: '',
  stock: '',
};

export default function AdminPanel({ products, loading, error, onProductsChanged }) {
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [submitting, setSubmitting] = useState(false);
  const [formError, setFormError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);

  const resetForm = () => {
    setForm(emptyForm);
    setEditingId(null);
    setFormError(null);
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const buildPayload = () => ({
    name: form.name.trim(),
    description: form.description.trim(),
    category: form.category,
    brand: form.brand.trim(),
    price: Number(form.price),
    stock: Number(form.stock),
  });

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSubmitting(true);
    setFormError(null);
    setSuccessMessage(null);

    try {
      const payload = buildPayload();
      if (editingId) {
        await updateProduct(editingId, payload);
        setSuccessMessage('Producto actualizado correctamente.');
      } else {
        await createProduct(payload);
        setSuccessMessage('Producto creado correctamente.');
      }
      resetForm();
      await onProductsChanged?.();
    } catch (err) {
      setFormError(err.message || 'Error al guardar el producto');
    } finally {
      setSubmitting(false);
    }
  };

  const handleEdit = (product) => {
    setEditingId(product.id);
    setForm({
      name: product.name ?? '',
      description: product.description ?? '',
      category: product.category ?? 'ESTILO_CABELLO',
      brand: product.brand ?? '',
      price: String(product.price ?? ''),
      stock: String(product.stock ?? ''),
    });
    setFormError(null);
    setSuccessMessage(null);
  };

  const handleDelete = async (id) => {
    if (!window.confirm('¿Eliminar este producto?')) return;
    setSubmitting(true);
    setFormError(null);
    setSuccessMessage(null);
    try {
      await deleteProduct(id);
      if (editingId === id) resetForm();
      setSuccessMessage('Producto eliminado.');
      await onProductsChanged?.();
    } catch (err) {
      setFormError(err.message || 'Error al eliminar');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="admin-panel">
      <section className="admin-form-card">
        <h2>{editingId ? 'Editar producto' : 'Agregar producto'}</h2>
        <form className="auth-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="admin-name">Nombre</label>
            <input
              id="admin-name"
              name="name"
              value={form.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="admin-description">Descripción</label>
            <input
              id="admin-description"
              name="description"
              value={form.description}
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="admin-category">Categoría</label>
            <select
              id="admin-category"
              name="category"
              value={form.category}
              onChange={handleChange}
              required
            >
              {PRODUCT_CATEGORIES.filter((c) => c.value).map((cat) => (
                <option key={cat.value} value={cat.value}>
                  {cat.label}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="admin-brand">Marca</label>
            <input
              id="admin-brand"
              name="brand"
              value={form.brand}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="admin-price">Precio (CLP)</label>
            <input
              id="admin-price"
              name="price"
              type="number"
              min="0"
              value={form.price}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="admin-stock">Stock</label>
            <input
              id="admin-stock"
              name="stock"
              type="number"
              min="0"
              value={form.stock}
              onChange={handleChange}
              required
            />
          </div>

          {formError && (
            <div className="alert alert-error" role="alert">
              {formError}
            </div>
          )}
          {successMessage && (
            <div className="alert alert-success" role="status">
              {successMessage}
            </div>
          )}

          <div className="form-actions">
            <button type="submit" className="btn btn-primary" disabled={submitting}>
              {editingId ? 'Guardar cambios' : 'Crear producto'}
            </button>
            {editingId && (
              <button
                type="button"
                className="btn btn-ghost"
                disabled={submitting}
                onClick={resetForm}
              >
                Cancelar edición
              </button>
            )}
          </div>
        </form>
      </section>

      <section className="admin-list-card">
        <h2>Productos en catálogo</h2>
        {loading && <p>Cargando productos…</p>}
        {error && (
          <div className="alert alert-error" role="alert">
            {error}
          </div>
        )}
        {!loading && !error && products.length === 0 && (
          <p className="empty-state">No hay productos registrados.</p>
        )}
        {!loading && products.length > 0 && (
          <div className="admin-table-wrap">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Categoría</th>
                  <th>Precio</th>
                  <th>Stock</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                {products.map((product) => (
                  <tr key={product.id}>
                    <td>{product.name}</td>
                    <td>{formatCategory(product.category)}</td>
                    <td>{formatPrice(product.price)}</td>
                    <td>{product.stock}</td>
                    <td className="admin-table__actions">
                      <button
                        type="button"
                        className="btn btn-ghost"
                        disabled={submitting}
                        onClick={() => handleEdit(product)}
                      >
                        Editar
                      </button>
                      <button
                        type="button"
                        className="btn btn-ghost"
                        disabled={submitting}
                        onClick={() => handleDelete(product.id)}
                      >
                        Eliminar
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>
    </div>
  );
}
