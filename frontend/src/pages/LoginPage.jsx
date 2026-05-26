import {
  useState,
  useContext
} from "react";

import { useNavigate }
from "react-router-dom";

import {
  loginUser
} from "../services/authService";

import {
  AuthContext
} from "../context/AuthContext";

import "../styles/Auth.css";

export default function LoginPage() {

  const [email, setEmail] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [error, setError] =
    useState("");

  const { login } =
    useContext(AuthContext);

  const navigate =
    useNavigate();

  const handleSubmit =
    async (e) => {

      e.preventDefault();

      try {

        const data =
          await loginUser({
            email,
            password
          });

        login(data);

        navigate("/");

      } catch (err) {

        setError(
          "Invalid credentials"
        );
      }
    };

  return (

    <div className="auth-container">

      <form
        className="auth-form"
        onSubmit={handleSubmit}
      >

        <h2>Login</h2>

        {error && (
          <p className="error">
            {error}
          </p>
        )}

        <input
          type="email"
          placeholder="Email"

          value={email}

          onChange={(e) =>
            setEmail(e.target.value)
          }
        />

        <input
          type="password"
          placeholder="Password"

          value={password}

          onChange={(e) =>
            setPassword(
              e.target.value
            )
          }
        />

        <button type="submit">
          Login
        </button>

      </form>

    </div>
  );
}