import "./App.css";


import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Homepage from "./pages/Homepage";
import IceCreamShops from "./pages/IceCreamShops";
import { Routes, Route, BrowserRouter } from "react-router-dom"
import IceCreams from "./pages/IceCreams";
import ProtectedRoute from "./components/ProtectedRoute";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";
import { useEffect, useState } from 'react';
import IceCreamShopsPage from "./pages/IceCreamShopsPage";
import IceCreamsPage from "./pages/IceCreamsPage";
import CartPage from "./pages/CartPage";
import AdminOrdersPage from "./pages/AdminOrdersPage";
import Profile from "./pages/Profile";
import VerifyPage from "./pages/VerifyPage";

function App() {



  const [cart, setCart] = useState(() => {
    try {
      return JSON.parse(localStorage.getItem("cart")) || [];
    } catch {
      return [];
    }
  });

  useEffect(() => {
    localStorage.setItem("cart", JSON.stringify(cart));
  }, [cart]);



  let userId = null;
  try {
    const me = JSON.parse(localStorage.getItem("me"));
    userId = me?.id || null;
  } catch (e) {
    console.error("Cannot load localStorage.me", e);
  }


  const addToCart = (p) => {
    const currentCart = [...cart];

    const existingItemIndex = currentCart.findIndex(item => item.id === p.id);

    let updatedCart;
    if (existingItemIndex > -1) {
      // ako vec postoji, samo mu povecamo kolicinu za 1
      currentCart[existingItemIndex].quantity = (currentCart[existingItemIndex].quantity || 1) + 1;
      updatedCart = currentCart;
    } else {
      // ako ne postoji, dodajemo ga kao novi proizvod sa kolicinom 1
      updatedCart = [...cart, { ...p, quantity: 1 }];
    }
    setCart(updatedCart);
    localStorage.setItem("cart", JSON.stringify(updatedCart));
    window.dispatchEvent(new Event("cartUpdated"));
  };

  return (
    <BrowserRouter>

      <Navbar></Navbar>

      <Routes>
        <Route path="/" element={<Homepage />} />

        <Route path="/admin/icecreamshops" element={<ProtectedRoute role="ADMIN">  <IceCreamShops /> </ProtectedRoute>} />
        <Route path="/admin/icecreams" element={<ProtectedRoute role="ADMIN">  <IceCreams /> </ProtectedRoute>} />
        <Route path="/admin/orders" element={<ProtectedRoute role="ADMIN"><AdminOrdersPage /></ProtectedRoute>} />

 
        <Route path="/login" element={<Login onSuccess={() => window.location.href = '/'} />} />
        <Route path="/register" element={<Register onSuccess={() => window.location.href = '/'} />} />
        <Route path="/verify" element={<VerifyPage />} />

        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/reset-password" element={<ResetPassword />} />

        <Route path="/profile" element={<ProtectedRoute role="USER"> <Profile /> </ProtectedRoute>} />
        <Route path="/cart" element={<ProtectedRoute role="USER">
          <CartPage cart={cart} setCart={setCart} userId={userId} />
        </ProtectedRoute>} />

      <Route path="/icecreamshops-page" element={<IceCreamShopsPage />} />
      <Route path="/icecreams-page/:id" element={<IceCreamsPage addToCart={addToCart} />} />
      </Routes>

      <Footer></Footer>

    </BrowserRouter>
  );
}

export default App;