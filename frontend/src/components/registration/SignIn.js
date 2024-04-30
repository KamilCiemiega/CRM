import { useState, useEffect } from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { Link } from "react-router-dom";
import { GoogleLogin } from "@react-oauth/google";
import { useDispatch } from "react-redux";
import { signInAction, updateGoogleCredentials } from "../store/signIn-slice";
import { Alert } from "@mui/material";
import axios from "axios";

const defaultTheme = createTheme();

const SignIn = () => {
  const [googleError, setGoogleError] = useState(false);
  const [requestError, setRequestError] = useState(false);
  const dispatch = useDispatch();

  useEffect(() => {
    const timer = setTimeout(() => {
      setGoogleError(false);
      setRequestError(false);
    }, 3000);

    return () => clearTimeout(timer);
  }, [googleError, requestError]);

  const handleSubmit = async (event) => {
    event.preventDefault();

    const data = new FormData(event.currentTarget);
    const email = data.get("email");
    const password = data.get("password");
  
    try {
      const response = await axios.get(`http://localdev:8082/api/auth/login?email=${email}&password=${password}`);
      const firstName = response.data.firstName;
      const lastName = response.data.lastName;
      console.log(firstName, lastName);
      dispatch(signInAction.setLoggedInUserData({firstName, lastName}))
    } catch (error) {
      console.log(error);
      setRequestError(true);
    }
  };

  const handleGoogleLoginSuccess = (credentialResponse) => {
    dispatch(updateGoogleCredentials(credentialResponse));
  };

  const handleGoogleLoginError = () => {
    setGoogleError(true);
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        {googleError && (
          <Alert severity="error" onClose={() => setGoogleError(false)}>
            Login with google account failed
          </Alert>
        )}
        {requestError && (
          <Alert severity="error" onClose={() => setRequestError(false)}>
            Error occurred while sending request to localdev
          </Alert>
        )}
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <FormControlLabel
              control={<Checkbox value="remember" color="primary" />}
              label="Remember me"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Sign In
            </Button>
            <Grid container>
              <Grid item xs>
                <Link to="/forgetpassword" variant="body2">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link to="/signup" variant="body2">
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </Box>
          <Box
            component="div"
            sx={{
              mt: 5,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Typography component="span" variant="body2" sx={{ mb: 3 }}>
              --------------- Or continue with google -----------------
            </Typography>
            <GoogleLogin
              onSuccess={handleGoogleLoginSuccess}
              onError={handleGoogleLoginError}
            ></GoogleLogin>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default SignIn;
