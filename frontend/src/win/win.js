import '../main.css';
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";

const backend_endpoint = "http://localhost:8080/api";

function Win() {
    const navigate = useNavigate();
    const [winner, setWinner] = useState(null);
    const [score, setScore] = useState(null);
    const [currentPlayer, setCurrentPlayer] = useState(1);
    const [state, nextState] = useState(0);

    useEffect(() => {
        if (state === 0){
            nextState(state + 1);
        }
        async function saveScore(){
            const response = await fetch(backend_endpoint + "/game/" + localStorage.getItem("id") + "/playerCurrent")


            if(response.ok) {
                let playerId = await response.json()
                setCurrentPlayer(playerId);
                if(playerId === 1){
                    const response2 = await fetch(backend_endpoint + "/game/" + localStorage.getItem("id") + "/player1")
                    if(response2.ok){
                        let score = await response2.json()
                        setScore(score);

                        const newScore = {
                            player: localStorage.getItem("nickname1"),
                            playedOn: new Date().toISOString().slice(0, 19),
                            game: "Slide a Lama",
                            points: score
                        }

                        await fetch(backend_endpoint + "/score",
                            {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/json",
                                },
                                body: JSON.stringify(newScore)
                            });
                    }
                    setWinner(localStorage.getItem("nickname1"));
                }
                else{
                    const response2 = await fetch(backend_endpoint + "/game/" + localStorage.getItem("id") + "/player2")
                    if(response2.ok){
                        let score = await response2.json()
                        setScore(score);

                        const newScore = {
                            player: localStorage.getItem("nickname2"),
                            playedOn: new Date().toISOString().slice(0, 19),
                            game: "Slide a Lama",
                            points: score
                        }

                        await fetch(backend_endpoint + "/score",
                            {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/json",
                                },
                                body: JSON.stringify(newScore)
                            });
                    }
                    setWinner(localStorage.getItem("nickname2"));
                }
            }
        }

        saveScore().catch(console.error);
    }, []);

    return (
        <div className="form-panel-big"
             style={{
                 position: "absolute",
                 zIndex: "100",
                 width: "40%",
                 height: "40%",
                 maxWidth: "450px",
                 top: "50%",
                 left: "50%",
                 margin: "-20% 0 0 -20%"
             }}>
            <div style={{width: "100%", maxWidth: "600px"}} className="grid-container-1to1-v">
                <div style={{width: "75%"}}>
                    <h2 style={{textAlign: "center"}}>Congratulations {winner}!!!</h2>
                    <h3>Score: {score}</h3>
                    <br/>
                    <br/>
                    <h3>Play againe, leave feedback, or check if you made<br/> it out to top-10!</h3>
                </div>
                <div style={{
                    position: "relative",
                    width: "100%",
                    margin: "0",
                    padding: "0",
                    textAlign: "center"
                }} className="grid-container-1to1to1-h">
                    <button style={{
                        display: "inline-block",
                        width: "20vh",
                        height: "5vh",
                        backgroundSize: "100% 100%"
                    }} className="grid-item-alt" onClick={() => {
                        localStorage.clear();
                        navigate("/")
                    }}>
                        MENU
                    </button>
                    <button style={{
                        display: "inline-block",
                        width: "20vh",
                        left: "-30%",
                        height: "5vh",
                        backgroundSize: "100% 100%"
                    }} className="grid-item-alt" onClick={() => {
                        localStorage.clear();
                        navigate("/scores")
                    }}>
                        SCORES
                    </button>
                    <button className="grid-item-alt" style={{
                        display: "inline-block",
                        width: "23vh",
                        height: "5vh",
                        left: "-70%",
                        backgroundSize: "100% 100%"
                    }} onClick={() => {
                        localStorage.clear();
                        navigate("/feedback")
                    }}>
                        FEEDBACK
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Win;
