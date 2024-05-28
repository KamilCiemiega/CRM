import { useState, useEffect } from "react";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { useFormik } from "formik";
import * as yup from "yup";
import { Link, useNavigate } from "react-router-dom";
import {
  Alert,
  Button,
  Avatar,
  CssBaseline,
  TextField,
  Box,
  Typography,
  Container,
} from "@mui/material";
import axios from "axios";

const ForgotPassword = () => {
  const [successMessage, setSuccessMessage] = useState({
    openAlert: false,
    successMessage: "",
    redirect: false,
  });
  const navigate = useNavigate();

  useEffect(() => {
    let timer;
    if (successMessage.openAlert) {
      timer = setTimeout(() => {
        setSuccessMessage({
          openAlert: false,
          successMessage: "",
          redirect: true,
        });
      }, 3000);
    }
    if(successMessage.redirect){
        navigate('/');
    }
    return () => clearTimeout(timer);
  }, [successMessage.openAlert]);

  const formik = useFormik({
    initialValues: {
      email: "",
    },
    validationSchema: yup.object({
      email: yup.string().required("This field can't be empty"),
    }),
    onSubmit: async (values) => {
      try {
        const response = await axios.post(
          "http://localdev:8082/api/auth/password-reset-request",
          values
        );
        if (response.status === 200) {
          setSuccessMessage({
            openAlert: true,
            successMessage: "We send you link to reset password on your email",
            redirect: false,
          });
        }
      } catch (error) {}
    },
  });

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      {successMessage.openAlert && (
        <Alert
          severity="success"
          onClose={() => {
            setSuccessMessage({
              openAlert: false,
              successMessage: "",
              redirect: true,
            });
          }}
        >
          {successMessage.successMessage}
        </Alert>
      )}
      <Box
        component="form"
        noValidate
        onSubmit={formik.handleSubmit}
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
        <Typography component="h1" variant="h5" sx={{ mt: 1 }}>
          Forget your password ?
        </Typography>
        <Typography component="p" variant="body2">
          Please enter the emailyou use to sigIn.
        </Typography>
        <TextField
          name="email"
          required
          fullWidth
          id="email"
          label="Your email"
          sx={{ mt: 5 }}
          value={formik.values.email}
          onChange={formik.handleChange}
          onBlur={formik.handleBlur}
          error={formik.touched.email && Boolean(formik.errors.email)}
          helperText={formik.touched.email && formik.errors.email}
        />
        <Typography
          component="p"
          variant="body2"
          sx={{ mt: 8, textAlign: "center" }}
        >
          We will send you an email that will allow you to reset password.
        </Typography>
        <Button
          color="primary"
          variant="contained"
          fullWidth
          type="submit"
          sx={{ mt: 3 }}
        >
          Reset Password
        </Button>
        <Box sx={{ mt: 2 }}>
          <Link to="/" variant="body2">
            Back to SignIn
          </Link>
        </Box>
      </Box>
    </Container>
  );
};

export default ForgotPassword;
