import '../main.css';
import {useEffect, useRef, useState} from "react";
import Alert from "../alert/alert";
import ReactDOM from "react-dom/client";

const backend_endpoint = "http://localhost:8080/api";

function checkString(string) {
    return /^[0-9]*$/.test(string);
}

function Feedback() {
    // const [state, nextState] = useState(0);
    const rating = useRef(null);
    const comment = useRef(null);
    const nickname = useRef(null);
    const [comments, setComments] = useState([]);
    const [intervalId, setIntervalId] = useState(null);

    const leaveFeedback = async () => {
        if (!checkString(rating.current.value)){
            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Rating is not a number"/>);
            return;
        }

        let rating_int = parseInt(rating.current.value);
        if (rating_int > 5 || rating_int < 1){
            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Rating out of bounds"/>);
            return;
        }

        if (comment.current.value.trim().length === 0){
            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Empty comment"/>);
            return;
        }

        if (nickname.current.value.trim().length === 0){
            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Empty nickname"/>);
            return;
        }

        const newComment = {
            game: "Slide a Lama",
            comment: comment.current.value,
            player: nickname.current.value,
            commentedOn: new Date().toISOString().slice(0, 19)
        }

        const response = await fetch(backend_endpoint + "/comment",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newComment)
            });

        const newRating = {
            game: "Slide a Lama",
            rating: rating_int,
            player: nickname.current.value,
            ratedOn: new Date().toISOString().slice(0, 19)
        }


        const response2 = await fetch(backend_endpoint + "/rating",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(newRating)
            });

        if(response.ok && response2.ok){
            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Feedback uploaded successfully"/>);
        }
    }

    const clearInput = () => {
        comment.current.value = "";
        rating.current.value = "";
        nickname.current.value = "";
    }

    useEffect(() => {
        async function getComments(){
            const response = await fetch(backend_endpoint + "/comment/Slide a Lama")
            if (response.ok){
                const json = await response.json();
                console.log(json);
                setComments(json.slice(0, 4));
            }
        }

        getComments().catch(console.error);
    }, []);

    useEffect(() => {
        let id = setInterval(() => {
            const maxLamas = 15;
            for(let i = 0; i < maxLamas; i++){
                try{
                    if(document.getElementById('lama'+i) !== null){
                        let old = document.getElementById('lama'+i).style.getPropertyValue("left").replace('%', '');
                        let new_ = (parseFloat(old) + 0.1) % 110;
                        document.getElementById('lama'+i).style.setProperty("left", new_ + "%");
                    }
                    else{
                        let old = document.getElementById('r-lama'+i).style.getPropertyValue("left").replace('%', '');
                        let new_ = (parseFloat(old) - 0.1);
                        if(new_ < -10){
                            new_ = 110;
                        }
                        document.getElementById('r-lama'+i).style.setProperty("left", new_ + "%");
                    }
                }
                catch {
                    clearInterval(id);
                }
            }
        }, 30);

        setIntervalId(id);
    }, []);

    const renderLamas = () => {
        const lamas = 15;
        let lamaPoolIdle = [];
        for(let i = 0; i < lamas; i++){
            let dir = Math.random();
            let bounce_height = Math.random();
            let bounce_class = "bounce";
            if(bounce_height >= 0.8){
                bounce_class = "bounce-high";
            }
            else if(bounce_height <= 0.2){
                bounce_class = "bounce-low";
            }
            if (dir >= 0.5){
                lamaPoolIdle.push(<div style={{left:  Math.random()*100 + "%", bottom: "5%"}} className={bounce_class + " lama"} id={"lama"+i}></div>);
            }
            else{
                lamaPoolIdle.push(<div style={{left:  Math.random()*100 + "%", bottom: "5%"}} className={bounce_class + " r-lama"} id={"r-lama"+i}></div>);
            }
        }

        return lamaPoolIdle;
    }

    const lamaSad = () => {
        for(let i = 0; i < 20; i++){
            try{
                if(document.getElementById('lama'+i) !== null){
                    document.getElementById('lama'+i).setAttribute("class", "sad-lama fall-clock-wise")
                }
                else{
                    document.getElementById('r-lama'+i).setAttribute("class", "r-sad-lama fall-counter-clock-wise")
                }
            }
            catch {
            }
        }

        clearInterval(intervalId);
    }

    return (
        <div>
            <div style={{
                zIndex: "100",
                position: "absolute",
                top: "2vh",
                left: "2vh",
                width: "20vh",
                height: "5vh",
                padding: "0"
            }}>
                <button onClick={() => {
                    lamaSad();
                    setTimeout(() => window.history.go(-1), 1000)
                }}>BACK
                </button>
            </div>
            <div className="header">
                <div className="panel-mini">FEEDBACK</div>
            </div>
            <div style={{
                top: "3vh",
                width: "60vw",
                maxWidth: "600px",
                minWidth: "450px",
                height: "50vh",
                margin: "auto",
                backgroundSize: "100% 100%"
            }} className="form-panel-big">
                <div className="grid-container-1to2to1-v">
                    <div className="grid-item-alt grid-container-2">
                        <div className="left grid-item-alt">
                            <input style={{top: "10%"}}
                                   type="text"
                                   placeholder="Nickname"
                                   ref={nickname}
                            />
                        </div>
                        <div className="right grid-item-alt">
                            <input style={{top: "10%"}}
                                   type="number"
                                   placeholder="Rating (from 1 to 5)"
                                   ref={rating}
                            />
                        </div>
                    </div>
                    <div
                        style={{padding: "0 5% 3% 5%"}} className="grid-item-alt grid-container-3to1-h">
                        <textarea
                            placeholder="Your comment"
                            cols="30"
                            ref={comment}
                            rows="5"
                            className="grid-item-alt"/>
                        <div className="grid-item-alt grid-container-1to1-v">
                            <button onClick={async () => await leaveFeedback()} className="grid-item-alt"
                                    style={{
                                        backgroundSize: "100% 100%",
                                        width: "100%",
                                        display: "block",
                                        margin: "0",
                                        position: "relative",
                                        padding: "0",
                                        textAlign: "center"
                                    }}>Submit
                            </button>
                            <button onClick={clearInput} className="grid-item-alt"
                                    style={{
                                        backgroundSize: "100% 100%",
                                        width: "100%",
                                        display: "block",
                                        margin: "0",
                                        position: "relative",
                                        padding: "0",
                                        textAlign: "center"
                                    }}>Clear
                            </button>
                        </div>
                    </div>
                    <div style={{paddingLeft: "20px"}}>
                        <div style={{margin: "3% auto", textAlign: "center"}}> RECENT COMMENTS</div>
                        <div className="grid-container-2x2 grid-item-alt">
                            {
                                comments.map((comment) => {
                                    return (
                                        <div>
                                            <div style={{color: "red"}}>{comment.player}</div>
                                            <div style={{padding: "2% 6%"}}>{comment.comment}</div>
                                        </div>
                                    )
                                })
                            }
                        </div>
                    </div>
                </div>
            </div>
            <div className="lamas-container">
                {
                    renderLamas()
                }
            </div>
        </div>
    );
}

export default Feedback;
