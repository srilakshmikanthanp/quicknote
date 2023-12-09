// Your component file (Nav.js)

import React, { useState } from "react";
import quicknote from "../assets/images/quicknote.png";
 // Import the CSS file

const Nav = () => {
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const handleMobileMenuToggle = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  return (
    <nav className="nav-wrapper">
      <div className="nav-content">
        <ul className="list-styled">
          <li>
            <img src={quicknote} alt="QuickNote" />
            <h1 className="brand">QuickNote</h1>
          </li>
        </ul>
        <div className={`mobile-menu-button ${isMobileMenuOpen ? "hidden" : ""}`} onClick={handleMobileMenuToggle}>
          ☰
        </div>
        <div className={`close-button ${!isMobileMenuOpen ? "hidden" : ""}`} onClick={handleMobileMenuToggle}>
          &times;
        </div>
        <ul className={`naa ${isMobileMenuOpen ? "visible" : ""}`}>
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
