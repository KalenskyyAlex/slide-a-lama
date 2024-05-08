import '../main.css';
import {useEffect, useState} from "react";

const backend_endpoint = "http://localhost:8080/api";

function checkString(string) {
    return /^[0-9]*$/.test(string);
}

function Score() {
    const [scores, setScores] = useState([]);

    useEffect(() => {
        async function getScores(){
            const response = await fetch(backend_endpoint + "/score/Slide a Lama")

            if (response.ok){
                setScores(await response.json());
            }
        }

        getScores().catch(console.error);
    }, []);

    return (
        <div>
            <div style={{zIndex: "100", position: "absolute", top: "2vh", left: "2vh", width: "20vh", height: "5vh", padding: "0"}}><button onClick={() => window.history.go(-1)}>BACK</button></div>
            <div className="header">
                <div className="panel-mini">SCORES</div>
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
                <div className="score-grid">
                    {
                        scores.map((score, index) => {
                            if (index === 0){
                                return (<div className="grid-item-alt grid-container-1to5to2">
                                    <div className="gold grid-item-alt"></div>
                                    <div style={{margin: "auto 0"}}>{score.player}</div>
                                    <div style={{margin: "auto 0"}}>{score.points}</div>
                                </div>)
                            }
                            else if (index === 1){
                                return (<div className="grid-item-alt grid-container-1to5to2">
                                    <div className="silver grid-item-alt"></div>
                                    <div style={{margin: "auto 0"}}>{score.player}</div>
                                    <div style={{margin: "auto 0"}}>{score.points}</div>
                                </div>)
                            } else if (index === 2){
                                return (<div className="grid-item-alt grid-container-1to5to2">
                                    <div className="bronze grid-item-alt"></div>
                                    <div style={{margin: "auto 0"}}>{score.player}</div>
                                    <div style={{margin: "auto 0"}}>{score.points}</div>
                                </div>)
                            } else{
                                return (<div className="grid-item-alt grid-container-1to5to2">
                                    <div style={{margin: "auto"}}>{index + 1}</div>
                                    <div style={{margin: "auto 0"}}>{score.player}</div>
                                    <div style={{margin: "auto 0"}}>{score.points}</div>
                                </div>)
                            }
                        })
                    }
                </div>
            </div>
        </div>
    );
}

export default Score;
