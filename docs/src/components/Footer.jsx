import React from 'react'

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
         <footer className="footer text-center">
      <div className="container">
        <div className="row">
          <div className="col-md-12">
            <p className="mb-0">
              "Your favorite notes, always at your fingertips."
            </p>
            <p className="mb-0">© 2023 QuickNote. All rights reserved.</p>
          </div>
        </div>
      </div>
    </footer>
    </div>
  )
}

export default Footer