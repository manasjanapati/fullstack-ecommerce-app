import {
  useEffect,
  useState
} from "react";

import AdminLayout
from "../layouts/AdminLayout";

import {

  getAllOrders,

  updateOrderStatus

} from "../services/orderService";

import "../styles/AdminOrders.css";

export default function AdminOrders() {

  const [orders, setOrders] =
    useState([]);

  useEffect(() => {

    loadOrders();

  }, []);

  const loadOrders =
    async () => {

      try {

        const data =
          await getAllOrders();

        setOrders(data);

      } catch (error) {

        console.log(error);
      }
    };

  const handleStatusChange =
    async (orderId, status) => {

      try {

        await updateOrderStatus(
          orderId,
          status
        );

        loadOrders();

      } catch (error) {

        console.log(error);
      }
    };

  return (

    <AdminLayout>

      <div className="admin-orders">

        <h1>
          Manage Orders
        </h1>

        {orders.map(order => (

          <div
            key={order.orderId}
            className="admin-order-card"
          >

            <h2>
              Order #{order.orderId}
            </h2>

            <p>
              Status:
              {order.status}
            </p>

            <p>
              Total:
              ₹{order.totalAmount}
            </p>

            <p>
                Order Number:
                {order.orderNumber}
            </p>

            <p>
                Customer:
                {order.customerName}
            </p>

            <p>
                Email:
                {order.customerEmail}
            </p>

            <p>
                Phone:
                {order.phoneNumber}
            </p>

            <p>
                Address:
                {order.shippingAddress}
            </p>

            <p>
                Date:
                {order.createdAt}
            </p>

            <div className="order-items">

              {order.items.map(item => (

                <div key={item.productName}>

                  <p>
                    {item.productName}
                  </p>

                  <p>
                    Qty:
                    {item.quantity}
                  </p>

                </div>
              ))}

            </div>

            <select

              value={order.status}

              onChange={(e) =>
                handleStatusChange(

                  order.orderId,

                  e.target.value
                )
              }
            >

              <option value="PENDING">
                PENDING
              </option>

              <option value="PROCESSING">
                PROCESSING
              </option>

              <option value="SHIPPED">
                SHIPPED
              </option>

              <option value="DELIVERED">
                DELIVERED
              </option>

              <option value="CANCELLED">
                CANCELLED
              </option>

              

            </select>

          </div>
        ))}

      </div>

    </AdminLayout>
  );
}