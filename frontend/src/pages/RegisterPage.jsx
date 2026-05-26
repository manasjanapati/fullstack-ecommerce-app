import {
  useState
} from "react";

import {
  useNavigate
} from "react-router-dom";

import {
  registerUser
} from "../services/authService";

import "../styles/Auth.css";

export default function RegisterPage() {

  const [name, setName] =
    useState("");

  const [email, setEmail] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [message, setMessage] =
    useState("");

  const navigate =
    useNavigate();

  const handleSubmit =
    async (e) => {

      e.preventDefault();

      try {

        await registerUser({
          name,
          email,
          password
        });

        setMessage(
          "Registration successful"
        );

        setTimeout(() => {

          navigate("/login");

        }, 1500);

      } catch (error) {

        setMessage(
          "Registration failed"
        );
      }
    };

  return (

    <div className="auth-container">

      <form
        className="auth-form"
        onSubmit={handleSubmit}
      >

        <h2>Register</h2>

        {message && (
          <p>{message}</p>
        )}

        <input
          type="text"
          placeholder="Name"

          value={name}

          onChange={(e) =>
            setName(e.target.value)
          }
        />

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
          Register
        </button>

      </form>

    </div>
  );
}