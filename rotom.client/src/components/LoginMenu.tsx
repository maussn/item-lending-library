import { useLocation } from "react-router-dom";
import { NavButton } from "./NavButton";
import { useAuth } from "../contexts/AuthContext";

export const LoginMenu = () => {
  const { isLoggedIn, username, logout } = useAuth();
  const { pathname } = useLocation();
  return (
    <div className="login-menu">
      {isLoggedIn ? (
        <div className="user-info">
          <span className="username">{username}</span>
          <button className="logout-button" onClick={logout}>Logout</button>
        </div>
      ) : (
        <NavButton to="/login" text="login" isActive={pathname === "/login"} />
      )}
    </div>
  );
};

// From chatgpt