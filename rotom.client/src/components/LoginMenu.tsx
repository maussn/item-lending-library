import { useLocation } from "react-router-dom";
import { NavButton } from "./NavButton";

interface LoginMenuProps {
  isLoggedIn: boolean;
  username?: string;
}

export const LoginMenu = ({ isLoggedIn, username }: LoginMenuProps) => {
  const { pathname } = useLocation();
  return (
    <div className="login-menu">
      {isLoggedIn ? (
        <div className="user-info">
          <span className="username">{username}</span>
          <button className="logout-button">Logout</button>
        </div>
      ) : (
        <NavButton to="/login" text="login" isActive={pathname === "/login"} />
      )}
    </div>
  );
};

// From chatgpt