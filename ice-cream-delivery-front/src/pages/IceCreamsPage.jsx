import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import http from "../api/http";
import "../css/IceCreamsPage.css";

const IceCreamsPage = ({ addToCart }) => {
  const { id } = useParams(); // shopId
  const navigate = useNavigate();
  const [iceCreams, setIceCreams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [addedItems, setAddedItems] = useState({}); 

  useEffect(() => {
    http.get(`/icecream/icecreamshop/${id}`)
      .then((res) => {
        setIceCreams(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching ice creams:", err);
        setLoading(false);
      });
  }, [id]);

  const handleAddToCart = (item) => {
    addToCart(item);

    setAddedItems((prev) => ({ ...prev, [item.id]: true }));
    setTimeout(() => {
      setAddedItems((prev) => ({ ...prev, [item.id]: false }));
    }, 2000);
  };

  if (loading) {
    return (
      <div className="ice-creams-container">
        <div className="ice-creams-grid">
          {[1, 2, 3].map((n) => (
            <div key={n} className="ice-cream-card skeleton-card">
              <div className="skeleton ice-cream-image-skeleton"></div>
              <div className="skeleton ice-cream-content-skeleton"></div>
            </div>
          ))}
        </div>
      </div>
    );
  }

  return (
    <div className="ice-creams-container">
      <button className="back-btn" onClick={() => navigate(-1)}>
        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><line x1="19" y1="12" x2="5" y2="12"></line><polyline points="12 19 5 12 12 5"></polyline></svg>
        Back to Shops
      </button>

      <h2 className="menu-title">Our Gelato Menu</h2>

      {iceCreams.length === 0 ? (
        <div className="empty-state">
          <h3>No flavours available in this shop</h3>
          <p>We are currently churning fresh batches. Come back soon!</p>
        </div>
      ) : (
        <div className="ice-creams-grid">
          {iceCreams.map((iceCream) => (
            <div key={iceCream.id} className="ice-cream-card">
              <div className="ice-cream-image">
                <img
                  src={
                    iceCream.image
                      ? `data:image/jpeg;base64,${iceCream.image}`
                      : "https://images.unsplash.com/photo-1563805042-7684c019e1cb?auto=format&fit=crop&w=600&q=80"
                  }
                  alt={iceCream.name}
                />
              </div>

              <div className="ice-cream-info">
                <div className="ice-cream-header">
                  <h4>{iceCream.name}</h4>
                  <span className="price">{iceCream.price} RSD</span>
                </div>

                <p className="description">{iceCream.description || "Artisanal recipe handcrafted daily using zero artificial colorants."}</p>

                <div className="ice-cream-footer">
                  {(() => {
                    const tags = ["Artisanal", "Freshly Made", "100% Natural", "Premium Gelato"];
                    const tagIndex = (iceCream.id || iceCream.name.length) % tags.length;
                    return <span className="tag">{tags[tagIndex]}</span>;
                  })()}
                  <button
                    onClick={() => handleAddToCart(iceCream)}
                    className={`btn-add-to-cart ${addedItems[iceCream.id] ? "added" : ""}`}
                    disabled={addedItems[iceCream.id]}
                  >
                    {addedItems[iceCream.id] ? (
                      <>
                        <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="3"><polyline points="20 6 9 17 4 12"></polyline></svg>
                        Added!
                      </>
                    ) : (
                      "Add to Order"
                    )}
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default IceCreamsPage;