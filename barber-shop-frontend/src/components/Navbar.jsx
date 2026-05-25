import { Link, NavLink, useNavigate } from 'react-router-dom';

export default function Navbar({ user, onLogout }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    onLogout?.();
    navigate('/');
  };

  return (
    <header className="navbar">
      <div className="navbar__inner">
        <Link to="/" className="navbar__brand">
          Barber <span>Shop</span>
        </Link>

        <nav className="navbar__links" aria-label="Principal">
          <NavLink
            to="/productos"
            className={({ isActive }) =>
              `navbar__link${isActive ? ' navbar__link--active' : ''}`
            }
          >
            Productos
          </NavLink>

          {user ? (
            <>
              <span className="navbar__user">
                Hola, {user.name} ({user.role})
              </span>
              <button type="button" className="btn btn-ghost" onClick={handleLogout}>
                Cerrar sesión
              </button>
            </>
          ) : (
            <>
              <NavLink
                to="/login"
                className={({ isActive }) =>
                  `navbar__link${isActive ? ' navbar__link--active' : ''}`
                }
              >
                Iniciar sesión
              </NavLink>
              <NavLink to="/registro">
                <span className="btn btn-primary">Registrarse</span>
              </NavLink>
            </>
          )}
        </nav>
      </div>
    </header>
  );
}
