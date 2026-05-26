import {
  useContext
} from "react";

import {
  useNavigate
} from "react-router-dom";

import {
  AuthContext
} from "../context/AuthContext";

import {
  addToCart
} from "../services/cartService";

import "../styles/ProductCard.css";

import {
  Link
} from "react-router-dom";

export default function ProductCard({
  product
}) {

  const { user } =
    useContext(AuthContext);

  const navigate =
    useNavigate();

  const handleAddToCart =
    async () => {

      if (!user) {

        navigate("/login");

        return;
      }

      try {

        await addToCart({

          productId: product.id,

          quantity: 1
        });

        alert(
          "Added to cart"
        );

      } catch (error) {

        console.log(error);

        alert(
          "Failed to add"
        );
      }
    };

  return (

    <div className="product-card">

      <Link to={`/products/${product.id}`}>

        <img
          src={product.imageUrl}
          alt={product.name}
        />

        <h3>
          {product.name}
        </h3>

        <p className="product-price">

          ₹{product.price}

        </p>

        <p className="product-category">

          {product.category.name}

        </p>

        <div className="product-rating">

          <span>

            ⭐
            {product.averageRating
              ?.toFixed(1) || 0}

          </span>

          <span>

            (
            {product.ratingCount || 0}
            )

          </span>

        </div>

      </Link>

      <button
        className="add-cart-btn"

        onClick={handleAddToCart}
      >

        Add To Cart

      </button>

    </div>
  );
}