import { useState } from 'react';
import { Link } from 'react-router-dom';

const initialForm = {
  name: '',
  email: '',
  password: '',
  role: 'CLIENT',
};

export default function RegisterForm({ onSubmit, loading = false, error = null }) {
  const [form, setForm] = useState(initialForm);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await onSubmit?.(form);
  };

  return (
    <div className="auth-card">
      <h2 className="page-title" style={{ fontSize: '1.35rem' }}>
        Crear cuenta
      </h2>

      {error && (
        <div className="alert alert-error" role="alert">
          {error}
        </div>
      )}

      <form className="auth-form" onSubmit={handleSubmit} noValidate>
        <div className="form-group">
          <label htmlFor="register-name">Nombre</label>
          <input
            id="register-name"
            name="name"
            type="text"
            required
            maxLength={120}
            value={form.name}
            onChange={handleChange}
            placeholder="Juan Pérez"
          />
        </div>

        <div className="form-group">
          <label htmlFor="register-email">Email</label>
          <input
            id="register-email"
            name="email"
            type="email"
            autoComplete="email"
            required
            value={form.email}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="register-password">Contraseña</label>
          <input
            id="register-password"
            name="password"
            type="password"
            autoComplete="new-password"
            required
            minLength={6}
            value={form.password}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="register-role">Rol</label>
          <select
            id="register-role"
            name="role"
            value={form.role}
            onChange={handleChange}
          >
            <option value="CLIENT">Cliente</option>
            <option value="ADMIN">Administrador</option>
          </select>
        </div>

        <div className="form-actions">
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Registrando…' : 'Registrarse'}
          </button>
        </div>
      </form>

      <p className="form-footer">
        ¿Ya tienes cuenta? <Link to="/login">Inicia sesión</Link>
      </p>
    </div>
  );
}
