import {
  useEffect,
  useState
} from "react";

import {
  useNavigate
} from "react-router-dom";

import {
  getCart,
  removeCartItem
} from "../services/cartService";

import {
  checkout
} from "../services/orderService";

import "../styles/CartPage.css";

export default function CartPage() {

  const [cart, setCart] =
    useState(null);

  const navigate =
    useNavigate();

  useEffect(() => {

    loadCart();

  }, []);

  const loadCart =
    async () => {

      try {

        const data =
          await getCart();

        setCart(data);

      } catch (error) {

        console.log(error);
      }
    };

  const handleRemove =
    async (productId) => {

      try {

        const data =
          await removeCartItem(
            productId
          );

        setCart(data);

      } catch (error) {

        console.log(error);
      }
    };
  const [shippingAddress,
    setShippingAddress] =
      useState("");

    const [phoneNumber,
    setPhoneNumber] =
      useState("");

  const handleCheckout =
    async () => {

      try {

        await checkout({

          shippingAddress,

          phoneNumber
        });

        alert(
          "Order placed successfully"
        );

        navigate("/orders");

      } catch (error) {

        console.log(error);

        alert(
          "Checkout failed"
        );
      }
    };

  if (!cart) {

    return <h1>Loading...</h1>;
  }

  return (

    <div className="cart-page">

      <h1>Your Cart</h1>

      {cart.items.length === 0 ? (

        <h2>Cart is empty</h2>

      ) : (

        <>
          {cart.items.map(item => (

            <div
              key={item.productId}
              className="cart-item"
            >

              <div>

                <h3>
                  {item.productName}
                </h3>

                <p>
                  Quantity:
                  {item.quantity}
                </p>

                <p>
                  ₹{item.totalPrice}
                </p>

              </div>

              <input
                type="text"

                placeholder="Shipping Address"

                value={shippingAddress}

                onChange={(e) =>
                  setShippingAddress(
                    e.target.value
                  )
                }
              />

              <input
                type="text"

                placeholder="Phone Number"

                value={phoneNumber}

                onChange={(e) =>
                  setPhoneNumber(
                    e.target.value
                  )
                }
              />

              <button
                onClick={() =>
                  handleRemove(
                    item.productId
                  )
                }
              >
                Remove
              </button>

            </div>
          ))}

          <h2>
            Total:
            ₹{cart.grandTotal}
          </h2>

          

          <button
            className="checkout-btn"

            onClick={handleCheckout}
          >
            Checkout
          </button>
        </>
      )}

    </div>
  );
}