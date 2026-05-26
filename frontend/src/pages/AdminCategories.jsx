import {
  useEffect,
  useState
} from "react";

import AdminLayout
from "../layouts/AdminLayout";

import api
from "../services/api";

import "../styles/AdminCategories.css";

export default function AdminCategories() {

  const [categories, setCategories] =
    useState([]);

  const [name, setName] =
    useState("");

  const [editingId, setEditingId] =
    useState(null);

  useEffect(() => {

    loadCategories();

  }, []);

  const loadCategories =
    async () => {

      try {

        const response =
          await api.get(
            "/categories"
          );

        setCategories(
          response.data
        );

      } catch (error) {

        console.log(error);
      }
    };

  const resetForm = () => {

    setName("");

    setEditingId(null);
  };

  const handleSubmit =
    async (e) => {

      e.preventDefault();

      try {

        if (editingId) {

          await api.put(

            `/categories/${editingId}`,

            { name }
          );

          alert(
            "Category updated"
          );

        } else {

          await api.post(

            "/categories",

            { name }
          );

          alert(
            "Category created"
          );
        }

        resetForm();

        loadCategories();

      } catch (error) {

        console.log(error);

        alert(
          "Operation failed"
        );
      }
    };

  const handleEdit =
    (category) => {

      setEditingId(category.id);

      setName(category.name);
    };

  const handleDelete =
    async (id) => {

      const confirmed =
        window.confirm(
          "Delete category?"
        );

      if (!confirmed) return;

      try {

        await api.delete(
          `/categories/${id}`
        );

        loadCategories();

      } catch (error) {

        console.log(error);

        alert(
          "Delete failed"
        );
      }
    };

  return (

    <AdminLayout>

      <div className="admin-categories">

        <h1>
          Manage Categories
        </h1>

        <form
          className="category-form"

          onSubmit={handleSubmit}
        >

          <input
            type="text"

            placeholder="Category Name"

            value={name}

            onChange={(e) =>
              setName(
                e.target.value
              )
            }
          />

          <button type="submit">

            {editingId
              ? "Update Category"
              : "Create Category"}

          </button>

        </form>

        <table>

          <thead>

            <tr>

              <th>ID</th>

              <th>Name</th>

              <th>Actions</th>

            </tr>

          </thead>

          <tbody>

            {categories.map(category => (

              <tr key={category.id}>

                <td>
                  {category.id}
                </td>

                <td>
                  {category.name}
                </td>

                <td>

                  <button
                    className="edit-btn"

                    onClick={() =>
                      handleEdit(
                        category
                      )
                    }
                  >

                    Edit

                  </button>

                  <button
                    className="delete-btn"

                    onClick={() =>
                      handleDelete(
                        category.id
                      )
                    }
                  >

                    Delete

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