import { Link } from 'react-router-dom';

export default function HomePage() {
  return (
    <section className="hero">
      <h1>Tienda Barber Shop</h1>
      <p>
        Catálogo de productos para barbería. Este frontend consume únicamente el
        BFF en <code>http://localhost:8080</code>, sin llamar a los microservicios
        directamente.
      </p>
      <Link to="/productos" className="btn btn-primary">
        Ver productos
      </Link>
    </section>
  );
}
