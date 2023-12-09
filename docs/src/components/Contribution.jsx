import React from "react";
import Work from "../assets/images/Work.png";

function Contribution() {
  return (
    <div className="contribution">
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
                  <a href="https://github.com/srilakshmikanthanp/quicknote#readme" >
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
