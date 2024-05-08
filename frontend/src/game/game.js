import '../main.css';
import {useState} from "react";
import {useNavigate} from "react-router-dom";

const backend_endpoint = "http://localhost:8080/api";

function Game() {
    const [state, nextState] = useState(0);
    const navigate = useNavigate();

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
        if(localStorage.getItem("id") === null){
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
        }
        catch {
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

        try{
            return JSON.parse(localStorage.getItem("front"));
        }
        catch {
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

    const insert = async (dir, index) => {
        console.log(dir, index);
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
            tryGetField(tryInit(), 0, 0);
            nextState(state + 1);
        }

        return null;
    }

    const front = tryGetFront(tryInit());
    const mapping= {
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

    return (
        <div>
            <div className="grid-container-3">
                <div className="grid-container">
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
                <div className="grid-container-4">
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
            <div style={{position: "absolute", bottom: "10%"}} className="footer">
                <button onClick={() => navigate("/feedback")}>FEEDBACK</button>
                <button onClick={() => navigate("/scores")}>SCORES</button>
            </div>
        </div>
    );
}

export default Game;
