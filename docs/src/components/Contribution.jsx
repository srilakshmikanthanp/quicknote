import React, { useEffect } from "react";
import { gsap } from "gsap";
import { ScrollTrigger } from "gsap/ScrollTrigger";
import Work from "../assets/images/Work.png";

gsap.registerPlugin(ScrollTrigger);

function Contribution() {
  useEffect(() => {
    // Animation for the installation section
    gsap.fromTo(
      ".contribution",
      { opacity: 0, y: 50 },
      {
        opacity: 1,
        y: 0,
        duration: 0.5,
        transition: 1,
        ease: "power3.out",
        scrollTrigger: { trigger: ".contribution", scrub: true },
      }
    );

    // Animation for the card section
    gsap.fromTo(
      ".img-fluid",
      { opacity: 0, y: 50 },
      {
        opacity: 1,
        y: 0,
        duration: 2,
        ease: "power3.out",
        scrollTrigger: { trigger: ".img-fluid", start: "top 80%", scrub: true },
      }
    );
  }, []);

  return (
    <div className="contribution"id="contribution">
      <h1 className="text-center head">Contributions</h1>
      <div className="conribution">
        <div className="container">
          <div className="row">
            <div className="col-md-6">
              <p className="para">
                Contributions are what make the open source community such an
                amazing place to learn, inspire, and create. Any contributions
                you make are greatly appreciated.
                <br />
                <span className="button">
                  <a href="https://github.com/srilakshmikanthanp/quicknote#readme">
                    {" "}
                    Check this steps to contribute
                  </a>
                </span>
              </p>
            </div>
            <div className="col-md-6 ">
              <img
                src={Work}
                alt="QuickNote"
                className="img-fluid"
                style={{ width: "800px", height: "600px" }}
              />
            </div>
          </div>
        </div>
      </div>

      {/* <p>Follow these steps to contribute:</p> */}
      {/* <ol>
              <li>Fork the Project</li>
              <li>Create your Feature Branch (git checkout -b feature/AmazingFeature)</li>
              <li>Commit your Changes (git commit -m 'Add some AmazingFeature')</li>
              <li>Push to the Branch (git push origin feature/AmazingFeature)</li>
              <li>Open a Pull Request into my webpage</li>
            </ol> */}
    </div>
  );
}

export default Contribution;
