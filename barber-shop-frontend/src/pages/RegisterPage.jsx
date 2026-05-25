import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import RegisterForm from '../components/RegisterForm.jsx';
import { useAuth } from '../hooks/useAuth.js';

export default function RegisterPage() {
  const navigate = useNavigate();
  const { register, loading, error, clearError } = useAuth();
  const [success, setSuccess] = useState('');

  const handleSubmit = async (userData) => {
    clearError();
    setSuccess('');
    try {
      await register(userData);
      setSuccess('Cuenta creada. Ya puedes usar la tienda.');
      navigate('/productos');
    } catch {
      /* error expuesto por useAuth */
    }
  };

  return (
    <>
      <h1 className="page-title">Registro</h1>
      {success && (
        <div className="alert alert-success" role="status">
          {success}
        </div>
      )}
      <RegisterForm onSubmit={handleSubmit} loading={loading} error={error} />
    </>
  );
}
