import {
  useEffect,
  useState
} from "react";

import {
  rateProduct
} from "../services/productService";

import {
  getOrders
} from "../services/orderService";

import "../styles/OrdersPage.css";

export default function OrdersPage() {

  const [orders, setOrders] =
    useState([]);

  const [ratedProducts,
  setRatedProducts] =
    useState({});

  useEffect(() => {

    loadOrders();

  }, []);

  const loadOrders =
    async () => {

      try {

        const data =
          await getOrders();

        setOrders(data);

      } catch (error) {

        console.log(error);
      }
  };

  const handleRating =
    async (
      productId,
      rating
    ) => {

      if (!productId) {

        alert(
          "Invalid product"
        );

        return;
      }

      try {

        await rateProduct(
          productId,
          rating
        );

        setRatedProducts({

          ...ratedProducts,

          [productId]: rating
        });

        alert(
          "Rating submitted"
        );

      } catch (error) {

        console.log(error);

        alert(
          "Rating failed"
        );
      }
  };

  return (

    <div className="orders-page">

      <h1>
        My Orders
      </h1>

      {orders.length === 0 && (

        <p>
          No orders found
        </p>
      )}

      {orders.map(order => (

        <div
          key={order.orderId}

          className="order-card"
        >

          <div className="order-header">

            <h2>

              Order #
              {order.orderNumber}

            </h2>

            <span className="order-status">

              {order.status}

            </span>

          </div>

          <p>

            <strong>Total:</strong>

            ₹{order.totalAmount}

          </p>

          <p>

            <strong>Date:</strong>

            {" "}

            {new Date(
              order.createdAt
            ).toLocaleString()}

          </p>

          <div className="order-items">

            {order.items.map(item => (

              <div

                key={`${order.orderId}-${item.productId}`}

                className="order-item"
              >

                <img

                  src={
                    item.imageUrl ||
                    "https://via.placeholder.com/150"
                  }

                  alt={item.productName}
                />

                <div className="order-item-info">

                  <h3>

                    {item.productName}

                  </h3>

                  <p>

                    Quantity:
                    {item.quantity}

                  </p>

                  <p>

                    Price:
                    ₹{item.price}

                  </p>

                  <div className="order-rating">

                    <p>

                      Rate this product

                    </p>

                    <div className="stars">

                      {[1,2,3,4,5]
                        .map(star => (

                          <span

                            key={star}

                            className={
                              star <=
                              (
                                ratedProducts[
                                  item.productId
                                ] || 0
                              )

                              ? "star active"

                              : "star"
                            }

                            onClick={() =>
                              handleRating(

                                item.productId,

                                star
                              )
                            }
                          >

                            ★

                          </span>
                      ))}

                    </div>

                  </div>

                </div>

              </div>
            ))}

          </div>

        </div>
      ))}

    </div>
  );
}