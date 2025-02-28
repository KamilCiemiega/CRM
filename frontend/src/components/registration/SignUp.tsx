import { useState, useEffect } from "react";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useFormik } from "formik";
import * as yup from "yup";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { Alert, Button, Avatar, CssBaseline,TextField,Grid,Box,Typography,Container } from "@mui/material";

const defaultTheme = createTheme();

const SignUp = () => {
  const [error, setError] = useState({errMessage: "", openAlert: false}); 
  const navigate = useNavigate(); 

  useEffect(() => {
    let timer: number;
    if (error.openAlert) {
      timer = window.setTimeout(() => {
        setError({errMessage: "", openAlert: false});
      }, 3000);
    }
    return () => clearTimeout(timer);
  }, [error.openAlert]);

  const formik = useFormik({
    initialValues: {
      firstName: "",
      lastName: "",
      password: "",
      email: "",
      roleDTO: {
         "id": 1
      }
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
        const payload = {
          ...values,
          roleDTO: values.roleDTO && values.roleDTO.id ? { id: values.roleDTO.id } : { id: 1 }
        };

        const response = await axios.post("http://localdev:8082/api/users", payload);
        
        if(response.status === 201){
            navigate('/');
        }
        
      } catch (error: unknown) {
        let errorMessage = "An unknown error occurred";
        if (axios.isAxiosError(error)) {
          errorMessage = error.message;
        } else if (error instanceof Error) {
          errorMessage = error.message;
        }
        setError({ errMessage: errorMessage, openAlert: true });
      } 
    },
  });

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        {error.openAlert && (
          <Alert severity="warning" onClose={() => {setError({errMessage: "", openAlert: false})}}>
            {error.errMessage}
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
                mt: 3,
              }}
            >
              Sign Up
            </Button>
            <Grid container justifyContent="flex-end">
              <Grid item sx={{mt: 2}}>
                <Link to="/" >
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
