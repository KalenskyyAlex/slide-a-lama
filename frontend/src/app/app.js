import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import Game from "../game/game";
import Login from "../login/login";

const router = createBrowserRouter(
    [
        {
            path: "/",
            element: <Login/>
        },
        {
            path: "/game",
            element: <Game/>
        }
    ]
)

function App() {
    return (
        <RouterProvider router={router}/>
    );
}

export default App;
