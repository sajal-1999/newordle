import { useRef, useEffect, useState } from "react";
import { Row } from "react-bootstrap";
import Cell from "./Cell";

function usePrevious(value) {
    const ref = useRef();
  
    useEffect(() => {
      ref.current = value;
    }, [value]);
    
    return ref.current;
  }
  

function useEffectAllDepsChange(fn, deps) {
    const prevDeps = usePrevious(deps);
    const changeTarget = useRef();

    useEffect(() => {
        // nothing to compare to yet
        if (changeTarget.current === undefined) {
            changeTarget.current = prevDeps;
        }

        // we're mounting, so call the callback
        if (changeTarget.current === undefined) {
            return fn();
        }

        // make sure every dependency has changed
        if (changeTarget.current.every((dep, i) => dep !== deps[i])) {
            changeTarget.current = deps;

            return fn();
        }
    }, [fn, prevDeps, deps]);
}

function WordRow(props) {
    // console.log(props.id + " - " + props.active);
    const [word, setWord] = useState("");
    const [cell, setCell] = useState(0);
    const [key, setKey] = useState("");
    const [letter1, setLetter1] = useState("");
    const [letter2, setLetter2] = useState("");
    const [letter3, setLetter3] = useState("");
    const [letter4, setLetter4] = useState("");
    const [letter5, setLetter5] = useState("");

    useEffectAllDepsChange(() => {
        switch (cell) {
            case 1: setLetter1(key)
                break;
            case 2: setLetter2(key)
                break;
            case 3: setLetter3(key)
                break;
            case 4: setLetter4(key)
                break;
            case 5: setLetter5(key)
                break;
            default:
                break;
        }
    }, [cell, key]);

    useEffect(() => {
        if (key !== "") {
            setCell(prevCell => prevCell + 1);
            setWord(prevWord => prevWord + key);
        }
    }, [key]);

    useEffect(() => {
        const detectKeyDown = (e) => {
            if (props.active && new RegExp('^[a-zA-Z]$').test(e.key)) {
                setKey(e.key);
            }
        }
        document.addEventListener('keydown', detectKeyDown, true)
    }, [props.active]);


    return (
        <Row style={{ height: '3rem', justifyContent: 'center' }} >
            <Cell active={props.active && cell.valueOf() >= 1} text={letter1} rowid={props.id} cell={1} />
            <Cell active={props.active && cell.valueOf() >= 2} text={letter2} rowid={props.id} cell={2} />
            <Cell active={props.active && cell.valueOf() >= 3} text={letter3} rowid={props.id} cell={3} />
            <Cell active={props.active && cell.valueOf() >= 4} text={letter4} rowid={props.id} cell={4} />
            <Cell active={props.active && cell.valueOf() >= 5} text={letter5} rowid={props.id} cell={5} />
            <p>{word}</p>
            {/* {Array.from({ length: 5 }).map((_, idx) =>
                <Cell key={idx} active={false} letter={letter1} setLetter={setLetter} />
            )} */}
        </Row>
    )
}


export default WordRow;