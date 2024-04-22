import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useFormik } from "formik";
import * as yup from "yup";
import { Link } from "react-router-dom";
import axios from "axios";

const defaultTheme = createTheme();

const httpAddress = 'http://localhost:8082/users'

const fetchCSRFToken = async () => {
  try {
      // const response = await axios.get(`${httpAddress}/csrf-token`);
      // console.log(response.data.csrfToken);
      // return response.data.csrfToken;
      const csrfToken = document.cookie.match(/XSRF-TOKEN=([\w-]+)/)?.[1];
      console.log(csrfToken);
      return csrfToken;
  } catch (error) {
      console.error('Error while trying to get CSRF token: ', error);
      throw error; 
  }
};


const SignUp = () => {
  const formik = useFormik({
    initialValues: {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
    },
    validationSchema: yup.object({
      firstName: yup.string().required("FirstName is required"),
      lastName: yup.string().required("lastName is required"),
      email: yup
        .string()
        .email("Invalid email address")
        .required("Email is required"),
      password: yup
        .string()
        .required("Password is required")
        .min(6, "Password must be at least 6 characters")
        .matches(
          /(?=.*[A-Z])/g,
          "Password must contain at least one uppercase letter"
        )
        .matches(
          /(?=.*[!@#$%^&*])/g,
          "Password must contain at least one special character"
        ),
    }),
    onSubmit: async (values) => {
      try {
        const username = "admin";
        const password = "admin";
        const token = btoa(`${username}:${password}`);
        // const csrfToken = await fetchCSRFToken();
        const response = await axios.post(`${httpAddress}/save`, values ,{
          headers: {
            'Authorization': `Basic ${token}`,
            // "X-XSRF-TOKEN": csrfToken,
          }
        });
        console.log(response.data);
       
      } catch (error) {
        console.error(error);
       
      }
    },
  });


  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
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
            Sign up
          </Typography>
          <Box
            component="form"
            noValidate
            onSubmit={formik.handleSubmit}
            sx={{ mt: 3 }}
          >
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  name="firstName"
                  required
                  fullWidth
                  id="firstName"
                  label="First Name"
                  value={formik.values.firstName}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  error={
                    formik.touched.firstName && Boolean(formik.errors.firstName)
                  }
                  helperText={
                    formik.touched.firstName && formik.errors.firstName
                  }
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  required
                  fullWidth
                  id="lastName"
                  label="Last Name"
                  name="lastName"
                  value={formik.values.lastName}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  error={
                    formik.touched.lastName && Boolean(formik.errors.lastName)
                  }
                  helperText={formik.touched.lastName && formik.errors.lastName}
                />
              </Grid>
            </Grid>
            <TextField
              fullWidth
              id="email"
              name="email"
              label="Email"
              value={formik.values.email}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={formik.touched.email && Boolean(formik.errors.email)}
              helperText={formik.touched.email && formik.errors.email}
              sx={{
                marginTop: "3%",
              }}
            />
            <TextField
              fullWidth
              id="password"
              name="password"
              label="Password"
              type="password"
              value={formik.values.password}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
              error={formik.touched.password && Boolean(formik.errors.password)}
              helperText={formik.touched.password && formik.errors.password}
              sx={{
                marginTop: "3%",
              }}
            />
            <Button
              color="primary"
              variant="contained"
              fullWidth
              type="submit"
              sx={{
                marginTop: "3%",
              }}
            >
              Sign Up
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item>
                <Link to="/" variant="body2">
                  Already have an account? Sign in
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default SignUp;
