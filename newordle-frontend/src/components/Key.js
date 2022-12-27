import { Button } from 'react-bootstrap';

function Key(props) {

    return <Button
        className='.bg-dark'
        // onClick={props.onClick(props.id)}
        onClick={() => props.onClick(props.id)}
        style={{
            width: '30px',
            height: '40px',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            background: props.color,
            color: '#DADADA',
            border: 'white',
            borderWidth: '2px',
            borderRadius: '5px',
            margin: '2px',
            fontFamily: 'helvetica',
        }}
    >
        <b>
            {props.id}
        </b>
    </Button>;
}

export default Key;