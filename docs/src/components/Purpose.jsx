import React from "react";
import Time from "../assets/images/Time.png";
import Userr from "../assets/images/Userr.png";
import easy from "../assets/images/easy.png";

function Purpose() {
  
  return (
    <div className="text-center purpose " id="purpose">
      <h1 className="mb-4">Why Use QuickNote?</h1>
      <div className="d-flex pp">
        <div className="row">
          <div className="col-md-4">
            <div className="thumbnail">
              <img
                src={Time}
                alt="QuickNote"
                className="img-fluid mx-auto"
                style={{ width: "200px", height: "200px" }}
              />
            </div>
            <p>
              <span className="line">Time to Save</span>
              <br />
              QuickNote is designed to provide quick and easy access to
              note-taking functionalities directly from your taskbar. There's no
              need to navigate through multiple applications or windows, saving
              you valuable time.
            </p>
          </div>

          <div className="col-md-4">
            <div className="thumbnail">
              <img
                src={Userr}
                alt="QuickNote"
                className="img-fluid mx-auto"
                style={{ width: "200px", height: "200px" }}
              />
            </div>
            <p>
              <span className="line">User-Friendly</span>
              <br />
              At QuickNote, we prioritize user-friendliness to ensure a seamless
              and enjoyable note-taking experience. The intuitive interface and
              accessible features make it easy for users of all levels to
              navigate effortlessly.
            </p>
          </div>

          <div className="col-md-3">
            <div className="thumbnail">
              <img
                src={easy}
                alt="QuickNote"
                className="img-fluid mx-auto"
                style={{ width: "200px", height: "200px" }}
              />
            </div>
            <p>
              <span className="line">Easy to Use</span>
              <br />
              QuickNote boasts an intuitive and straightforward interface,
              making it easy for users of all levels to navigate effortlessly.
              Whether you're a seasoned professional or a first-time note-taker,
              our design ensures a hassle-free experience.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Purpose;
