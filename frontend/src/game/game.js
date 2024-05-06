import '../main.css';
import {useEffect} from "react";

const backend_endpoint = "http://localhost:8080/api";

function Game() {
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

        if (response.ok){
            return await response.json();
        }

        return null;
    };

    const tryInit = () => {
        if (localStorage.getItem("id") === null){
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
        if (response.ok){
            return await response.json();
        }

        return null;
    };

    const tryGetField = (id, row, col) => {
        if (localStorage.getItem("field") === null){
            getField(id).then(
                (result) => {
                    localStorage.setItem("field", JSON.stringify(result));
                }
            )

        }

        const field = JSON.parse(localStorage.getItem("field")).tiles[row][col];

        return field;
    }

    let tiles = [];
    for(let i = 0; i < 5; i++){
        for(let j = 0; j < 5; j++){
            tiles.push(tryGetField(tryInit(), i, j));
        }
    }

    return (
        <div>
            <div className="grid-container">
                {
                    tiles.map((tile, index) => (
                        <div className="grid-item">{tile}</div>
                    ))
                }
            </div>
        </div>
    );
}

export default Game;
