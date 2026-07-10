import React, { useRef } from "react";
import { useNavigate } from "react-router-dom";
import "../css/Homepage.css";
import heroVideo from "../assets/hero-video.mp4";
import iceCream from "../assets/ice-cream.mp4";
import iceCreamsPhoto from "../assets/luff-ice-cream-photo.jpg";

function Homepage() {
  const navigate = useNavigate();
  const brandsSectionRef = useRef(null);

  const scrollToBrands = () => {
    brandsSectionRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  return (
    <main className="homepage">
      <section className="hero">
        <video autoPlay muted loop playsInline className="hero-video">
          <source src={heroVideo} type="video/mp4" />
        </video>
        <div className="hero-overlay"></div>
        <div className="hero-content fade-up">
          <span className="hero-badge">PREMIUM GELATO DELIVERY</span>
          <h1>The finest gelato <br /> delivered to you.</h1>
          <p>Discover Belgrade's most loved artisan ice cream brands delivered fresh, fast and perfectly chilled.</p>
          <div className="hero-buttons">
            <button className="primary-btn" onClick={() => navigate("/icecreamshops-page")}>Explore Ice Creams</button>
            <button className="secondary-btn" onClick={scrollToBrands}>Our Brands</button>
          </div>
        </div>
      </section>

      <section className="brands" ref={brandsSectionRef}>
        <div className="section-header fade-up">
          <span>PARTNERS</span>
          <h2>Premium gelato brands <br /> selected for you.</h2>
          <p>We collaborate with the best local ice cream makers to bring unforgettable desserts directly to your door.</p>
        </div>

        <div className="brands-grid">
          <div className="brand-card"><img src="/logos/luff.png" alt="Luff" /></div>
          <div className="brand-card"><img src="/logos/gram.png" alt="Gram" /></div>
          <div className="brand-card"><img src="/logos/crna-ovca.jpg" alt="Crna Ovca" /></div>
          <div className="brand-card"><img src="/logos/homemade.jpg" alt="Homemade Company" /></div>
          <div className="brand-card"><img src="/logos/moritz-eis.png" alt="Moritz Eis" /></div>
        </div>
      </section>

      <section className="features">
        <div className="section-header fade-up">
          <span>WHY GELATOGO</span>
          <h2>More than delivery. <br /> A premium experience.</h2>
        </div>

        <div className="features-grid">
          <article className="feature-card">
            <div className="feature-icon">🍦</div>
            <h3>Premium Selection</h3>
            <p>Carefully chosen gelato brands with authentic flavors and quality ingredients.</p>
          </article>
          <article className="feature-card">
            <div className="feature-icon">🚚</div>
            <h3>Fast Delivery</h3>
            <p>Special packaging keeps every dessert fresh until it reaches you.</p>
          </article>
          <article className="feature-card">
            <div className="feature-icon">⭐</div>
            <h3>Loved By Customers</h3>
            <p>Thousands of dessert lovers trust GelatoGo for their sweet moments.</p>
          </article>
        </div>
      </section>

      <section className="how-it-works">
        <div className="section-header fade-up">
          <span>SIMPLE PROCESS</span>
          <h2>From craving <br /> to first bite.</h2>
        </div>

        <div className="steps-grid">
          <div className="step-card">
            <span className="step-number">01</span>
            <h3>Choose your gelato</h3>
            <p>Explore premium flavors from Belgrade's favorite ice cream shops.</p>
          </div>
          <div className="step-card">
            <span className="step-number">02</span>
            <h3>Place your order</h3>
            <p>Select your favorite desserts and complete your order in seconds.</p>
          </div>
          <div className="step-card">
            <span className="step-number">03</span>
            <h3>Enjoy the moment</h3>
            <p>Your perfectly chilled gelato arrives directly at your doorstep.</p>
          </div>
        </div>
      </section>

      <section className="story">
        <div className="story-left">
          <div className="story-image-wrapper">
            <img src={iceCreamsPhoto} alt="Premium gelato" />
            <div className="rating-card">
              <strong>★ 5.0</strong>
              <span>Customer rating</span>
            </div>
          </div>
        </div>

        <div className="story-right">
          <span>DELIVERY WITHOUT COMPROMISE</span>
          <h2>Perfect temperature. <br /> Perfect taste.</h2>
          <p>We use specialized delivery methods to make sure every scoop arrives exactly as the creators intended — creamy, fresh and delicious.</p>
          <div className="story-stats">
            <div>
              <strong>5+</strong>
              <span>Premium Brands</span>
            </div>
            <div>
              <strong>100%</strong>
              <span>Fresh Delivery</span>
            </div>
            <div>
              <strong>24/7</strong>
              <span>Order Access</span>
            </div>
          </div>
        </div>
      </section>

      <section className="cta">
        <div className="cta-card">
          <video autoPlay muted loop playsInline className="cta-video">
            <source src={iceCream} type="video/mp4" />
          </video>
          <div className="cta-overlay"></div>
          <div className="cta-content">
            <span>READY FOR A SWEET MOMENT?</span>
            <h2>Your favorite gelato <br /> is only a few clicks away.</h2>
            <button className="primary-btn" onClick={() => navigate("/icecreamshops-page")}>Order Now</button>
          </div>
        </div>
      </section>
    </main>
  );
}

export default Homepage;