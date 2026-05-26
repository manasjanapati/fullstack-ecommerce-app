import {
  useEffect,
  useState
} from "react";

import ProductCard
from "../components/ProductCard";

import {

  getProducts,

  searchProducts,

  getProductsByCategory

} from "../services/productService";

import {
  getCategories
} from "../services/categoryService";

import "../styles/HomePage.css";

export default function HomePage() {

  const [products, setProducts] =
    useState([]);

  const [groupedProducts,
  setGroupedProducts] =
    useState({});

  const [categories,
  setCategories] =
    useState([]);

  const [loading, setLoading] =
    useState(true);

  const [searchKeyword,
  setSearchKeyword] =
    useState("");

  const [selectedCategory,
  setSelectedCategory] =
    useState("");

  const [sortOption,
  setSortOption] =
    useState("");

  useEffect(() => {

    loadCategories();

    loadProducts();

  }, []);

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

  const groupProductsByCategory =
    (productsList) => {

      const grouped = {};

      productsList.forEach(product => {

        const categoryName =
          product.category.name;

        if (!grouped[categoryName]) {

          grouped[categoryName] = [];
        }

        grouped[categoryName]
          .push(product);
      });

      setGroupedProducts(grouped);
  };

  const loadProducts =
    async () => {

      try {

        const data =
          await getProducts();

        setProducts(data);

        groupProductsByCategory(data);

      } catch (error) {

        console.log(error);

      } finally {

        setLoading(false);
      }
  };

  const handleSearch =
    async (keyword) => {

      setSearchKeyword(keyword);

      if (!keyword.trim()) {

        loadProducts();

        return;
      }

      try {

        const data =
          await searchProducts(
            keyword
          );

        setProducts(data);

        groupProductsByCategory(data);

      } catch (error) {

        console.log(error);
      }
  };

  const handleCategoryFilter =
    async (categoryId) => {

      setSelectedCategory(
        categoryId
      );

      if (!categoryId) {

        loadProducts();

        return;
      }

      try {

        const data =
          await getProductsByCategory(
            categoryId
          );

        setProducts(data);

        groupProductsByCategory(data);

      } catch (error) {

        console.log(error);
      }
  };

  const handleSort =
    (option) => {

      setSortOption(option);

      let sortedProducts =
        [...products];

      if (option === "lowToHigh") {

        sortedProducts.sort(

          (a, b) =>
            a.price - b.price
        );

      } else if (
        option === "highToLow"
      ) {

        sortedProducts.sort(

          (a, b) =>
            b.price - a.price
        );
      }

      setProducts(sortedProducts);

      groupProductsByCategory(
        sortedProducts
      );
  };

  if (loading) {

    return <h1>Loading...</h1>;
  }

  return (

    <div className="home-page">

      <div className="filters">

        <input
          type="text"

          placeholder="Search products..."

          value={searchKeyword}

          onChange={(e) =>
            handleSearch(
              e.target.value
            )
          }
        />

        <select

          value={selectedCategory}

          onChange={(e) =>
            handleCategoryFilter(
              e.target.value
            )
          }
        >

          <option value="">
            All Categories
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

        <select

          value={sortOption}

          onChange={(e) =>
            handleSort(
              e.target.value
            )
          }
        >

          <option value="">
            Sort By
          </option>

          <option value="lowToHigh">
            Price Low → High
          </option>

          <option value="highToLow">
            Price High → Low
          </option>

        </select>

      </div>

      <h1 className="home-title">

        Latest Products

      </h1>

      {Object.entries(groupedProducts)
        .map(([categoryName,
               categoryProducts]) => (

          <div
            key={categoryName}

            className="category-section"
          >

            <h2 className="category-title">

              {categoryName}

            </h2>

            <div className="products-grid">

              {categoryProducts.map(product => (

                <ProductCard
                  key={product.id}
                  product={product}
                />
              ))}

            </div>

          </div>
      ))}

    </div>
  );
}