import Keyboard from 'react-simple-keyboard';

import 'react-simple-keyboard/build/css/index.css';
import './keyboard.css';

function KeyboardComp() {

    const layout = {
        'default': [
            'Q W E R T Y U I O P',
            'A S D F G H J K L',
            '{enter} Z X C V B N M {bksp}',
        ],
    }

    const onPress = (e) => {
        if (e === "{enter}") {
            const enterEvent = new KeyboardEvent('keyup', { 'key': "Enter" });
            document.dispatchEvent(enterEvent);
        } else if (e === "{bksp}") {
            const enterEvent = new KeyboardEvent('keyup', { 'key': "Backspace" });
            document.dispatchEvent(enterEvent);
        } else {
            const keyPress = new KeyboardEvent('keyup', { 'key': e.slice(-1) });
            document.dispatchEvent(keyPress);
        }
    }

    return <div>
        <Keyboard
            layout={layout}
            theme={"hg-theme-default myTheme1"}
            onKeyPress={onPress}
            display={{ '{enter}': '⏎', '{bksp}': '⌫' }}

        />
    </div>
}

export default KeyboardComp;