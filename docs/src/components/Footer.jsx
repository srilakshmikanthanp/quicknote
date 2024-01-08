import React from "react";
import Donate from "./Donate";
import Arow from "../assets/images/arrow.png";
import { Tooltip } from "react-tooltip";
function Footer() {
  const footerStyle = {
    backgroundColor: "#041E42",
    color: "#FFFFFF",
    padding: "20px 0",
  };

  const quoteStyle = {
    fontStyle: "italic",
    fontSize: "18px",
  };
  return (
    <div>
      <Donate />

      <footer className="container-fluid text-center footer">
        <div className="container">
          <div className="row">
            <div className="col-md-12 ftr">
              <p className="mb-0">
                "Your favorite notes, always at your fingertips."
              </p>
              <p className="mb-0">© 2023 QuickNote. All rights reserved.</p>
            </div>
          </div>
        </div>
      </footer>
      <div className="arrow">

          <a href="#nav">
            <img
              src={Arow}
              alt=""
              className="col mx-auto "
              style={{ width: "50px", height: "50px" }}
            />
          </a>
      </div>
    </div>
  );
}

export default Footer;
