import api from "./api";

export const checkout =
  async (checkoutData) => {

    const response =
      await api.post(

        "/orders/checkout",

        checkoutData
      );

    return response.data;
};

export const getOrders =
  async () => {

    const response =
      await api.get("/orders");

    return response.data;
};

export const getAllOrders =
  async () => {

    const response =
      await api.get(
        "/orders/admin"
      );

    return response.data;
};

export const updateOrderStatus =
  async (orderId, status) => {

    const response =
      await api.put(

        `/orders/${orderId}/status`,

        { status }
      );

    return response.data;
};