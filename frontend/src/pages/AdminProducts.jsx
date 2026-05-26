import {
  useEffect,
  useState
} from "react";

import {
  uploadImage
} from "../services/uploadService";

import AdminLayout
from "../layouts/AdminLayout";

import {
    getProducts,
    createProduct,
    deleteProduct,
    updateProduct
} from "../services/productService";

import {
  getCategories
} from "../services/categoryService";

import "../styles/AdminProducts.css";

export default function AdminProducts() {

  const [products, setProducts] =
    useState([]);

  const [categories, setCategories] =
    useState([]);

  const [imageFile,setImageFile] = useState(null);

  const [editingId,setEditingId] = useState(null);

  const [formData, setFormData] =
    useState({

      name: "",

      description: "",

      price: "",

      stock: "",

      imageUrl: "",

      categoryId: ""
    });

  useEffect(() => {

    loadProducts();

    loadCategories();

  }, []);

  const loadProducts =
    async () => {

      try {

        const data =
          await getProducts();

        setProducts(data);

      } catch (error) {

        console.log(error);
      }
    };

  const loadCategories =
    async () => {

      try {

        const data =
          await getCategories();

        setCategories(data);

      } catch (error) {

        console.log(error);
      }
    };

  const handleChange =
    (e) => {

      setFormData({

        ...formData,

        [e.target.name]:
          e.target.value
      });
    };

    const handleSubmit =
    async (e) => {

        e.preventDefault();

        try {

        let imageUrl =
            formData.imageUrl;

        if (imageFile) {

            imageUrl =
            await uploadImage(
                imageFile
            );
        }

        const productPayload = {

            ...formData,

            imageUrl,

            price:
            Number(formData.price),

            stock:
            Number(formData.stock),

            categoryId:
            Number(formData.categoryId)
        };

        if (editingId) {

            await updateProduct(

            editingId,

            productPayload
            );

            alert(
            "Product updated"
            );

        } else {

            await createProduct(
            productPayload
            );

            alert(
            "Product created"
            );
        }

        resetForm();

        loadProducts();

        } catch (error) {

        console.log(error);

        alert(
            "Operation failed"
        );
        }
    };

  const handleDelete =
    async (id) => {

      try {

        await deleteProduct(id);

        loadProducts();

      } catch (error) {

        console.log(error);
      }
    };

    const handleEdit =
    (product) => {

        setEditingId(product.id);

        setFormData({

        name:
            product.name,

        description:
            product.description,

        price:
            product.price,

        stock:
            product.stock,

        imageUrl:
            product.imageUrl,

        categoryId:
            product.category.id
        });
    };

    const resetForm = () => {

    setFormData({

        name: "",

        description: "",

        price: "",

        stock: "",

        imageUrl: "",

        categoryId: ""
    });

    setImageFile(null);

    setEditingId(null);
    };

  return (

    <AdminLayout>

      <div className="admin-products">

        <h1>
          Manage Products
        </h1>

        <form
          className="product-form"

          onSubmit={handleSubmit}
        >

          <input
            type="text"
            name="name"
            placeholder="Product Name"

            value={formData.name}

            onChange={handleChange}
          />

          <input
            type="text"
            name="description"
            placeholder="Description"

            value={formData.description}

            onChange={handleChange}
          />

          <input
            type="number"
            name="price"
            placeholder="Price"

            value={formData.price}

            onChange={handleChange}
          />

          <input
            type="number"
            name="stock"
            placeholder="Stock"

            value={formData.stock}

            onChange={handleChange}
          />

            <div className="file-upload">

            <input
                type="file"

                onChange={(e) =>
                setImageFile(
                    e.target.files[0]
                )
                }
            />

            {imageFile && (

                <>
                <p className="file-name">

                    Selected:
                    {imageFile.name}

                </p>

                <img

                    src={
                    URL.createObjectURL(
                        imageFile
                    )
                    }

                    alt="Preview"

                    className="preview-image"
                />
                </>
            )}

            {formData.imageUrl && !imageFile && (

                <img
                src={formData.imageUrl}

                alt="Current"

                className="preview-image"
                />
            )}

            </div>

          <select
            name="categoryId"

            value={formData.categoryId}

            onChange={handleChange}
          >

            <option value="">
              Select Category
            </option>

            {categories.map(category => (

              <option
                key={category.id}

                value={category.id}
              >

                {category.name}

              </option>
            ))}

          </select>

          <button type="submit">
            {editingId
            ? "Update Product"
            : "Create Product"}
          </button>

        </form>

        <table>

        <thead>

        <tr>

            <th>ID</th>

            <th>Image</th>

            <th>Name</th>

            <th>Price</th>

            <th>Stock</th>

            <th>Category</th>

            <th>Actions</th>

        </tr>

        </thead>

          <tbody>

            {products.map(product => (

                <tr key={product.id}>

                <td>
                    {product.id}
                </td>

                <td>

                    <img
                    src={product.imageUrl}

                    alt={product.name}

                    className="product-table-image"
                    />

                </td>

                <td>
                    {product.name}
                </td>

                <td>
                    ₹{product.price}
                </td>

                <td>
                    {product.stock}
                </td>

                <td>
                    {product.category.name}
                </td>

                <td>

                    <button
                    type="button"

                    onClick={() =>
                        handleDelete(
                        product.id
                        )
                    }
                    >
                    Delete
                    </button>

                    <button
                    type="button"

                    className="edit-btn"

                    onClick={() =>
                        handleEdit(product)
                    }
                    >

                    Edit

                    </button>

                </td>

                </tr>
            ))}

            </tbody>

        </table>

      </div>

    </AdminLayout>
  );
}