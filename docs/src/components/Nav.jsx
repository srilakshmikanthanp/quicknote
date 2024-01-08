import React, { useEffect, useState } from "react";
import gsap from "gsap";
import quicknote from "../assets/images/quicknote.png";


const Nav = () => {
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => {
    const tl = gsap.timeline();

    // Set the initial state (transparent and moved to the top)
    tl.set(".nav-wrapper", { autoAlpha: 0, y: -50 });

    // Add a fade-in animation with top translation
    tl.to(".nav-wrapper", { autoAlpha: 1, y: 0, duration: 1, delay: 0.5 });
  }, []);

  const handleMenuToggle = () => {
    setMenuOpen(!menuOpen);
  };

  return (
    <nav className={`nav-wrapper ${menuOpen ? "menu-open" : ""}`} id="nav">
      <div className="nav-content">
        <div className="logo-container">
          <img
            src={quicknote}
            alt="QuickNote"
            className="logo"
            style={{ width: "50px", height: "50px" }}
          />
        </div>

        <div className="menu-btn" onClick={handleMenuToggle}>
          {!menuOpen ? (
            <i class="fa fa-bars" aria-hidden="true" hidden></i>
          ) : (
            <i class="fa fa-times" aria-hidden="true"></i>
          )}
        </div>

        <ul className={`naa ${menuOpen ? "menu-open" : ""}`}>
          <li>
            <a className="link-styled" href="#about">
              About
            </a>
          </li>
          <li>
            <a className="link-styled" href="#install">
              Install
            </a>
          </li>
          <li>
            <a className="link-styled" href="#contribution">
              Contribution
            </a>
          </li>
          <li>
            <a className="link-styled" href="#purpose">
              Purpose
            </a>
          </li>
        </ul>
      </div>
    </nav>
  );
};

export default Nav;