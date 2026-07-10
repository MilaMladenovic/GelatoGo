import { useEffect, useState } from "react";
import { Link, NavLink, useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "../css/Navbar.css";
import { ShoppingCart } from "lucide-react";
import { User, ShoppingBag, LogOut, ChevronDown } from "lucide-react";

function getMe() {
  try {
    return JSON.parse(localStorage.getItem("me") || "null");
  } catch {
    return null;
  }
}

function isAuthed() {
  return !!localStorage.getItem("token");
}

function Navbar() {
  const [scrolled, setScrolled] = useState(false);
  const location = useLocation();
  const [userMenuOpen, setUserMenuOpen] = useState(false);
  const [cartCount, setCartCount] = useState(0);

  const forceSolid =
    location.pathname === "/login" ||
    location.pathname === "/register" ||
    location.pathname === "/admin/icecreams" ||
    location.pathname === "/admin/icecreamshops" ||
    location.pathname === "/forgot-password" ||
    location.pathname === "/reset-password" ||
    location.pathname === "/icecreamshops-page" ||
    location.pathname === "/cart" ||
    location.pathname === "/admin/orders" ||
    location.pathname === "/profile" ||
    location.pathname.includes("/verify") ||
    location.pathname.startsWith("/icecreams-page/");

  const nav = useNavigate();
  const authed = isAuthed();
  const me = getMe();

  useEffect(() => {
    function handleScroll() {
      setScrolled(forceSolid ? true : window.scrollY > 80);
    }

    handleScroll();
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, [forceSolid]);

  function handleLogout() {
    localStorage.removeItem("token");
    localStorage.removeItem("me");
    localStorage.setItem("cart", "[]");
    window.dispatchEvent(new Event("cartUpdated"));
    setUserMenuOpen(false);
    nav("/login");
  }

  useEffect(() => {
    const updateCart = () => {
      const cart = JSON.parse(localStorage.getItem("cart") || "[]");

      const total = cart.reduce(
        (sum, item) => sum + (item.quantity || 1),
        0
      );

      setCartCount(total);
    };
    updateCart();
    window.addEventListener("storage", updateCart);
    window.addEventListener("cartUpdated", updateCart);
    return () => {
      window.removeEventListener("storage", updateCart);
      window.removeEventListener("cartUpdated", updateCart);
    };

  }, []);

  return (
    <nav className={scrolled ? "navbar navbar-scrolled" : "navbar"}>
      <div
        className="navbar-logo"
        onClick={() => {
          nav("/");
          window.scrollTo({ top: 0, behavior: "smooth" });
        }}
        style={{ cursor: 'pointer' }}
      >
        GELATO <span>GO</span>
      </div>

      <ul className="navbar-links">
        <NavLink to="/" className="nav-item">Homepage</NavLink>

        {authed && me?.role === "ADMIN" && (
          <>
            <NavLink to="/admin/icecreams" className="nav-item">Ice Creams</NavLink>
            <NavLink to="/admin/icecreamshops" className="nav-item">Ice Cream Shops</NavLink>
            <NavLink to="/admin/orders" className="nav-item">Orders</NavLink>
          </>
        )}

        {(!authed || me?.role === "USER") && (
          <>
            <NavLink to="/icecreamshops-page" className="nav-item">
              Ice Cream Shops
            </NavLink>
          </>
        )}

        {authed && me?.role === "USER" && (
          <NavLink to="/cart" className="nav-item">
            Cart
          </NavLink>
        )}
      </ul>

      <div className="navbar-actions">
        {!authed ? (
          <>
            <Link to="/login" className="nav-link-btn ghost">Login</Link>
            <Link to="/register" className="nav-link-btn primary">Register</Link>
          </>
        ) : (
          <>
            <div className="user-menu">

              <button
                className="user-chip"
                onClick={() => setUserMenuOpen(!userMenuOpen)}
              >
                {me?.firstName ?? me?.username ?? "User"}

                <ChevronDown
                  size={16}
                  className={userMenuOpen ? "rotate" : ""}
                />
              </button>

              {userMenuOpen && (

                <div className="user-dropdown">

                  <Link
                    to="/profile"
                    className="dropdown-item"
                    onClick={() => setUserMenuOpen(false)}
                  >
                    <User size={17} />
                    My Profile
                  </Link>

                  <Link
                    to="/cart"
                    className="dropdown-item"
                    onClick={() => setUserMenuOpen(false)}
                  >
                    <ShoppingBag size={17} />
                    My Cart
                  </Link>

                  <button
                    className="dropdown-item logout-item"
                    onClick={handleLogout}
                  >
                    <LogOut size={17} />
                    Logout
                  </button>

                </div>
              )}
            </div>
          </>
        )}

        <button className="navbar-btn" onClick={() => nav("/icecreamshops-page")}>
          Order now
        </button>

        <NavLink to="/cart" className={`cart-icon ${(location.pathname === "/" && !scrolled) ? "hero-cart" : ""}`}>
          <ShoppingCart size={23} strokeWidth={2.2} />
          {cartCount > 0 && (
            <span className="cart-badge">
              {cartCount}
            </span>
          )}
        </NavLink>
      </div>
    </nav>
  );
}

export default Navbar;