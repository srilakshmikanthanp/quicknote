import React, { useEffect } from "react";
import gsap from "gsap";
import Heart from "../assets/images/heartt.png";
import { Tooltip } from "react-tooltip";

const Donate = () => {
  useEffect(() => {
    gsap.fromTo(
      ".donate-image",
      { opacity: 0, scale: 0 },
      {
        opacity: 1,
        scale: 1,
        duration: 1,
        ease: "power3.out",
        onComplete: () => {
          gsap.to(".donate-image", {
            scale: 1.2,
            yoyo: true,
            repeat: -1,
            ease: "bounce.out",
          });
        },
      }
    );
  }, []);

  return (
    <div className="donate">
      <a data-tooltip-id="my-tooltip" data-tooltip-content="DONATE">
        <Tooltip id="my-tooltip" />

        <a href="https://srilakshmikanthanp.github.io/donate/">
          {" "}
          <img
            src={Heart}
            alt="QuickNote"
            className="col mx-auto donate-image"
            style={{ width: "40px", height: "40px" }}
            data-tip
            data-for="donate-tooltip"
          />
        </a>
      </a>

      <br />
      <br />
      <p className="quote"></p>
    </div>
  );
};

export default Donate;
