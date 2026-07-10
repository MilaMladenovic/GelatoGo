import React, { useMemo, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import http from "../api/http";
import "../css/auth.css";

export default function ResetPassword() {

  const [params] = useSearchParams();
  const navigate = useNavigate();

  const token = useMemo(
    () => params.get("token") || "",
    [params]
  );

  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const invalidLink = !token;

  async function handleSubmit(e) {
    e.preventDefault();

    setMessage("");
    setError("");

    if (password.length < 6) {
      setError("Password must contain at least 6 characters.");
      return;
    }

    if (password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    setLoading(true);

    try {
      await http.post("/auth/reset-password", {
        token,
        password,
      });

      setMessage(
        "Your password has been updated successfully. Redirecting to Sign In..."
      );

      setTimeout(() => {
        navigate("/login");
      }, 1800);

    } catch (e) {

      setError(
        e?.response?.data ||
        "This password reset link is invalid or has expired."
      );

    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-wrap premium-gate-bg">

      <div className="auth-glass-card auth-password-card fade-in">

        <div className="password-icon">
          🔒
        </div>

        <div className="card-header">
          <h2>Create a New Password</h2>

          <p className="muted">
            Choose a strong password to keep your GelatoGo account secure.
          </p>
        </div>

        {invalidLink && (
          <div className="auth-alert">
            <span className="alert-icon">⚠️</span>

            This password reset link is invalid or has expired.
            Please request a new one.
          </div>
        )}

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

        {!invalidLink && (

          <form
            className="auth-form-premium"
            onSubmit={handleSubmit}
          >

            <div className="premium-field">

              <label>New Password</label>

              <div className="input-wrapper">
                <input
                  type="password"
                  placeholder="Enter your new password"
                  required
                  minLength={6}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>

            </div>

            <div className="premium-field">

              <label>Confirm Password</label>

              <div className="input-wrapper">
                <input
                  type="password"
                  placeholder="Confirm your new password"
                  required
                  minLength={6}
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                />
              </div>

            </div>

            <button
              className="btn-primary-premium"
              disabled={loading}
            >
              {loading ? "Updating Password..." : "Update Password"}
            </button>

          </form>
        )}

        <div className="auth-card-footer">
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