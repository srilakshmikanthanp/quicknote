import React from "react";
import install from '../assets/images/install.png';

function Installation() {
  return (
    <div>
      <div className="install" >
        <h1 >Installation</h1>
        <p className="paraa">
          Follow the instructions below to install QuickNote on different
          platforms.
        </p>
      </div>
      <div className="section-center" >
        <div className="container">
          <div className="row">
            <div className="col-md-2">
            <img src={install} alt="QuickNote" className="img-fluid"  />

            </div>
            <div className="col-md-5">
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">Windows</h5>
                  <p className="card-text">
                    Just go to{" "}
                    <a href="https://github.com/srilakshmikanthanp/quicknote/releases">
                      Releases
                    </a>{" "}
                    and download the latest version of the installer.
                  </p>
                </div>
              </div>
            </div>

            <div className="col-md-5">
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">Linux</h5>
                  <p className="card-text">
                    Waiting for electron.js to fix the{" "}
                    <a href="https://github.com/electron/electron/issues/37112">
                      Issue
                    </a>{" "}
                    with the tray icon on Linux.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Installation;
