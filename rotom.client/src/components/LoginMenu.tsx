import { Link } from "react-router-dom";

interface LoginMenuProps {
  isLoggedIn: boolean;
  username?: string;
}

export const LoginMenu = ({ isLoggedIn, username }: LoginMenuProps) => {
  return (
    <div className="login-menu">
      {isLoggedIn ? (
        <div className="user-info">
          <span className="username">{username}</span>
          <button className="logout-button">Logout</button>
        </div>
      ) : (
        <Link to="/login" className="login-button">
          Login
        </Link>
      )}
    </div>
  );
};

// From chatgpt