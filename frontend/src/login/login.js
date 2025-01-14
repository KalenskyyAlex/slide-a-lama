import '../main.css';
import {useNavigate} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import ReactDOM from "react-dom/client";
import Alert from "../alert/alert";

const backend_endpoint = "http://localhost:8080/api";

function Login() {
    const navigate = useNavigate();

    const p1UsernameRef = useRef(null);
    const p1PasswordRef = useRef(null);
    const p2PasswordRef = useRef(null);
    const p2UsernameRef = useRef(null);
    const p2SignInRef = useRef(null);
    const p2LogInRef = useRef(null);
    const p2CompRef = useRef(null);
    const [intervalId, setIntervalId] = useState(null);

    const [enableP2LogIn, setEnableP2LogIn] = useState(true);
    const [enableP2SignIn, setEnableP2SignIn] = useState(true);
    const [enableP2Comp, setEnableP2Comp] = useState(true);

    const activate = () => {
        p2PasswordRef.current.className = '';
        p2UsernameRef.current.className = '';
        setEnableP2LogIn(!enableP2LogIn);
        setEnableP2SignIn(!enableP2SignIn);
        setEnableP2Comp(!enableP2Comp);
    };

    const read_credentials = (username, password) => {
        let usernameValue = username.current.value;
        let passwordValue = password.current.value;
        if (usernameValue.trim().length === 0) {
            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Empty username"/>);
            return [false, null, null];
        }
        if (passwordValue.trim().length === 0) {
            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Empty password"/>);
            return [false, null, null];
        }

        return [true, usernameValue, passwordValue];
    }

    const activate_login = async () => {
        const [ok, username, password] = read_credentials(p1UsernameRef, p1PasswordRef)
        if (ok) {
            const response = await fetch(backend_endpoint + "/user/Slide a Lama/" + username);
            if (response.ok) {
                const user = await response.json();
                if (user.password === password) {
                    localStorage.setItem("nickname1", user.username);
                    activate();
                    return;
                }
            }

            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Wrong credentials"/>);
        }
    }

    const activate_signup = async () => {
        const [ok, username, password] = read_credentials(p1UsernameRef, p1PasswordRef)
        if (ok) {
            const getResponse = await fetch(backend_endpoint + "/user/Slide a Lama/" + username);
            if (getResponse.ok) {
                const user = await getResponse.json();
                if (user.username === "NOT FOUND") {
                    const newUser = {
                        "username": username,
                        "game": "Slide a Lama",
                        "password": password
                    };

                    const postResponse = await fetch(backend_endpoint + "/user", {
                        method: "POST", body: JSON.stringify(newUser),
                        headers: {
                            "Content-Type": "application/json",
                        }
                    });

                    if (postResponse.ok) {
                        const root = ReactDOM.createRoot(document.getElementById('root'));
                        root.render(<Alert message="New user created"/>);
                        localStorage.setItem("nickname1", user.username);
                        activate();
                        return;
                    }
                }
            }

            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Could not create new user"/>);
        }
    }

    const start_login = async () => {
        const [ok, username, password] = read_credentials(p2UsernameRef, p2PasswordRef)
        if (ok) {
            const response = await fetch(backend_endpoint + "/user/Slide a Lama/" + username);
            if (response.ok) {
                const user = await response.json();
                if (user.password === password) {
                    localStorage.setItem("nickname2", user.username);
                    navigate('/game');
                    return;
                }
            }

            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Wrong credentials"/>);
        }
    }

    const start_signup = async () => {
        const [ok, username, password] = read_credentials(p2UsernameRef, p2PasswordRef)
        if (ok) {
            const getResponse = await fetch(backend_endpoint + "/user/Slide a Lama/" + username);
            if (getResponse.ok) {
                const user = await getResponse.json();
                if (user.username === "NOT FOUND") {
                    const newUser = {
                        "username": username,
                        "game": "Slide a Lama",
                        "password": password
                    };

                    const postResponse = await fetch(backend_endpoint + "/user", {
                        method: "POST", body: JSON.stringify(newUser),
                        headers: {
                            "Content-Type": "application/json",
                        }
                    });

                    if (postResponse.ok) {
                        const root = ReactDOM.createRoot(document.getElementById('root'));
                        root.render(<Alert message="New user created"/>);
                        localStorage.setItem("nickname1", user.username);
                        navigate('/game');
                        return;
                    }
                }
            }

            const root = ReactDOM.createRoot(document.getElementById('root'));
            root.render(<Alert message="Could not create new user"/>);
        }
    }

    const start_comp = () => {
        // TODO create coop mode
        lamaSad();
        localStorage.setItem("nickname2", "Computer");
        setTimeout(() => {navigate('/game')}, 1000)
    }

    useEffect(() => {
        const id = setInterval(() => {
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
        setIntervalId(id)
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
        <div className="Login">
            <div className="header">
                <img className="logo" alt="Slide a Lama" src="/visual/logo.png"/>
            </div>
            <div className="grid-container-2">
                <div className="left">
                    <div className="login-form">
                        <div className="header" style={{padding: 0}}>
                            <div className="panel-mini">Player 1</div>
                        </div>
                        <div className="login-form-panel">
                            <br/>
                            <label>Username:</label>
                            <input type="text"
                                   id="first"
                                   ref={p1UsernameRef}
                                   name="first"
                                   placeholder="Enter your Username" required/>
                            <label>Password:</label>
                            <input type="password"
                                   id="password"
                                   ref={p1PasswordRef}
                                   name="password"
                                   placeholder="Enter your Password" required/>
                            <button onClick={activate_login} style={{display: "inline"}}>
                                login
                            </button>
                            <button onClick={activate_signup} style={{display: "inline"}}>
                                sign up
                            </button>
                            <div className="header">
                                <br/>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="right">
                    <div className="login-form">
                        <div className="header" style={{padding: 0}}>
                            <div className="panel-mini">Player 2</div>
                        </div>
                        <div className="login-form-panel">
                            <br/>
                            <label>
                                Username:
                            </label>
                            <input className="disabled"
                                   type="text"
                                   id="first"
                                   ref={p2UsernameRef}
                                   name="first"
                                   placeholder="Enter your Username" required/>
                            <label>
                                Password:
                            </label>
                            <input className="disabled"
                                   type="password"
                                   id="password"
                                   ref={p2PasswordRef}
                                   name="password"
                                   placeholder="Enter your Password" required/>
                            <button disabled={enableP2LogIn} ref={p2LogInRef} onClick={start_login}
                                    style={{display: "inline"}}>
                                login
                            </button>
                            <button disabled={enableP2SignIn} ref={p2SignInRef} onClick={start_signup}
                                    style={{display: "inline"}}>
                                sign up
                            </button>
                            <button disabled={enableP2Comp} ref={p2CompRef} onClick={start_comp}
                                    style={{display: "inline", top: "-40px", marginLeft: "50px"}}>
                                Play with Computer
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div className="footer">
                <button onClick={() => navigate("/feedback")}>FEEDBACK</button>
                <button onClick={() => navigate("/scores")}>SCORES</button>
            </div>
            <div className="lamas-container">
                {
                    renderLamas()
                }
            </div>
        </div>
    );
}

export default Login;
