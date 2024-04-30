    import * as React from "react";
    import Avatar from "@mui/material/Avatar";
    import Button from "@mui/material/Button";
    import CssBaseline from "@mui/material/CssBaseline";
    import TextField from "@mui/material/TextField";
    import Box from "@mui/material/Box";
    import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
    import Typography from "@mui/material/Typography";
    import Container from "@mui/material/Container";
    import { useFormik } from "formik";
    import * as yup from "yup";
    import { Link } from "react-router-dom";
    import axios from "axios";

    const ForgotPassword = () => {
        const formik = useFormik({
            initialValues: {
                forgetPassword: ""
            },
            validationSchema: yup.object({
                forgetPassword: yup.string().required("This field can't be empty")
            }),
            onSubmit: (values) => {
            console.log(values)
            },
        });

    return (
        <Container component="main" maxWidth="xs">
        <CssBaseline />
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
            name="forgetPassword"
            required
            fullWidth
            id="forgetPassword"
            label="Your email"
            sx={{ mt: 5 }}
                value={formik.values.forgetPassword}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                error={
                formik.touched.forgetPassword && Boolean(formik.errors.forgetPassword)
                }
                helperText={
                formik.touched.forgetPassword && formik.errors.forgetPassword
                }
            />
            <Typography component="p" variant="body2" sx={{ mt: 8, textAlign: "center"}}>
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
            <Box sx={{ mt: 2}}>
            <Link to="/" variant="body2">
                    Back to SignIn
            </Link>
            </Box>
        </Box>
        </Container>
    );
    };

    export default ForgotPassword;
