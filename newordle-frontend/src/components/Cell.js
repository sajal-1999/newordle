import { Card } from 'react-bootstrap';

function Cell(props) {
    var letter = '';
    if (letter === '') {
        letter = props.active ? props.text : '';
    }
    // console.log(props.rowid + "---" + props.cell  + "---" + props.active + "---" + letter);

    return <Card
        className='.bg-dark'
        style={{
            width: '3rem',
            height: '3rem',
            margin: '5px',
            color: 'black',
            background: props.background,
            border: `2px solid white`,
            justifyContent: 'center'
        }}
    >
        <b>
            {props.active ? props.text : ''}
        </b>
    </Card>;
}

export default Cell;