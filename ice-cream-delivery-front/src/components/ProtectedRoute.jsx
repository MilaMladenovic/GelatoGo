import React from "react";
import "../css/auth.css";

export default function ProtectedRoute({ children, role }) {
  const token = localStorage.getItem("token");

  // ako korisnik nije ulogovan
  if (!token) {
    return (
      <div className="auth-wrap premium-gate-bg">
        <div className="gate-card">
          <div className="gate-icon-wrapper">
            <span className="gate-icon" role="img" aria-label="ice cream">🍦</span>
          </div>

          <div className="gate-header">
            <span className="premium-badge">GelatoGo Experience</span>
            <h2>Authentication Required</h2>
            <p className="gate-subtitle">
              Unlock exclusive access to artisanal IceCreamShops and get gourmet IceCreams delivered fresh to your doorstep.
            </p>
          </div>

          <div className="gate-actions">
            <a href="/login" className="btn-primary-premium text-center-link">
              <span>Sign In to Your Account</span>
            </a>
            <a href="/register" className="btn-secondary-premium text-center-link">
              Create New Account
            </a>
          </div>

          <div className="gate-footer">
            <p>By continuing, you agree to our premium delivery terms.</p>
          </div>
        </div>
      </div>
    );
  }

  // ako je ulogovan, provera trenutne uloge korisnika
  let userRole = "USER";
  try {
    const me = JSON.parse(localStorage.getItem("me"));
    userRole = me?.role || "USER";
  } catch (e) {
    console.error("Error while loading user's role", e);
  }

  // ako ruta zahteva specificnu ulogu, a korisnik je nema
  if (role && userRole !== role) {

    // dinamicki tekstovi u zavisnosti od toga koja se uloga trazi za stranicu
    const isTargetingAdmin = role === "ADMIN";

    const title = isTargetingAdmin ? "Admin Section Only" : "Customers Only";
    const subtitle = isTargetingAdmin
      ? "Sorry, you do not have the required permissions to access this management console. This area is reserved for GelatoGo administrators."
      : "Administrators cannot access customer sections like the cart or profile. Please log in with a customer account to place orders.";

    return (
      <div className="auth-wrap premium-gate-bg">
        <div className="gate-card denided-card">
          <div className="gate-icon-wrapper alert-icon-bg">
            <span className="gate-icon" role="img" aria-label="lock">🔒</span>
          </div>

          <div className="gate-header">
            <span className="premium-badge alert-badge">Access Restricted</span>
            <h2>{title}</h2>
            <p className="gate-subtitle">{subtitle}</p>
          </div>

          <div className="gate-actions">
            <a href="/" className="btn-primary-premium text-center-link">
              <span>Back to Homepage</span>
            </a>
          </div>
        </div>
      </div>
    );
  }

  return children;
}