import { useEffect, useState } from "react";
import WordRow from "./WordRow";

function WordGrid() {
    const [curRow, setCurRow] = useState(1);

    useEffect(() => {
        const detectKeyUp = (e) => {
            if (e.key === "Enter") {
                setCurRow(row => row + 1)
            }
            //TODO: add callback and revert to same row if not correct
        }
        document.addEventListener("keyup", detectKeyUp, false) // why twice?
        return () => document.removeEventListener("keyup", detectKeyUp);
    }, []);

    return <div>
        <WordRow active={curRow.valueOf() === 1} displayed={curRow.valueOf() >= 1} />
        <WordRow active={curRow.valueOf() === 2} displayed={curRow.valueOf() >= 2} />
        <WordRow active={curRow.valueOf() === 3} displayed={curRow.valueOf() >= 3} />
        <WordRow active={curRow.valueOf() === 4} displayed={curRow.valueOf() >= 4} />
        <WordRow active={curRow.valueOf() === 5} displayed={curRow.valueOf() >= 5} />
        <WordRow active={curRow.valueOf() === 6} displayed={curRow.valueOf() >= 6} />
    </div>
}

export default WordGrid;