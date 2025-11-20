import { createBrowserRouter } from "react-router-dom";
import { RootLayout } from "./layout/RootLayout";
import { Login } from "./pages/Login";
import { Catalogue } from "./pages/Catalogue";


export const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      {
        index: true,
        element: <Catalogue />
      },
      {
        path: "login",
        element: <Login />
      }
    ]
  }
])