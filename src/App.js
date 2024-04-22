import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignIn from './components/registration/SignIn';
import SignUp from './components/registration/SignUp';
import ForgotPassword from './components/registration/ForgotPassword';
import EmailMainView from './components/email/EmailMainView';

function App() {
  return (
    <Router>
    <Routes>
      <Route exact path="/" element={<SignIn />} />
      <Route exact path="/signup" element={<SignUp />} />
      <Route exact path="/forgetpassword" element={<ForgotPassword />} />
      <Route exact path="/emailView" element={<EmailMainView />} />
    </Routes>
  </Router>
  );
}

export default App;
