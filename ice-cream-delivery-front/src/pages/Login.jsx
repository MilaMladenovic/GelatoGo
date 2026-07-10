import React, { useState } from "react";
import http from "../api/http";
import "../css/auth.css";

export default function Login({ onSuccess }) {
  const [form, setForm] = useState({ username: "", password: "" });
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setErr("");
    setLoading(true);
    try {
      const res = await http.post("/auth/login", form);
      localStorage.setItem("token", res.data.token);
      localStorage.setItem("me", JSON.stringify(res.data.user));
      if (typeof onSuccess === "function") onSuccess(res.data.user);
    } catch (e) {
      setErr(e?.response?.data?.message || "Invalid credentials. Please try again.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="split-layout-wrap">
      <div className="auth-sidebar">
        <div className="sidebar-content">
          <div className="brand-logo">
            <span className="logo-emoji">🍦</span>
            <span className="logo-text">GelatoGo</span>
          </div>
          
          <div className="sidebar-hero-text">
            <h1>Craving something extraordinary?</h1>
            <p>Your portal to the finest local craft gelato, curated by experts and delivered in sub-zero precision.</p>
          </div>

          <div className="feature-list">
            <div className="feature-item">
              <div className="feature-icon">✨</div>
              <div className="feature-info">
                <h4>Premium IceCreamShops</h4>
                <p>Exclusive partnerships with award-winning local artisans.</p>
              </div>
            </div>
            <div className="feature-item">
              <div className="feature-icon">⚡</div>
              <div className="feature-info">
                <h4>Hyper-Fast Delivery</h4>
                <p>Temperature-controlled transit keeps your IceCreams perfectly frozen.</p>
              </div>
            </div>
            <div className="feature-item">
              <div className="feature-icon">🛡️</div>
              <div className="feature-info">
                <h4>Flawless Experience</h4>
                <p>From checkout to first bite, enjoy 5-star service every time.</p>
              </div>
            </div>
          </div>
        </div>
        <div className="sidebar-footer">
          © 2026 GelatoGo Inc. All rights reserved.
        </div>
      </div>

      <div className="auth-main-panel">
        <div className="auth-glass-card fade-in">
          <div className="mobile-logo-header">
            <span className="logo-emoji">🍦</span>
            <span className="logo-text">GelatoGo</span>
          </div>

          <div className="card-header">
            <h2>Welcome back</h2>
            <p className="muted">Enter your credentials to access your premium dashboard</p>
          </div>

          {err && (
            <div className="auth-alert fade-in">
              <span className="alert-icon">⚠️</span>
              <p>{err}</p>
            </div>
          )}

          <form onSubmit={handleSubmit} className="auth-form-premium">
            <div className="premium-field">
              <label>Username</label>
              <div className="input-wrapper">
                <input
                  type="text"
                  autoComplete="username"
                  placeholder="e.g., gelatolover"
                  value={form.username}
                  onChange={(e) => setForm((f) => ({ ...f, username: e.target.value }))}
                  required
                />
              </div>
            </div>

            <div className="premium-field">
              <div className="label-row">
                <label>Password</label>
                <a href="/forgot-password" className="forgot-link">Forgot password?</a>
              </div>
              <div className="input-wrapper">
                <input
                  type="password"
                  autoComplete="current-password"
                  placeholder="••••••••"
                  value={form.password}
                  onChange={(e) => setForm((f) => ({ ...f, password: e.target.value }))}
                  required
                />
              </div>
            </div>

            <button className="btn-primary-premium" disabled={loading}>
              <span>{loading ? "Authenticating..." : "Sign In to GelatoGo"}</span>
            </button>
          </form>

          <div className="auth-card-footer">
            <span className="muted">New to our platform?</span>
            <a href="/register" className="switch-auth-link">Create an account</a>
          </div>
        </div>
      </div>
    </div>
  );
}