import {
  Link
} from "react-router-dom";

import "../styles/Admin.css";

export default function AdminLayout({

  children

}) {

  return (

    <div className="admin-layout">

      <aside className="sidebar">

        <h2>Admin Panel</h2>

        <Link to="/admin/products">
          Products
        </Link>

        <Link to="/admin/categories">
          Categories
        </Link>

        <Link to="/admin/orders">
          Orders
        </Link>

      </aside>

      <main className="admin-content">

        {children}

      </main>

    </div>
  );
}