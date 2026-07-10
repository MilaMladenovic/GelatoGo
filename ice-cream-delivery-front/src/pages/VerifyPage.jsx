import React, { useEffect, useState } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import "../css/VerifyPage.css";

export default function VerifyPage() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const statusParam = searchParams.get("status");
  const [status, setStatus] = useState("loading");
  const [message, setMessage] = useState("");

  useEffect(() => {
    if (statusParam === "success") {
      setStatus("success");
      setMessage("Your account has been successfully activated! You can now log in.");
    } else if (statusParam === "expired") {
      setStatus("error");
      setMessage("The verification link has expired. Please register again.");
    } else {
      setStatus("error");
      setMessage("Something went wrong. The token might be invalid or already used.");
    }
  }, [statusParam]);

  return (
    <div className="protected-page">
      <div className="protected-card">

        {status === "success" && (
          <>
            <div className="protected-icon success-icon-bg">🎉</div>
            <h1>Welcome to GelatoGo!</h1>
            <p className="protected-text text-success">{message}</p>
            <p className="protected-subtext">
              Your email is verified. Dive in and start ordering your favorite artisanal ice creams now!
            </p>
            <div className="protected-buttons">
              <button
                onClick={() => navigate("/login")}
                className="protected-login-btn"
              >
                Sign In Now
              </button>
            </div>
          </>
        )}

        {status === "error" && (
          <>
            <div className="protected-icon error-icon-bg">❌</div>
            <h1>Verification Failed</h1>
            <p className="protected-text text-danger">{message}</p>
            <p className="protected-subtext">
              Please try registering again or contact support if you think this is a mistake.
            </p>
            <div className="protected-buttons">
              <button
                onClick={() => navigate("/register")}
                className="protected-login-btn"
              >
                Back to Registration
              </button>
              <button
                onClick={() => navigate("/")}
                className="protected-register-btn"
              >
                Go Home
              </button>
            </div>
          </>
        )}

      </div>
    </div>
  );
}