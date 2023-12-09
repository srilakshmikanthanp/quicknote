import React from "react";
import tenor from "../assets/images/tenor.gif";

function Section() {
  return (
    <div className="display-section wrapper">
      <div className="container">
        <div className="row">
          <div className="col-md-9">
            <p>
              <span>Lets Get Started With QuickNote</span>
              <br />
              QuickNote is a utility program to take Notes! right from your
              taskbar. QuickNote resides <br />
              in your taskbar, providing instant access to note-taking
              functionalities with a simple click.
              <br /> No need to switch between applications;
              <br /> your notes are just a glance away.
            </p>
          </div>
          <div className="col-md-3 image">
            <img src={tenor} alt="QuickNote" className="img-fluid" />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Section;
