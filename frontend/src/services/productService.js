import api from "./api";

export const getProducts =
  async () => {

    const response =
      await api.get("/products");

    return response.data;
};


export const createProduct =
  async (productData) => {

    const response =
      await api.post(
        "/products",
        productData
      );

    return response.data;
};

export const deleteProduct =
  async (productId) => {

    const response =
      await api.delete(
        `/products/${productId}`
      );

    return response.data;
};

export const updateProduct =
  async (id, productData) => {

    const response =
      await api.put(
        `/products/${id}`,
        productData
      );

    return response.data;
};

export const getProductById =
  async (id) => {

    const response =
      await api.get(
        `/products/${id}`
      );

    return response.data;
};


export const searchProducts =
  async (keyword) => {

    const response =
      await api.get(

        `/products/search?keyword=${keyword}`
      );

    return response.data.content;
};

export const getProductsByCategory =
  async (categoryId) => {

    const response =
      await api.get(

        `/products/category/${categoryId}`
      );

    return response.data.content;
};

export const rateProduct =
  async (productId, rating) => {

    const response =
      await api.post(

        `/products/${productId}/rating`,

        { rating }
      );

    return response.data;
};