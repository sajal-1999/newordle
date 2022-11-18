import React, { Component } from 'react';
import './App.css';
import WordGrid from './components/WordGrid';

class App extends Component {

  state = {};

  // componentDidMount() {
  //   this.frontendCheck()
  // }

  // frontendCheck = () => {
  //   fetch('/frontend')
  //     .then(response => response.text())
  //     .then(message => {
  //       this.setState({ message: message });
  //     });
  // };

  render() {
    return (
      <div className="App bg-dark">
        <div>
          <h2 className="App-title" style={{ color: "white"}}>Newordle</h2>
          <WordGrid />
        </div>
      </div>
    );
  }
}

export default App;