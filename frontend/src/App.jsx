import {
  Routes,
  Route
} from "react-router-dom";

import Navbar from "./components/Navbar";

import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import CartPage from "./pages/CartPage";
import OrdersPage from "./pages/OrdersPage";

import ProtectedRoute
from "./routes/ProtectedRoute";

import AdminDashboard
from "./pages/AdminDashboard";

import AdminProducts
from "./pages/AdminProducts";

import AdminCategories
from "./pages/AdminCategories";

import AdminOrders
from "./pages/AdminOrders";

import ProductDetailsPage
from "./pages/ProductDetailsPage";

function App() {

  return (

    <>

      <Navbar />

      <Routes>

        <Route
          path="/"
          element={<HomePage />}
        />

        <Route
          path="/login"
          element={<LoginPage />}
        />

        <Route
          path="/register"
          element={<RegisterPage />}
        />

        <Route
          path="/cart"
          element={<CartPage />}
        />

        <Route
          path="/orders"
          element={<OrdersPage />}
        />

        <Route
          path="/admin"

          element={

            <ProtectedRoute
              role="ADMIN"
            >

              <AdminDashboard />

            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/products"

          element={

            <ProtectedRoute
              role="ADMIN"
            >

              <AdminProducts />

            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/categories"

          element={

            <ProtectedRoute
              role="ADMIN"
            >

              <AdminCategories />

            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/orders"

          element={

            <ProtectedRoute
              role="ADMIN"
            >

              <AdminOrders />

            </ProtectedRoute>
          }
        />

        <Route
          path="/products/:id"

          element={
            <ProductDetailsPage />
          }
        />

      </Routes>

    </>
  );
}

export default App;