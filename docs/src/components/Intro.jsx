import React, { useEffect } from "react";
import gsap from "gsap";

import tenor from "../assets/images/tenor.gif";

function Section() {
  useEffect(() => {
    // Create a GSAP timeline
    const tl = gsap.timeline();
    const t2 = gsap.timeline();

    // Set the initial state (transparent and hidden)
    tl.set(".image, .col-md-9 p", { autoAlpha: 0, x: -50 });

    // Add a fade-in animation with left translation after 2 seconds
    tl.to(".image, .col-md-9 p", { autoAlpha: 1, x: 0, duration: 2, delay: 1 });

  },
   []);


  return (
    <div className="display-section wrapper" id="intro">
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
