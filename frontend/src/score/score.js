import '../main.css';
import {useEffect, useState} from "react";

const backend_endpoint = "http://localhost:8080/api";

function checkString(string) {
    return /^[0-9]*$/.test(string);
}

function Score() {
    const [scores, setScores] = useState([]);
    const [intervalId, setIntervalId] = useState(null);

    useEffect(() => {
        async function getScores(){
            const response = await fetch(backend_endpoint + "/score/Slide a Lama")

            if (response.ok){
                setScores(await response.json());
            }
        }

        getScores().catch(console.error);
    }, []);

    useEffect(() => {
        let id = setInterval(() => {
            const maxLamas = 15;
            for(let i = 0; i < maxLamas; i++){
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
                }}>BACK</button>
            </div>
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
                            if (index === 0) {
                                return (<div className="grid-item-alt grid-container-1to5to2">
                                    <div className="gold grid-item-alt"></div>
                                    <div style={{margin: "auto 0"}}>{score.player}</div>
                                    <div style={{margin: "auto 0"}}>{score.points}</div>
                                </div>)
                            } else if (index === 1) {
                                return (<div className="grid-item-alt grid-container-1to5to2">
                                    <div className="silver grid-item-alt"></div>
                                    <div style={{margin: "auto 0"}}>{score.player}</div>
                                    <div style={{margin: "auto 0"}}>{score.points}</div>
                                </div>)
                            } else if (index === 2) {
                                return (<div className="grid-item-alt grid-container-1to5to2">
                                    <div className="bronze grid-item-alt"></div>
                                    <div style={{margin: "auto 0"}}>{score.player}</div>
                                    <div style={{margin: "auto 0"}}>{score.points}</div>
                                </div>)
                            } else {
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
            <div className="lamas-container">
                {
                    renderLamas()
                }
            </div>
        </div>
    );
}

export default Score;
