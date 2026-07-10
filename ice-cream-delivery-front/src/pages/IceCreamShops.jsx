import { useEffect, useState } from "react"
import http from "../api/http";
import IceCreamShopsTableRow from "../components/IceCreamShopsTableRow"
import '../css/IceCreamShops.css'


const IceCreamShops = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [q, setQ] = useState("");
  const [sort, setSort] = useState({ by: "id", dir: "asc" });
  const [newName, setNewName] = useState("");
  const [newAddress, setNewAddress] = useState("");
  const [editingId, setEditingId] = useState(null);
  const [imageBase64, setImageBase64] = useState("");
  const [previewImage, setPreviewImage] = useState("");


  function handleImageChange(e) {

    const file = e.target.files[0];

    if (!file) return;

    const reader = new FileReader();

    reader.onloadend = () => {
      const base64 = reader.result.split(",")[1];
      setImageBase64(base64);
      setPreviewImage(reader.result);
    };
    reader.readAsDataURL(file);
  }

  function toggleSort(col) {
    setSort((s) =>
      s.by === col ? { by: col, dir: s.dir === "asc" ? "desc" : "asc" } : { by: col, dir: "asc" }
    );
  }

  useEffect(() => {
    setLoading(true);
    http
      .get("/icecreamshop")
      .then((res) => setData(Array.isArray(res.data) ? res.data : []))
      .catch((e) => setError(e?.response?.data?.message || e.message))
      .finally(() => setLoading(false));
  }, []);

  let displayed = data.filter(
    (r) =>
      r.name.toLowerCase().includes(q.toLowerCase()) ||
      r.address.toLowerCase().includes(q.toLowerCase())
  );

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


  // CRUD

  async function handleDelete(id) {
    if (!window.confirm("Delete ice cream shop?")) return;
    try {
      await http.delete(`/icecreamshop/${id}`);
      setData((prev) => prev.filter((r) => r.id !== id));
      alert("Ice cream shop deleted successfully.")
    } catch (e) {
      alert(e?.response?.data?.message || e.message);
    }
  }

  async function handleSubmit(e) {
    e.preventDefault();
    if (!newName.trim() || !newAddress.trim()) {
      alert("Fill in name and address.");
      return;
    }
    try {
      if (editingId == null) {
        // CREATE (POST)
        const res = await http.post("/icecreamshop", {

          name: newName.trim(),
          address: newAddress.trim(),
          image: imageBase64

        });
        setData((prev) => [...prev, res.data]);
        alert("Ice cream shop created successfully.")
      } else {
        // UPDATE (PUT)
        const res = await http.put(`/icecreamshop/${editingId}`, {

          id: editingId,
          name: newName.trim(),
          address: newAddress.trim(),
          image: imageBase64

        });
        setData((prev) => prev.map((r) => (r.id === editingId ? res.data : r)));
        alert("Ice cream shop updated successfully.")
      }

      resetForm();

    } catch (e) {
      alert(e?.response?.data?.message || e.message);
    }
  }

  function startEdit(row) {
    setEditingId(row.id);

    setNewName(row.name || "");
    setNewAddress(row.address || "");

    if (row.image) {
      setImageBase64(row.image);
      setPreviewImage(`data:image/jpeg;base64,${row.image}`);
    } else {
      setImageBase64("");
      setPreviewImage("");
    }

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  }

  function cancelEdit() {
    resetForm()
  }

  function resetForm() {
    setEditingId(null);
    setNewName("");
    setNewAddress("");
    setImageBase64("");
    setPreviewImage("");
  }

  return (
    <div className="container admin-wrap">

      <header className="admin-head">
        <div>
          <h1>Ice Cream Shops</h1>
          <p className="muted">Ice Cream Shops Overview (admin).</p>
        </div>
        <div className="row-gap">
          <input
            className="input search-input"
            placeholder="Search (name, address, city...)"
            value={q}
            onChange={(e) => {
              setQ(e.target.value);
              //  setPage(1)
            }} />
          <button className="btn" onClick={() => setQ("")}>Reload</button>
        </div>
      </header>

      <h3>
        {editingId === null
          ? "Create ice cream shop"
          : "Update ice cream shop"}
      </h3>
      <form className="panel" onSubmit={handleSubmit} style={{ marginBottom: 12, display: "flex", gap: 8, alignItems: "center", flexWrap: "wrap" }}>
        <input
          className="input"
          placeholder="Name"
          value={newName}
          onChange={(e) => setNewName(e.target.value)}
        />
        <input
          className="input"
          placeholder="Address"
          value={newAddress}
          onChange={(e) => setNewAddress(e.target.value)}
        />
        <label className="upload-btn">
          {editingId === null
            ? "📷 Choose image"
            : "📷 Change image"}

          <input
            type="file"
            accept="image/*"
            onChange={handleImageChange}
            hidden
          />
        </label>

        <span className="upload-name">
          {editingId === null
            ? (previewImage ? "Image selected" : "No image selected")
            : (previewImage ? "Current image" : "No image")}
        </span>
        {previewImage && (
          <img
            src={previewImage}
            alt=""
            className="preview-image"
          />
        )}
        <button className="btn primary" type="submit">
          {editingId === null ? "Save" : "Update"}
        </button>
        {editingId != null && (
          <button className="btn" type="button" onClick={cancelEdit}>Cancel</button>
        )}
      </form>

      <table className="table">
        <thead>
          <tr>
            <th onClick={() => toggleSort("id")}>
              Id {sort.by === "id" && (sort.dir === "asc" ? "▲" : "▼")}
            </th>
            <th onClick={() => toggleSort("name")}>
              Name {sort.by === "name" && (sort.dir === "asc" ? "▲" : "▼")}
            </th>
            <th onClick={() => toggleSort("address")}>
              Address {sort.by === "address" && (sort.dir === "asc" ? "▲" : "▼")}
            </th>
            <th className="table-image">Image</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {displayed.map((row) => (
            <IceCreamShopsTableRow
              key={row.id}
              id={row.id}
              name={row.name}
              address={row.address}
              image={row.image}
              onDelete={handleDelete}
              onUpdate={() => startEdit(row)}
            />
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default IceCreamShops;