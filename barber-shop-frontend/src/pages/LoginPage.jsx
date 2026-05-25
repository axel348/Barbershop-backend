import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginForm from '../components/LoginForm.jsx';
import { useAuth } from '../hooks/useAuth.js';

export default function LoginPage() {
  const navigate = useNavigate();
  const { login, loading, error, clearError } = useAuth();
  const [success, setSuccess] = useState('');

  const handleSubmit = async (credentials) => {
    clearError();
    setSuccess('');
    try {
      await login(credentials);
      setSuccess('Sesión iniciada correctamente.');
      navigate('/productos');
    } catch {
      /* error expuesto por useAuth */
    }
  };

  return (
    <>
      <h1 className="page-title">Acceso</h1>
      {success && (
        <div className="alert alert-success" role="status">
          {success}
        </div>
      )}
      <LoginForm onSubmit={handleSubmit} loading={loading} error={error} />
    </>
  );
}
