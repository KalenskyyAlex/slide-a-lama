import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import Game from "../game/game";
import Login from "../login/login";
import Feedback from "../feedback/feedback";

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
        }
    ]
)

function App() {
    return (
        <RouterProvider router={router}/>
    );
}

export default App;
