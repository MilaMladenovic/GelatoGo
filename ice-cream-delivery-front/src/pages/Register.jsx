import React, { useState } from "react";
import http from "../api/http";
import "../css/auth.css";

export default function Register({ onSuccess }) {
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: "",
    firstName: "",
    lastName: "",
    address: "",
    phoneNumber: ""
  });
  
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState("");
  const [ok, setOk] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setErr(""); 
    setOk("");
    setLoading(true);
    try {
      await http.post("/auth/register", form);
      setOk("Verification sent! Please check your email inbox to activate your account.");
      if (typeof onSuccess === "function") onSuccess();
    } catch (e) {
      setErr(e?.response?.data?.message || "Registration failed. Please review your details.");
    } finally {
      setLoading(false);
    }
  }

  const updateField = (field, value) => {
    setForm((f) => ({ ...f, [field]: value }));
  };

  return (
    <div className="split-layout-wrap">
      <div className="auth-sidebar">
        <div className="sidebar-content">
          <div className="brand-logo">
            <span className="logo-emoji">🍦</span>
            <span className="logo-text">GelatoGo</span>
          </div>
          
          <div className="sidebar-hero-text">
            <h1>Join the elite world of Gelato lovers.</h1>
            <p>Gain access to limited-edition batches, seasonal flavors, and bespoke gourmet offerings from top local shops.</p>
          </div>

          <div className="feature-list">
            <div className="feature-item">
              <div className="feature-icon">🎟️</div>
              <div className="feature-info">
                <h4>Member-Only Flavors</h4>
                <p>Unlock early access to special formulas from top IceCreamShops.</p>
              </div>
            </div>
            <div className="feature-item">
              <div className="feature-icon">📈</div>
              <div className="feature-info">
                <h4>Smart Recommendations</h4>
                <p>Our AI learns your palette to suggest the perfect IceCreams.</p>
              </div>
            </div>
          </div>
        </div>
        <div className="sidebar-footer">
          © 2026 GelatoGo Inc. All rights reserved.
        </div>
      </div>

      <div className="auth-main-panel">
        <div className="auth-glass-card register-card-compact fade-in">
          <div className="mobile-logo-header">
            <span className="logo-emoji">🍦</span>
            <span className="logo-text">GelatoGo</span>
          </div>

          <div className="card-header">
            <h2>Create your account</h2>
            <p className="muted">Experience the future of gourmet ice cream delivery</p>
          </div>

          {err && (
            <div className="auth-alert fade-in">
              <span className="alert-icon">⚠️</span>
              <p>{err}</p>
            </div>
          )}

          {ok && (
            <div className="auth-success fade-in">
              <span className="alert-icon">✉️</span>
              <p>{ok}</p>
            </div>
          )}

          <form onSubmit={handleSubmit} className="auth-form-grid">
            
            <div className="premium-field">
              <label>First Name</label>
              <div className="input-wrapper">
                <input
                  type="text"
                  placeholder="John"
                  value={form.firstName}
                  onChange={(e) => updateField("firstName", e.target.value)}
                  required
                />
              </div>
            </div>

            <div className="premium-field">
              <label>Last Name</label>
              <div className="input-wrapper">
                <input
                  type="text"
                  placeholder="Doe"
                  value={form.lastName}
                  onChange={(e) => updateField("lastName", e.target.value)}
                  required
                />
              </div>
            </div>

            <div className="premium-field">
              <label>Username</label>
              <div className="input-wrapper">
                <input
                  type="text"
                  placeholder="gelatolover"
                  value={form.username}
                  onChange={(e) => updateField("username", e.target.value)}
                  required
                />
              </div>
            </div>

            <div className="premium-field">
              <label>Password</label>
              <div className="input-wrapper">
                <input
                  type="password"
                  placeholder="Min. 6 chars"
                  value={form.password}
                  onChange={(e) => updateField("password", e.target.value)}
                  required
                  minLength={6}
                />
              </div>
            </div>

            <div className="premium-field">
              <label>Email Address</label>
              <div className="input-wrapper">
                <input
                  type="email"
                  placeholder="you@example.com"
                  value={form.email}
                  onChange={(e) => updateField("email", e.target.value)}
                  required
                />
              </div>
            </div>

            <div className="premium-field">
              <label>Phone Number</label>
              <div className="input-wrapper">
                <input
                  type="tel"
                  placeholder="+381 6X..."
                  value={form.phoneNumber}
                  onChange={(e) => updateField("phoneNumber", e.target.value)}
                  required
                />
              </div>
            </div>

            <div className="premium-field full-width-field">
              <label>Delivery Address</label>
              <div className="input-wrapper">
                <input
                  type="text"
                  placeholder="Street Name, Apartment, Floor, Entry Code"
                  value={form.address}
                  onChange={(e) => updateField("address", e.target.value)}
                  required
                />
              </div>
            </div>

            <div className="full-width-field">
              <button className="btn-primary-premium btn-compact-adjust" disabled={loading}>
                <span>{loading ? "Creating Account..." : "Get Started Now"}</span>
              </button>
            </div>
          </form>

          <div className="auth-card-footer">
            <span className="muted">Already have an account?</span>
            <a href="/login" className="switch-auth-link">Sign In</a>
          </div>
        </div>
      </div>
    </div>
  );
}