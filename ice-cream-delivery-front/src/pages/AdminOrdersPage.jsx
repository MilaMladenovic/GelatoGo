import { useEffect, useState, Fragment } from "react";
import http from "../api/http";
import "../css/AdminOrdersPage.css";

const STATUSES = ["CREATED", "CONFIRMED", "COMPLETED", "CANCELED"];

export default function AdminOrdersPage() {
  const [orders, setOrders] = useState([]);
  const [products, setProducts] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const formatDate = (date) =>
    date ? new Date(date).toLocaleString("en-GB", { day: "2-digit", month: "short", year: "numeric", hour: "2-digit", minute: "2-digit" }) : "-";

  const formatPrice = (value) => `${Number(value || 0).toLocaleString()} RSD`;
  const totalPrice = (items = []) => items.reduce((sum, i) => sum + i.quantity * i.unitPrice, 0);
  const getName = (id) => products[id]?.name || `Ice Cream #${id}`;

  async function loadOrders() {
    try {
      setLoading(true);
      const [ordersRes, iceCreamRes] = await Promise.all([
        http.get("/order"),
        http.get("/icecream"),
      ]);

      const map = {};
      (iceCreamRes.data || []).forEach((i) => (map[i.id] = i));

      setProducts(map);
      setOrders(ordersRes.data || []);
      setError("");
    } catch (e) {
      console.error(e);
      setError("Unable to load orders. Please try again.");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadOrders();
  }, []);

  async function deleteOrder(id) {
    if (!window.confirm(`Delete order #${id}? This action cannot be undone.`)) return;
    try {
      await http.delete(`/order/${id}`);
      setOrders((prev) => prev.filter((o) => o.id !== id));
    } catch (e) {
      alert("Unable to delete this order.");
    }
  }

  async function updateStatus(id, status) {
    try {
      await http.patch(`/order/${id}/status?status=${status}`);
      setOrders((prev) => prev.map((o) => o.id === id ? { ...o, status } : o));
    } catch {
      alert("Failed to update order status.");
    }
  }

  function toggleItems(id) {
    setOrders((prev) => prev.map((o) => o.id === id ? { ...o, open: !o.open } : o));
  }

  if (loading) return <div className="admin-orders-loading">Loading orders...</div>;
  if (error) return <div className="admin-orders-error">{error}</div>;

  return (
    <div className="admin-orders-page">
      <header className="admin-orders-header">
        <div>
          <h1>Orders Management</h1>
          <p>Review customer orders, update their status and manage completed purchases.</p>
        </div>
        <button className="btn primary" onClick={loadOrders}>Refresh Orders</button>
      </header>

      {orders.length === 0 ? (
        <div className="orders-empty">
          <h3>No orders found</h3>
          <p>New customer orders will appear here.</p>
        </div>
      ) : (
        <div className="orders-card">
          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Customer</th>
                <th>Created</th>
                <th>Status</th>
                <th className="right">Items</th>
                <th className="right">Total</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((order) => (
                <Fragment key={order.id}>
                  <tr>
                    <td>#{order.id}</td>
                    <td>
                      {order.user?.firstName
                        ? `${order.user.firstName} ${order.user.lastName}`
                        : `User #${order.userId ?? "-"}`}
                    </td>
                    <td>{formatDate(order.createdAt)}</td>
                    <td>
                      <div className={`status-badge status-${order.status.toLowerCase()}`}>{order.status}</div>
                      <select className="status-select" value={order.status} onChange={(e) => updateStatus(order.id, e.target.value)}>
                        {STATUSES.map((s) => (
                          <option key={s}>{s}</option>
                        ))}
                      </select>
                    </td>
                    <td className="right">{order.items?.length || 0}</td>
                    <td className="right">{formatPrice(totalPrice(order.items))}</td>
                    <td className="actions">
                      <button className="btn" onClick={() => toggleItems(order.id)}>
                        {order.open ? "Hide Items" : "View Items"}
                      </button>
                      <button className="btn danger" onClick={() => deleteOrder(order.id)}>Delete</button>
                    </td>
                  </tr>
                  {order.open && (
                    <tr className="items-row">
                      <td colSpan="7">
                        <div className="order-items">
                          {order.items?.map((item) => (
                            <div key={item.id} className="order-item">
                              <div>
                                <strong>{getName(item.iceCreamId || item.productId)}</strong>
                                <span style={{ color: "var(--muted)", fontSize: "0.85rem" }}>
                                  Unit Price: {formatPrice(item.unitPrice)}
                                </span>
                              </div>
                              <div>
                                x {item.quantity}
                              </div>
                            </div>
                          ))}
                        </div>
                      </td>
                    </tr>
                  )}
                </Fragment>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}