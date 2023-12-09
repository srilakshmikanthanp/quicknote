import React from 'react'
import Nav from './components/Nav'
import Intro from './components/Intro';
import Installation from './components/Installation';
import Contribution from './components/Contribution';
import Purpose from './components/Purpose';
import Footer from './components/Footer';



const App = () => {
  return (
    <div>
      <Nav/>
      <Intro/>
      <Installation/>
      <Contribution/>
      <Purpose/>
      <Footer/>
      
      
    </div>
  )
}

export default App
