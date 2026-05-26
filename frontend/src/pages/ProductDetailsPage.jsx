import {
  useEffect,
  useState,
  useContext
} from "react";

import {
  useParams,
  useNavigate
} from "react-router-dom";

import {
  getProductById
} from "../services/productService";

import {
  addToCart
} from "../services/cartService";

import {
  AuthContext
} from "../context/AuthContext";

import "../styles/ProductDetails.css";

export default function ProductDetailsPage() {

  const [product, setProduct] =
    useState(null);

  const { id } =
    useParams();

  const navigate =
    useNavigate();

  const { user } =
    useContext(AuthContext);

  useEffect(() => {

    loadProduct();

  }, [id]);

  const loadProduct =
    async () => {

      try {

        const data =
          await getProductById(id);

        setProduct(data);

      } catch (error) {

        console.log(error);
      }
  };

  const handleAddToCart =
    async () => {

      if (!user) {

        navigate("/login");

        return;
      }

      try {

        await addToCart({

          productId:
            product.id,

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

  if (!product) {

    return <h1>Loading...</h1>;
  }

  return (

    <div className="product-details-page">

      <button
        className="back-btn"

        onClick={() => navigate("/")}
      >

        ← Back To Home

      </button>

      <div className="product-details-card">

        <img
          src={product.imageUrl}
          alt={product.name}
        />

        <div className="product-info">

          <h1>
            {product.name}
          </h1>

          <p className="category">

            Category:
            {product.category.name}

          </p>

          <h2>
            ₹{product.price}
          </h2>

          <div className="rating-section">

            <h3>

              Rating:
              {" "}
              {product.averageRating
                ?.toFixed(1)}

              / 5

            </h3>

            <p>

              {product.ratingCount}
              {" "}
              ratings

            </p>

            <div className="stars">

              {[1,2,3,4,5]
                .map(star => (

                  <span

                    key={star}

                    className={
                      star <=
                      Math.round(
                        product.averageRating
                      )

                      ? "star active"

                      : "star"
                    }
                  >

                    ★

                  </span>
              ))}

            </div>

          </div>

          <p className="description">

            {product.description}

          </p>

          <p className="stock">

            Stock:
            {" "}
            {product.stock}

          </p>

          <button
            onClick={handleAddToCart}
          >

            Add To Cart

          </button>

        </div>

      </div>

    </div>
  );
}