import { useEffect, useState } from "react";
import http from "../api/http";
import "../css/Profile.css";

const Profile = () => {
  const [user, setUser] = useState(null);
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadData() {
      try {
        const me = await http.get("/auth/me");
        setUser(me.data);
        const myOrders = await http.get("/order/my");
        setOrders(myOrders.data);
      } catch (e) {
        setError(e?.response?.data?.message || e.message);
      } finally {
        setLoading(false);
      }
    }
    loadData();
  }, []);

  function formatDate(date) {
    return new Date(date).toLocaleDateString("en-GB", { day: "2-digit", month: "long", year: "numeric" });
  }

  function calculateOrderTotal(order) {
    return order.items.reduce((sum, item) => sum + Number(item.totalPrice), 0);
  }

  if (loading) return <div className="profile-loading">Loading profile...</div>;
  if (error) return <div className="profile-error">{error}</div>;

  return (
    <div className="profile-page">
      <div className="profile-header">
        <div className="profile-avatar">{user.firstName.charAt(0)}</div>
        <div>
          <h1>Welcome back, {user.firstName}!</h1>
          <p>Thank you for choosing GelatoGo ❤️</p>
        </div>
      </div>

      <div className="profile-card">
        <h2>Personal Information</h2>
        <div className="profile-grid">
          <div><span>First name</span><strong>{user.firstName}</strong></div>
          <div><span>Last name</span><strong>{user.lastName}</strong></div>
          <div><span>Email</span><strong>{user.email}</strong></div>
          <div><span>Username</span><strong>{user.username}</strong></div>
          <div><span>Phone</span><strong>{user.phoneNumber}</strong></div>
          <div><span>Address</span><strong>{user.address}</strong></div>
        </div>
      </div>

      <div className="orders-section">
        <h2>Order History</h2>
        {orders.length === 0 ? (
          <div className="empty-orders">
            <h3>No orders yet</h3>
            <p>Your future ice cream adventures will appear here.</p>
          </div>
        ) : (
          orders.map((order, index) => (
            <div key={order.id} className="order-card">
              <div className="order-header">
                <div>
                  <h3>Order #{index + 1}</h3>
                  <span>{formatDate(order.createdAt)}</span>
                </div>
                <div className={`status ${order.status.toLowerCase()}`}>{order.status}</div>
              </div>

              <div className="order-items">
                {order.items.map(item => (
                  <div key={item.id} className="order-item">
                    <img src={item.image ? `data:image/jpeg;base64,${item.image}` : "https://via.placeholder.com/90x90"} alt={item.iceCreamName} />
                    <div className="item-info">
                      <h4>{item.iceCreamName}</h4>
                      <p>Quantity: {item.quantity}</p>
                    </div>
                    <div className="item-price">€{Number(item.totalPrice).toFixed(2)}</div>
                  </div>
                ))}
              </div>

              <div className="order-footer">
                <div>
                  <strong>Delivery address</strong>
                  <p>{order.deliveryAddress}</p>
                </div>
                <div className="order-total">
                  Total <span>€{calculateOrderTotal(order).toFixed(2)}</span>
                </div>
              </div>
            </div>
          ))
        )}

        <div className="thanks-card">
          <h2>Thank you ❤️</h2>
          <p>
            Thank you for choosing GelatoGo. Every order supports local artisanal ice cream shops and helps us
            bring more handcrafted flavors to your table. We look forward to preparing your next favorite scoop!
          </p>
        </div>
      </div>
    </div>
  );
};

export default Profile;