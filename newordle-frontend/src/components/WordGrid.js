import { useEffect, useState } from "react";
import WordRow from "./WordRow";

function WordGrid() {
    const [curRow, setCurRow] = useState(1);
    
    useEffect(() => {
        const detectKeyUp = (e) => {
            if(e.key==="Enter") {
                setCurRow(row => row+1)
            }
            //TODO: add callback and revert to same row if not correct
        }
        document.addEventListener("keyup", detectKeyUp, false) // why twice?
        return () => document.removeEventListener("keyup", detectKeyUp);
    }, []);

    return <div>
        <WordRow active={curRow.valueOf() >= 1} id={1} />
        <WordRow active={curRow.valueOf() >= 2} id={2} />
        <WordRow active={curRow.valueOf() >= 3} id={3} />
        <WordRow active={curRow.valueOf() >= 4} id={4} />
        <WordRow active={curRow.valueOf() >= 5} id={5} />
        <WordRow active={curRow.valueOf() >= 6} id={4} />
        <WordRow active={curRow.valueOf() >= 7} id={5} />
    </div>
}

export default WordGrid;