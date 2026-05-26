import api from "./api";

export const addToCart =
  async (cartData) => {

    const response =
      await api.post(
        "/cart/add",
        cartData
      );

    return response.data;
};

export const getCart =
  async () => {

    const response =
      await api.get("/cart");

    return response.data;
};

export const updateCartItem =
  async (cartData) => {

    const response =
      await api.put(
        "/cart/update",
        cartData
      );

    return response.data;
};

export const removeCartItem =
  async (productId) => {

    const response =
      await api.delete(
        `/cart/remove/${productId}`
      );

    return response.data;
};

export const clearCart =
  async () => {

    const response =
      await api.delete(
        "/cart/clear"
      );

    return response.data;
};