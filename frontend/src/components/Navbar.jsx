import {
  Link
} from "react-router-dom";

import {
  useContext
} from "react";

import {
  AuthContext
} from "../context/AuthContext";

import "../styles/Navbar.css";

export default function Navbar() {

  const {
    user,
    logout
  } = useContext(AuthContext);

  return (

    <nav className="navbar">

      <h2>E-Commerce</h2>

      <div className="nav-links">

        <Link to="/">
          Home
        </Link>

        {user && (

          <>

            {user.role === "ADMIN" && (

              <Link to="/admin">

                Admin

              </Link>
            )}
            
            <Link to="/cart">
              Cart
            </Link>

            <Link to="/orders">
              Orders
            </Link>

            <button
              onClick={logout}
            >
              Logout
            </button>
          </>
        )}

        {!user && (

          <>
            <Link to="/login">
              Login
            </Link>

            <Link to="/register">
              Register
            </Link>
          </>
        )}

      </div>

    </nav>
  );
}