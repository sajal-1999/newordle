import React, { Component } from 'react';
import './App.css';
import WordGrid from './components/WordGrid';
import Keyboard from './components/Keyboard';

class App extends Component {
  state = { letterColors: [], word: '' }
  getColorResponse = (word, letterColors) => {
    this.setState({
      word: word,
      letterColors: letterColors
    })
  }
  render() {
    return (
      <div className="App bg-dark">
        <div>
          <br></br>
          <h1 className="App-title" style={{ color: "white" }}>Newordle</h1>
          <br></br>
          <WordGrid getColorResponse={this.getColorResponse} />
          <Keyboard word={this.state.word} letterColors={this.state.letterColors} />
        </div>
      </div>
    );
  }
}

export default App;