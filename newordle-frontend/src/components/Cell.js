import { Card } from 'react-bootstrap';

function Cell(props) {
    var letter = '';
    if(letter==='') {
        letter = props.active ? props.text : '';
    }
    // console.log(props.rowid + "---" + props.cell  + "---" + props.active + "---" + letter);

    return <Card
        className='.bg-dark'
        style={{
            width: '2rem',
            height: '2rem',
            margin: '5px' }}
    >
        {props.active ? props.text : ''}
    </Card>;
}

export default Cell;