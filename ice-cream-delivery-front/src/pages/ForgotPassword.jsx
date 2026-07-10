import React, { useState } from "react";
import http from "../api/http";
import "../css/auth.css";

export default function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();

    setMessage("");
    setError("");
    setLoading(true);

    try {
      await http.post("/auth/forgot-password", { email });

      setMessage(
        "If an account with this email exists, we've sent a password reset link. Please check your inbox."
      );
    } catch {
      setMessage(
        "If an account with this email exists, we've sent a password reset link. Please check your inbox."
      );
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-wrap premium-gate-bg">
      <div className="auth-glass-card auth-password-card fade-in">

        <div className="password-icon">
          ✉️
        </div>

        <div className="card-header">
          <h2>Forgot your password?</h2>
          <p className="muted">
            No worries. Enter the email address associated with your GelatoGo
            account and we'll send you a secure password reset link.
          </p>
        </div>

        {error && (
          <div className="auth-alert">
            <span className="alert-icon">⚠️</span>
            {error}
          </div>
        )}

        {message && (
          <div className="auth-success">
            <span className="alert-icon">✓</span>
            {message}
          </div>
        )}

        <form
          className="auth-form-premium"
          onSubmit={handleSubmit}
        >
          <div className="premium-field">
            <label>Email Address</label>

            <div className="input-wrapper">
              <input
                type="email"
                placeholder="Enter your email address"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
          </div>

          <button
            className="btn-primary-premium"
            disabled={loading}
          >
            {loading ? "Sending Reset Link..." : "Send Reset Link"}
          </button>
        </form>

        <div className="auth-card-footer">
          <span>Remember your password?</span>

          <a
            href="/login"
            className="switch-auth-link"
          >
            Back to Sign In
          </a>
        </div>

      </div>
    </div>
  );
}