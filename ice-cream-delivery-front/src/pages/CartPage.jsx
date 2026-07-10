import React, { useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import http from "../api/http";
import "../css/CartPage.css";

const CartPage = ({ cart, setCart, userId }) => {
  const navigate = useNavigate();
  const [deliveryAddress, setDeliveryAddress] = useState("");
  const [note, setNote] = useState("");
  const [loading, setLoading] = useState(false);

  const subtotal = useMemo(() => {
    return cart.reduce((sum, item) => sum + item.price * (item.quantity || 1), 0);
  }, [cart]);

  const totalItems = useMemo(() => {
    return cart.reduce((sum, item) => sum + (item.quantity || 1), 0);
  }, [cart]);

  const updateQuantity = (id, qty) => {
    if (qty < 1) return;
    const updatedCart = cart.map(item => item.id === id ? { ...item, quantity: qty } : item);
    setCart(updatedCart);
    localStorage.setItem("cart", JSON.stringify(updatedCart));
    window.dispatchEvent(new Event("cartUpdated"));
  };

  const removeItem = (id) => {
    const updatedCart = cart.filter(item => item.id !== id);
    setCart(updatedCart);
    localStorage.setItem("cart", JSON.stringify(updatedCart));
    window.dispatchEvent(new Event("cartUpdated"));
  };

  const createOrder = async () => {
    if (cart.length === 0) return;
    if (!deliveryAddress.trim()) {
      alert("Please enter your delivery address.");
      return;
    }

    const dto = {
      userId,
      deliveryAddress,
      note,
      items: cart.map(item => ({
        iceCreamId: item.id,
        quantity: item.quantity || 1,
        unitPrice: item.price
      }))
    };

    try {
      setLoading(true);
      await http.post("/order", dto);
      alert("🍦 Your order has been successfully placed!");
      setCart([]);
      localStorage.setItem("cart", JSON.stringify([]));
      window.dispatchEvent(new Event("cartUpdated"));
      setDeliveryAddress("");
      setNote("");
      navigate("/");
    } catch (err) {
      console.error(err);
      alert("Something went wrong while creating the order.");
    } finally {
      setLoading(false);
    }
  };

  if (cart.length === 0) {
    return (
      <div className="cart-page">
        <div className="cart-empty">
          <div className="cart-empty-icon">🍦</div>
          <h2>Your cart is empty</h2>
          <p>Looks like you haven't added any delicious gelato yet.</p>
          <button className="continue-btn" onClick={() => navigate("/icecreamshops-page")}>
            Browse Ice Cream Shops
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="cart-page">
      <div className="cart-header">
        <span className="cart-subtitle">Premium Gelato Delivery</span>
        <h1>Your Order</h1>
        <p>Review your favourite flavours before placing the order.</p>
      </div>

      <div className="cart-layout">
        <div className="cart-items">
          {cart.map(item => (
            <div key={item.id} className="cart-card">
              <div className="cart-image">
                <img
                  src={item.image ? `data:image/jpeg;base64,${item.image}` : "https://images.unsplash.com/photo-1563805042-7684c019e1cb?auto=format&fit=crop&w=700&q=80"}
                  alt={item.name}
                />
              </div>

              <div className="cart-info">
                <div className="cart-top">
                  <div>
                    <h3>{item.name}</h3>
                    <p>{item.description || "Handcrafted premium gelato prepared fresh every day."}</p>
                  </div>
                  <div className="price">{item.price} RSD</div>
                </div>
                <div className="cart-bottom">
                  <div className="quantity">
                    <button onClick={() => updateQuantity(item.id, (item.quantity || 1) - 1)}> - </button>
                    <input type="number" min="1" value={item.quantity || 1} onChange={(e) => updateQuantity(item.id, Number(e.target.value))} />
                    <button onClick={() => updateQuantity(item.id, (item.quantity || 1) + 1)}> + </button>
                  </div>
                  <button className="remove-btn" onClick={() => removeItem(item.id)}>Remove</button>
                </div>
              </div>
            </div>
          ))}
        </div>

        <aside className="order-summary">
          <h2>Order Summary</h2>
          <div className="summary-row">
            <span>Items</span>
            <span>{totalItems}</span>
          </div>
          <div className="summary-row">
            <span>Subtotal</span>
            <span>{subtotal.toFixed(2)} RSD</span>
          </div>
          <div className="summary-row">
            <span>Delivery</span>
            <span className="free">Free</span>
          </div>
          <div className="summary-divider"></div>
          <div className="summary-total">
            <span>Total</span>
            <span>{subtotal.toFixed(2)} RSD</span>
          </div>

          <div className="summary-form">
            <label>Delivery Address</label>
            <textarea rows="3" placeholder="Enter your delivery address..." value={deliveryAddress} onChange={(e) => setDeliveryAddress(e.target.value)} />
            <label>Order Note</label>
            <textarea rows="3" placeholder="Any special requests? (optional)" value={note} onChange={(e) => setNote(e.target.value)} />
          </div>

          <button className="checkout-btn" disabled={loading} onClick={createOrder}>
            {loading ? "Placing order..." : "Place Order"}
          </button>
        </aside>
      </div>
    </div>
  );
};

export default CartPage;