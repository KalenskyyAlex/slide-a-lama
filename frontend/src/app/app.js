import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import Game from "../game/game";
import Login from "../login/login";
import Feedback from "../feedback/feedback";
import Score from "../score/score";
import Win from "../win/win";

const router = createBrowserRouter(
    [
        {
            path: "/",
            element: <Login/>
        },
        {
            path: "/game",
            element: <Game/>
        },
        {
            path: "/feedback",
            element: <Feedback/>
        },
        {
            path: "/scores",
            element: <Score/>
        },
        {
            path: "/win",
            element: <Win/>
        }
    ]
)

function App() {
    return (
        <RouterProvider router={router}/>
    );
}

export default App;
