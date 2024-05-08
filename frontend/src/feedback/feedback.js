import '../main.css';
import {useEffect, useRef, useState} from "react";

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

    const leaveFeedback = async () => {
        if (!checkString(rating.current.value)){
            alert("Rating is not a number");
            return;
        }

        let rating_int = parseInt(rating.current.value);
        if (rating_int > 5 || rating_int < 1){
            alert("Rating out of bounds");
            return;
        }

        if (comment.current.value.trim().length === 0){
            alert("Empty comment");
            return;
        }

        if (nickname.current.value.trim().length === 0){
            alert("Empty nickname");
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

        if(response.ok){
            alert("Comment uploaded successfully");
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
                setComments(json.slice(0, 2));
            }
        }

        getComments().catch(console.error);
    }, []);

    return (
        <div>
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
                                    style={{backgroundSize: "100% 100%", width: "100%", display: "block", margin: "0", position: "relative", padding: "0", textAlign: "center"}}>Submit</button>
                            <button onClick={clearInput} className="grid-item-alt"
                                    style={{backgroundSize: "100% 100%", width: "100%", display: "block", margin: "0", position: "relative", padding: "0", textAlign: "center"}}>Clear</button>
                        </div>
                    </div>
                    <div>
                        {
                            comments.map((comment) => {
                                return (
                                    <div>
                                        <div>{comment.player}</div>
                                        <br/>
                                        <div>{comment.comment}</div>
                                    </div>
                                )
                            })
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Feedback;
