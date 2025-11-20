import { Outlet, useLocation } from "react-router-dom";
import logo from "../assets/0479Rotom-Mow.png";
import { NavButton } from "../components/NavButton";

export const RootLayout = () => {
  const { pathname } = useLocation();

  return <div>
    <header id="logo-header">
      <img src={logo}/>
      <nav>
        <NavButton to="/" text="catalog" isActive={pathname === "/"} />
        <NavButton to="/login" text="login" isActive={pathname === "/login"}/>
      </nav>
    </header>

    <main className="flex-1">
      <Outlet />
    </main>
  </div>
};
