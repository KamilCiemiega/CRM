import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignIn from './components/registration/SignIn';
import SignUp from './components/registration/SignUp';

function App() {
  return (
    <Router>
    <Routes>
      <Route exact path="/" element={<SignIn />} />
      <Route exact path="/signup" element={<SignUp />} />
    </Routes>
  </Router>
  );
}

export default App;
