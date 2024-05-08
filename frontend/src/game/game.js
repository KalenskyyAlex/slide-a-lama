import '../main.css';
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import matchers from "@testing-library/jest-dom/matchers";

const backend_endpoint = "http://localhost:8080/api";

function Game() {
    const [state, nextState] = useState(0);
    const navigate = useNavigate();

    const [nickname1, setNickname1] = useState("Player1");
    const [nickname2, setNickname2] = useState("Player2");
    const [score1, setScore1] = useState(0);
    const [score2, setScore2] = useState(0);
    const [lamasPos, setLamasPos] = useState([]);
    const [lamasDir, setLamasDir] = useState([]);
    const [lamaSide, setLamaSide] = useState([]);
    const [currentPlayer, setCurrentPlayer] = useState(1);

    const init = async () => {
        const credentials = {
            nickname1: localStorage.getItem("nickname1"),
            nickname2: localStorage.getItem("nickname2")
        };

        const response = await fetch(backend_endpoint + "/game/init", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(credentials)
        });

        if (response.ok) {
            return await response.json();
        }

        return null;
    };

    const tryInit = () => {
        if (localStorage.getItem("id") === null) {
            init().then(
                (result) => {
                    localStorage.setItem("id", '' + result);
                }
            );
        }

        return localStorage.getItem("id");
    }

    const getField = async (id) => {
        const response = await fetch(backend_endpoint + "/game/" + id + "/field");
        if (response.ok) {
            return await response.json();
        }

        return null;
    };

    const tryGetField = (id, row, col) => {
        getField(id).then(
            (result) => {
                localStorage.setItem("field", JSON.stringify(result));
            }
        )

        try {
            return JSON.parse(localStorage.getItem("field")).tiles[row][col];
        } catch {
        }
    }

    const getFront = async (id) => {
        const response = await fetch(backend_endpoint + "/game/" + id + "/front");
        if (response.ok) {
            return await response.json();
        }

        return null;
    }

    const tryGetFront = (id) => {
        getFront(id).then(
            (result) => {
                localStorage.setItem("front", JSON.stringify(result));
            }
        )

        try {
            return JSON.parse(localStorage.getItem("front"));
        } catch {
        }
    }

    let tiles = [];
    tiles.push("blank");
    for (let i = 0; i < 5; i++) {
        tiles.push("insert-up");
    }
    tiles.push("blank");
    for (let i = 0; i < 5; i++) {
        tiles.push("insert-left");
        for (let j = 0; j < 5; j++) {
            tiles.push(tryGetField(tryInit(), i, j));
        }
        tiles.push("insert-right");
    }

    const spamFruits = (match) => {
        for(let i = 0; i < match; i++){
            
        }
    }

    const insert = async (dir, index) => {
        const cursor = {
            side: dir,
            position: index
        };

        const response = await fetch(backend_endpoint + "/game/" + tryInit() + "/field", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(cursor)
        });

        if (response.ok) {
            const matches = await response.json()
            if(matches.length !== 0){
                spamFruits(matches);
            }
            tryGetField(tryInit(), 0, 0);
            nextState(state + 1);
        }

        return null;
    }

    const front = tryGetFront(tryInit());
    const mapping = {
        1: ["UP", 1],
        2: ["UP", 2],
        3: ["UP", 3],
        4: ["UP", 4],
        5: ["UP", 5],
        7: ["LEFT", 5],
        13: ["RIGHT", 1],
        14: ["LEFT", 4],
        20: ["RIGHT", 2],
        21: ["LEFT", 3],
        27: ["RIGHT", 3],
        28: ["LEFT", 2],
        34: ["RIGHT", 4],
        35: ["LEFT", 1],
        41: ["RIGHT", 5]
    }

    const renderLamas = () => {
        const lamas = 20;
        let newArray = [];
        let newArrayDir = [];
        if (lamasPos.length === 0) {
            newArray = [];
            for (let i = 0; i < lamas; i++) {
                newArray.push(Math.random() * 100);
                newArrayDir.push(Math.random() >= 0.5 ? 1 : -1);
            }

            setLamasPos(newArray);
            setLamasDir(newArrayDir);
        } else {
            newArrayDir = [...lamasDir];
            newArray = [...lamasPos];
        }

        let lamaPoolIdle = [];
        for (let i = 0; i < lamas; i++) {
            let dir = newArrayDir[i];

            if (dir > 0) {
                lamaPoolIdle.push(<div style={{left: newArray[i] + "%", bottom: "5%"}}
                                       className="lama" id={"lama" + i}></div>);
            } else {
                lamaPoolIdle.push(<div style={{left: newArray[i] + "%", bottom: "5%"}}
                                       className="r-lama" id={"r-lama" + i}></div>);
            }
        }

        return lamaPoolIdle;
    }

    useEffect(() => {
        setNickname1(localStorage.getItem("nickname1"));
        setNickname2(localStorage.getItem("nickname2"));
    }, []);

    useEffect(() => {
        async function getCurrentPlayer(){
            const response = await fetch(backend_endpoint + "/game/" + tryInit() + "/playerCurrent")

            if(response.ok){
                let playerId = await response.json()
                setCurrentPlayer(playerId);

                if (nickname2 === "Computer" && playerId === 2){
                    const response2 = await fetch(backend_endpoint + "/game/" + tryInit() + "/computerHelper");

                    if(response2.ok){
                        const cursor = await response2.json();
                        await insert(cursor.side, cursor.position)
                    }
                }
            }
        }

        getCurrentPlayer().catch(console.error);
    }, [state]);

    useEffect(() => {
        async function getScores() {
            const score1Response = await fetch(backend_endpoint + "/game/" + tryInit() + "/player1")
            if (score1Response.ok) {
                setScore1(await score1Response.json())
            }

            const score2Response = await fetch(backend_endpoint + "/game/" + tryInit() + "/player2")
            if (score2Response.ok) {
                setScore2(await score2Response.json())
            }
        }

        getScores().catch(console.error);
    })

    useEffect(() => {
        var id = setInterval(() => {
            const maxLamas = 20;
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
        }, 30)
    }, []);

    return (
        <div>
            <div className="grid-container-3">
                <div>
                    <div className="panel-mini" style={{
                        width: "20%", position: "relative", textAlign: "center", fontSize: "1.5em"
                    }}>{currentPlayer === 1 ? nickname1 : nickname2}'s turn</div>
                    <div style={{backgroundSize: "100% 100%",
                        top: "0%",
                        padding: "2%"}}
                         className="grid-container login-form-panel">
                        {
                            tiles.map((tile, index) => {
                                    try {
                                        if (tile.includes("insert")) {
                                            return <div className={"grid-item insert-button " + tile}
                                                        onClick={async () => await insert(mapping[index][0], mapping[index][1])}/>
                                        }
                                        return <div className={"grid-item " + tile}/>
                                    } catch {
                                        window.location.reload()
                                    }
                                }
                            )
                        }
                    </div>
                </div>
                <div>
                    <div className="panel-mini" style={{
                        position: "relative",
                        margin: "auto",
                        textAlign: "center",
                        top: "10%",
                    }}>Next:
                    </div>
                    <div style={{
                        position: "relative",
                        top: "15%",
                        height: "32vh",
                        width: "8vh",
                        minHeight: "20vh",
                        padding: "5%",
                        backgroundSize: "100% 100%"
                    }}
                         className="login-form-panel">
                        <div style={{margin: "0", height: "100%", width: "100%"}} className="grid-container-4">
                            {
                                front.map((tile) => {
                                        try {
                                            return <div className={"grid-item " + tile}/>
                                        } catch {
                                            window.location.reload()
                                        }
                                    }
                                )
                            }
                        </div>
                    </div>
                </div>
            </div>
            <div style={{textAlign: "center", position: "absolute", bottom: "10%", paddingRight: "10%"}}
                 className="footer">
                <button onClick={() => navigate("/feedback")}>FEEDBACK</button>
                <button onClick={() => navigate("/scores")}>SCORES</button>
                <br/>
                <button style={{
                    display: "inline-block",
                    margin: "auto",
                    position: "relative",
                    width: "100%",
                    height: "100%",
                    backgroundSize: "100% 100%"
                }} onClick={() => navigate("/win")}>INSTAWIN (DEBUG)
                </button>
            </div>
            <div
                style={{textAlign: "center", width: "100%", height: "100%", position: "fixed", top: "100%", left: "0"}}>
                <div style={{
                    position: "fixed",
                    width: "10vh",
                    height: "auto",
                    bottom: "20%",
                    left: "20%",
                    textAlign: "center"
                }} className="panel-mini grid-item-alt">{nickname1}<br/>{score1}</div>
                <div style={{
                    position: "fixed",
                    width: "10vh",
                    height: "auto",
                    bottom: "20%",
                    right: "20%",
                    textAlign: "center"
                }} className="panel-mini grid-item-alt">{nickname2}<br/>{score2}</div>
                <div className="lamas-container">
                    {
                        renderLamas()
                    }
                </div>
            </div>
        </div>
    );
}

export default Game;
