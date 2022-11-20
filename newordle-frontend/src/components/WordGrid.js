import { useEffect, useState } from "react";
import WordRow from "./WordRow";

function WordGrid() {
    const [curRow, setCurRow] = useState(1);
    const [word, setWord] = useState("");

    useEffect(() => {
        if (validateWord(word)) {
            setCurRow(row => row + 1);
        }
    }, [word])

    const validateWord = (wordPassed) => {
        if (word.length === 5) {
            var check = "";
            fetch(`http://localhost:8080/newordle?queryParam=${wordPassed}`)
                .then(response => response.text())
                .then((response) => {
                    check = response;
                    console.log(response);
                }, (error) => {
                    console.log(error);
                });
            console.log("Validating:", wordPassed);
            if (check === "" || check === "Invalid Word!") {
                return false;
            }
            return true;
        }
        return false;
    }

    return <div>
        <WordRow active={curRow.valueOf() === 1} displayed={curRow.valueOf() >= 1} updateWord={setWord} rowId={1} />
        <WordRow active={curRow.valueOf() === 2} displayed={curRow.valueOf() >= 2} updateWord={setWord} rowId={2} />
        <WordRow active={curRow.valueOf() === 3} displayed={curRow.valueOf() >= 3} updateWord={setWord} rowId={3} />
        <WordRow active={curRow.valueOf() === 4} displayed={curRow.valueOf() >= 4} updateWord={setWord} rowId={4} />
        <WordRow active={curRow.valueOf() === 5} displayed={curRow.valueOf() >= 5} updateWord={setWord} rowId={5} />
        <WordRow active={curRow.valueOf() === 6} displayed={curRow.valueOf() >= 6} updateWord={setWord} rowId={6} />
    </div>
}

export default WordGrid;