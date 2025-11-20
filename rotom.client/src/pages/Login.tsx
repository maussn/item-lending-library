

export const Login = () => {
  return <div className="center-screen">
    <div className="box">
      <div className="row">
        <span className="input-label">Username:</span>
        <input id="username-input" />
      </div>
      
      <div className="row">
        <span className="input-label">Password:</span>
        <input type="password" id="password-input" />
      </div>

      <div className="button-row">
        <button>Login</button>
        <button>Register</button>
      </div>
    </div>
  </div>
}