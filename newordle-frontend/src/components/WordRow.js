import { useEffect, useState } from "react";
import { Row } from "react-bootstrap";
import Cell from "./Cell";

function WordRow(props) {
    const [word, setWord] = useState("");
    const [cell, setCell] = useState(0);
    const [key, setKey] = useState("");
    const [letter1, setLetter1] = useState("");
    const [letter2, setLetter2] = useState("");
    const [letter3, setLetter3] = useState("");
    const [letter4, setLetter4] = useState("");
    const [letter5, setLetter5] = useState("");

    useEffect(() => {
        var curCell = cell;
        if (`${key}` === "")
            curCell += 1;
        switch (curCell) {
            case 1: setLetter1(`${key}`)
                break;
            case 2: setLetter2(`${key}`)
                break;
            case 3: setLetter3(`${key}`)
                break;
            case 4: setLetter4(`${key}`)
                break;
            case 5: setLetter5(`${key}`)
                break;
            default:
                break;
        }
        setKey("0");
    }, [cell]);

    useEffect(() => {
        const detectKeyUp = (e) => {
            if (props.active) {
                if (word.length < 5 && new RegExp('^[a-zA-Z]$').test(e.key)) {
                    setKey(e.key); //a-z
                    setCell(prevCell => prevCell + 1);
                    setWord(prevWord => prevWord + e.key);
                } else if (e.key === 'Backspace' && word.length > 0) {
                    setKey("");
                    setCell(prevCell => prevCell - 1);
                    setWord(prevWord => prevWord.slice(0, -1));
                } else if (e.key === 'Enter' && word.length === 5) {
                    console.log(word); //check word
                } else {
                    console.log("invalid input");
                }
            }
        }
        document.addEventListener('keyup', detectKeyUp, false)
        return () => document.removeEventListener("keyup", detectKeyUp);
    }, [props.active, word]);


    return (
        <Row style={{ height: '5rem', justifyContent: 'center' }} >
            <Cell active={props.displayed && cell.valueOf() >= 1} text={letter1} />
            <Cell active={props.displayed && cell.valueOf() >= 2} text={letter2} />
            <Cell active={props.displayed && cell.valueOf() >= 3} text={letter3} />
            <Cell active={props.displayed && cell.valueOf() >= 4} text={letter4} />
            <Cell active={props.displayed && cell.valueOf() >= 5} text={letter5} />
            <p style={{ color: 'red' }}>{word}</p>
        </Row>
    )
}

export default WordRow;