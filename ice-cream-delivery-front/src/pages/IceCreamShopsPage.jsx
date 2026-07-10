import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import http from "../api/http";
import "../css/IceCreamShopsPage.css";

const IceCreamShopsPage = () => {
  const [shops, setShops] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    http.get("/icecreamshop") 
      .then((res) => {
        setShops(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching ice cream shops:", err);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return (
      <div className="shops-container">
        <div className="shops-header">
          <div className="skeleton title-skeleton"></div>
          <div className="skeleton subtitle-skeleton"></div>
        </div>
        <div className="shops-grid">
          {[1, 2, 3, 4].map((n) => (
            <div key={n} className="shop-card skeleton-card">
              <div className="skeleton image-skeleton"></div>
              <div className="skeleton content-skeleton"></div>
            </div>
          ))}
        </div>
      </div>
    );
  }

  return (
    <div className="shops-container">
      <div className="shops-header">
        <h1>GelatoGo Artisanal Shops</h1>
        <p>Discover premium ice cream creators in your area, crafted with love and fresh ingredients.</p>
      </div>

      {shops.length === 0 ? (
        <div className="empty-state">
          <h3>No shops available right now</h3>
          <p>Check back shortly for premium gelato experiences!</p>
        </div>
      ) : (
        <div className="shops-grid">
          {shops.map((shop) => (
            <div
              key={shop.id}
              className="shop-card"
              onClick={() => navigate(`/icecreams-page/${shop.id}`)}
            >
              <div className="shop-thumb">
                <img
                  src={
                    shop.image
                      ? `data:image/jpeg;base64,${shop.image}`
                      : "https://images.unsplash.com/photo-1501443715940-a10640c10f6a?auto=format&fit=crop&w=600&q=80"
                  }
                  alt={shop.name}
                />
                <div className="shop-badge">Premium</div>
              </div>
              <div className="shop-content">
                <span className="shop-category">Gelateria</span>
                <h3>{shop.name}</h3>
                <div className="shop-meta">
                  <span className="shop-address">
                    <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path><circle cx="12" cy="10" r="3"></circle></svg>
                    {shop.address}
                  </span>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default IceCreamShopsPage;