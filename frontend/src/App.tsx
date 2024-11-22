import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import SignIn from './components/registration/SignIn';
import SignUp from './components/registration/SignUp';
import ForgotPassword from './components/registration/ForgotPassword';
import EmailMainView from './components/email/EmailMainView';
import CrmPanelMainView from './components/crmPanel/CrmPanelMainView';

function App() {

  return (
    <Router>
    <Routes>
      <Route index element={<SignIn />} />
      <Route path="/signup" element={<SignUp />} />
      <Route path="/forgetpassword" element={<ForgotPassword />} />
      <Route path="/emailView" element={<EmailMainView />} />
      <Route path="/cmrPanel" element={<CrmPanelMainView />} />
    </Routes>
  </Router>
  );
}

export default App;
