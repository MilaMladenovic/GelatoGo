import { useEffect, useState } from "react";
import http from "../api/http";
import IceCreamsTableRow from "../components/IceCreamsTableRow";
import "../css/IceCreams.css";

const IceCreams = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [q, setQ] = useState("");
  const [sort, setSort] = useState({ by: "id", dir: "asc" });

  const [newName, setNewName] = useState("");
  const [newDescription, setNewDescription] = useState("");
  const [newPrice, setNewPrice] = useState("");
  const [newImage, setNewImage] = useState("");
  const [previewImage, setPreviewImage] = useState("");
  const [newShopId, setNewShopId] = useState("");
  const [shops, setShops] = useState([]);
  const [selectedShop, setSelectedShop] = useState("");

  const [editingId, setEditingId] = useState(null);

  function handleImageChange(e) {

    const file = e.target.files[0];

    if (!file) return;

    const reader = new FileReader();

    reader.onload = () => {
      setPreviewImage(reader.result);
      const base64 = reader.result.split(",")[1];
      setNewImage(base64);
    };

    reader.readAsDataURL(file);

  }

  function toggleSort(col) {
    setSort((s) =>
      s.by === col
        ? { by: col, dir: s.dir === "asc" ? "desc" : "asc" }
        : { by: col, dir: "asc" }
    );
  }

  useEffect(() => {
    setLoading(true);
    http
      .get("/icecream")
      .then((res) => setData(Array.isArray(res.data) ? res.data : []))
      .catch((e) => setError(e?.response?.data?.message || e.message))
      .finally(() => setLoading(false));
    http
      .get("/icecreamshop")
      .then((res) => setShops(Array.isArray(res.data) ? res.data : []))
      .catch(console.error);

  }, []);

  useEffect(() => {
    if (editingId !== null) return;
    setNewShopId(selectedShop);
  }, [selectedShop, editingId]);

  let displayed = data.filter((r) => {

    const matchesSearch =
      r.name.toLowerCase().includes(q.toLowerCase()) ||
      r.description.toLowerCase().includes(q.toLowerCase());

    const matchesShop =
      selectedShop === "" ||
      Number(r.iceCreamShopId) === Number(selectedShop);

    return matchesSearch && matchesShop;
  });

  displayed.sort((a, b) => {
    const va = a[sort.by];
    const vb = b[sort.by];

    if (va == null) return 1;
    if (vb == null) return -1;

    if (typeof va === "number" && typeof vb === "number") {
      return sort.dir === "asc" ? va - vb : vb - va;
    }

    return sort.dir === "asc"
      ? String(va).localeCompare(String(vb))
      : String(vb).localeCompare(String(va));
  });

  async function handleDelete(id) {
    if (!window.confirm("Delete ice cream?")) return;

    try {
      await http.delete(`/icecream/${id}`);
      setData((prev) => prev.filter((r) => r.id !== id));
      alert("Ice cream deleted successfully.");
    } catch (e) {
      alert(e?.response?.data?.message || e.message);
    }
  }

  function resetForm() {
    setEditingId(null);
    setNewName("");
    setNewDescription("");
    setNewPrice("");
    setNewImage("");
    setNewShopId("");
    setPreviewImage("");
    if (selectedShop !== "") {
      setNewShopId(selectedShop);
    }
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if (
      !newName.trim() ||
      !newDescription.trim() ||
      !newPrice ||
      !newShopId
    ) {
      alert("Fill all required fields.");
      return;
    }

    try {
      if (editingId == null) {
        const res = await http.post("/icecream", {
          name: newName.trim(),
          description: newDescription.trim(),
          price: Number(newPrice),
          image: newImage || null,
          iceCreamShopId: Number(newShopId),
        });

        setData((prev) => [...prev, res.data]);
        alert("Ice cream created successfully.");
      } else {
        const res = await http.put(`/icecream/${editingId}`, {
          id: editingId,
          name: newName.trim(),
          description: newDescription.trim(),
          price: Number(newPrice),
          image: newImage || null,
          iceCreamShopId: Number(newShopId),
        });

        setData((prev) =>
          prev.map((r) => (r.id === editingId ? res.data : r))
        );

        alert("Ice cream updated successfully.");
      }

      resetForm();
    } catch (e) {
      alert(e?.response?.data?.message || e.message);
    }
  }

  function startEdit(row) {
    setEditingId(row.id);

    setNewName(row.name || "");
    setNewDescription(row.description || "");
    setNewPrice(row.price || "");
    setNewShopId(row.iceCreamShopId || "");

    if (row.image) {
      setNewImage(row.image);
      setPreviewImage(`data:image/jpeg;base64,${row.image}`);
    } else {
      setNewImage("");
      setPreviewImage("");
    }

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  }

  function cancelEdit() {
    resetForm();
  }


  return (
    <div className="container admin-wrap">

      <header className="admin-head">
        <div>
          <h1>Ice Creams</h1>
          <p className="muted">Ice Cream Overview (admin).</p>
        </div>

        <div className="row-gap">

          <input
            className="input search-input"
            placeholder="Search..."
            value={q}
            onChange={(e) => setQ(e.target.value)}
          />

          <select
            className="input filter-select"
            value={selectedShop}
            onChange={(e) => setSelectedShop(e.target.value)}
          >
            <option value="">
              All shops
            </option>

            {shops.map(shop => (
              <option
                key={shop.id}
                value={shop.id}
              >
                {shop.name}
              </option>
            ))}
          </select>

          <button
            className="btn"
            onClick={() => {
              setQ("");
              setSelectedShop("");

              if (editingId === null) {
                setNewShopId("");
              }
            }}
          >
            Reset
          </button>
        </div>

      </header> 
      <h3>
        {editingId === null ? "Create Ice Cream" : "Update Ice Cream"}
      </h3>

      <hr className="section-divider" />

      <form
        className="panel icecream-form"
        onSubmit={handleSubmit}
      >

        <div className="form-row two-columns">
          <input
            className="input"
            placeholder="Name"
            value={newName}
            onChange={(e) => setNewName(e.target.value)}
          />
          <input
            className="input"
            type="number"
            placeholder="Price"
            value={newPrice}
            onChange={(e) => setNewPrice(e.target.value)}
          />
        </div>

        <div className="form-row">
          <input
            className="input"
            placeholder="Description"
            value={newDescription}
            onChange={(e) => setNewDescription(e.target.value)}
          />
        </div>

        <div className="form-row">
          <select
            className="input shop-select"
            value={newShopId}
            onChange={(e) => setNewShopId(e.target.value)}
          >

            <option value="">
              Choose ice cream shop...
            </option>

            {shops.map((shop) => (
              <option
                key={shop.id}
                value={shop.id}
              >
                {shop.name}
              </option>
            ))}
          </select>
        </div>

        <div className="upload-section">

          <label className="upload-btn">
            📷 {editingId === null ? "Choose image" : "Change image"}
            <input
              type="file"
              accept="image/*"
              onChange={handleImageChange}
              hidden
            />
          </label> 

          <span className="upload-name">
            {previewImage ? "Image selected" : "No image selected"}
          </span>

          {previewImage && (
            <img
              src={previewImage}
              alt="Preview"
              className="preview-image"
            />
          )}

          <div className="form-actions">
            <button className="btn primary" type="submit" >
              {editingId === null  ? "Save" : "Update"}
            </button>

            {editingId != null && (
              <button className="btn" type="button" onClick={cancelEdit}>
                Cancel
              </button>
            )}
          </div>
        </div>
      </form>

      {loading && (
        <p>Loading...</p>
      )}

      {error && (
        <p style={{ color: "red" }}>{error}</p>
      )}

      <table className="table">
        <thead>
          <tr>
            <th onClick={() => toggleSort("id")}>
              Id {sort.by === "id" &&
                (sort.dir === "asc" ? "▲" : "▼")}
            </th>

            <th onClick={() => toggleSort("name")}>
              Name {sort.by === "name" &&
                (sort.dir === "asc" ? "▲" : "▼")}
            </th>

            <th onClick={() => toggleSort("description")}>
              Description {sort.by === "description" &&
                (sort.dir === "asc" ? "▲" : "▼")}
            </th>

            <th onClick={() => toggleSort("price")}>
              Price {sort.by === "price" &&
                (sort.dir === "asc" ? "▲" : "▼")}
            </th>

            <th>Image</th>

            <th>Shop</th>

            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {displayed.map((row) => (
            <IceCreamsTableRow
              key={row.id}
              id={row.id}
              name={row.name}
              description={row.description}
              price={row.price}
              image={row.image}
              shopName={shops.find(s => s.id === row.iceCreamShopId)?.name || "-"}
              onDelete={handleDelete}
              onUpdate={() => startEdit(row)}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default IceCreams;
