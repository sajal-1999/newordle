// import Keyboard from 'react-simple-keyboard';
import { useEffect, useState } from "react";
import { Row } from "react-bootstrap";

import 'react-simple-keyboard/build/css/index.css';
import './keyboard.css';
import Key from './Key';


function Keyboard(props) {

    const [colorMap, setColorMap] = useState({});
    const { word, letterColors } = props;
    const rows = [['Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P'],
    ['A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L'],
    ['⏎', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '⌫'],]

    useEffect(() => {
        const letters = word.split();
        const letterSet = new Set(letters);
        for (let letter in letterSet) {
            for (let i = 0; i < 5; i++) {
                if (letter === letters[i]) {
                    if (letter in colorMap) {
                        if (colorMap[letter] === 'green') {
                            break;
                        }
                    }
                    if (letterColors[i] === 'green') {
                        colorMap[letter] = 'green';
                        setColorMap({
                            ...colorMap, letter: 'green'
                        })
                    } else if (letterColors[i] === 'yellow') {
                        setColorMap({
                            ...colorMap, letter: 'yellow'
                        })
                        break;
                    }
                }
            }
            if (!(letter in colorMap)) {
                setColorMap({
                    ...colorMap, letter: 'grey'
                })
            }
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [word, letterColors])

    const onPress = (e) => {
        console.log('Entered');
        if (e === "⏎") {
            const enterEvent = new KeyboardEvent('keyup', { 'key': "Enter" });
            document.dispatchEvent(enterEvent);
        } else if (e === "⌫") {
            const enterEvent = new KeyboardEvent('keyup', { 'key': "Backspace" });
            document.dispatchEvent(enterEvent);
        } else {
            const keyPress = new KeyboardEvent('keyup', { 'key': e.slice(-1) });
            document.dispatchEvent(keyPress);
        }
    }

    return <div><center>
        {/* <Keyboard
            layout={layout}
            theme={"hg-theme-default myTheme1"}
            onKeyPress={onPress}
            display={{ '{enter}': '⏎', '{bksp}': '⌫' }}

        /> */}
        {rows.map((row) => {
            return <Row style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
            }}>
                {row.map((i) => <Key color={colorMap[i] ? colorMap[i] : '#313539'} width={''} id={i} onClick={onPress} />)}
            </Row>;
        })}
    </center>
    </div >
}

export default Keyboard;