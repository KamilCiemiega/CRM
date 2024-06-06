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
import { Link, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { signInAction } from "../store/slices/authSlices/signIn-slice";
import { Alert } from "@mui/material";
import axios from "axios";
import { useFormik, FormikHelpers } from "formik";
import * as yup from "yup";

const defaultTheme = createTheme();

interface SignInFormValues {
  email: string;
  password: string;
}

const SignIn: React.FC = () => {
  const [requestError, setRequestError] = useState<boolean>(false);
  const navigate = useNavigate(); 
  const dispatch = useDispatch();

  useEffect(() => {
    const timer = setTimeout(() => {
      setRequestError(false);
    }, 3000);

    return () => clearTimeout(timer);
  }, [requestError]);


  const formik = useFormik<SignInFormValues>({
    initialValues: {
      email: "",
      password: "",
    },
    validationSchema: yup.object({
      email: yup.string().required("This field can't be empty"),
      password: yup.string().required("This field can't be empty"),
    }),
    onSubmit: async (values: SignInFormValues, { setSubmitting }: FormikHelpers<SignInFormValues>) => {
      const { email, password } = values;
    
    try {
      const response = await axios.get(`http://localdev:8082/api/auth/login?email=${email}&password=${password}`);
      const firstName: string = response.data.firstName;
      const lastName: string = response.data.lastName;
      
      dispatch(signInAction.setLoggedInUserData({firstName, lastName}))

      if(response.status === 200){
        navigate('/emailView');
      }

    } catch (error) {
      setRequestError(true);
    } finally {
      setSubmitting(false);
    }
    },
  });


  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        {requestError && (
          <Alert severity="error" onClose={() => setRequestError(false)}>
            Invalid username or password
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
            onSubmit={formik.handleSubmit}
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
              autoFocus
              value={formik.values.email}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={formik.touched.email && Boolean(formik.errors.email)}
              helperText={formik.touched.email && formik.errors.email}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              value={formik.values.password}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={formik.touched.password && Boolean(formik.errors.password)}
              helperText={formik.touched.password && formik.errors.password}
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
                <Link to="/forgetpassword">
                  Forgot password?
                </Link>
              </Grid>
              <Grid item>
                <Link to="/signup" >
                  {"Don't have an account? Sign Up"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default SignIn;
