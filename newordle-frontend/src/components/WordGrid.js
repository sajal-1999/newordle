import { useEffect, useState } from "react";
import WordRow from "./WordRow";

function WordGrid() {
    const [curRow, setCurRow] = useState(1);
    const [word, setWord] = useState("");
    const [resp, setResp] = useState("");
    const defaultColor = ['White', 'White', 'White', 'White', 'White'];
    const [colors, setColors] = useState([defaultColor, defaultColor, defaultColor, defaultColor, defaultColor, defaultColor]);

    useEffect(() => {
        if (validateWord(word)) {
            setCurRow(row => row + 1);
            const temp = resp.split(',');
            var curRowColors = colors;
            curRowColors[curRow - 1] = temp;
            setColors(curRowColors);
        }
        setResp("");
        setWord("");
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [resp])

    async function getWordValidation() {
        if (word.length === 5) {
            await fetch(`/newordle?queryParam=${word}`)
                .then(response => response.text())
                .then((response) => {
                    setResp(response);
                    console.log(response);
                }, (error) => {
                    console.log(error);
                });
        }
    }

    useEffect(() => {
        getWordValidation();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [word])

    const validateWord = (wordPassed) => {
        if (word.length === 5) {
            console.log("Validating:", wordPassed);
            if (resp === "" || resp === "Invalid Word!") {
                return false;
            }
            return true;
        }
        return false;
    }

    return <div>
        <WordRow active={curRow.valueOf() === 1} displayed={curRow.valueOf() >= 1} updateWord={setWord} rowId={1} colors={colors[0]} />
        <WordRow active={curRow.valueOf() === 2} displayed={curRow.valueOf() >= 2} updateWord={setWord} rowId={2} colors={colors[1]} />
        <WordRow active={curRow.valueOf() === 3} displayed={curRow.valueOf() >= 3} updateWord={setWord} rowId={3} colors={colors[2]} />
        <WordRow active={curRow.valueOf() === 4} displayed={curRow.valueOf() >= 4} updateWord={setWord} rowId={4} colors={colors[3]} />
        <WordRow active={curRow.valueOf() === 5} displayed={curRow.valueOf() >= 5} updateWord={setWord} rowId={5} colors={colors[4]} />
        <WordRow active={curRow.valueOf() === 6} displayed={curRow.valueOf() >= 6} updateWord={setWord} rowId={6} colors={colors[5]} />
    </div>
}

export default WordGrid;