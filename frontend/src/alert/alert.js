import '../main.css';
import {useEffect, useRef, useState} from "react";

const backend_endpoint = "http://localhost:8080/api";

function Alert(props) {
    return (
        <div id="alert-window" className="form-panel-big"
            style={{
                position: "absolute",
                zIndex: "100",
                width: "40%",
                height: "40%",
                top: "50%",
                left: "50%",
                margin: "-20% 0 0 -20%"
            }}>
            <div className="grid-container-1to1-v">
                <div>
                    {props.message}
                </div>
                <button onClick={()=> {document.getElementById("alert-window").remove(); window.location.replace(window.location.href)}}>
                    OK
                </button>
            </div>
        </div>
    );
}

export default Alert;
