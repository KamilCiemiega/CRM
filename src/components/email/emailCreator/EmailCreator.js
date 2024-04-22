import { useState } from "react";
import {
  Dialog,
  DialogTitle,
  Typography,
  Grid,
  TextField,
  Box,
} from "@mui/material";
import { useSelector } from "react-redux";
import { ThemeProvider } from "@mui/material/styles";
import { Close } from "@mui/icons-material";
import EmailCreatorTheme from "../../../themes/EmailCreatorTheme";

const EmailCreator = () => {
  const openDialog = useSelector((state) => state.emailCreator.openDialog);

  return (
    <ThemeProvider theme={EmailCreatorTheme}>
      <Dialog open={openDialog}>
        <DialogTitle>
          <Typography component="h8" variant="h8">
            New message
          </Typography>
          <Close />
        </DialogTitle>
        <Box component="form" noValidate>
          <Box
            component="div"
            style={{
              display: "flex",
              alignItems: "center",
              marginLeft: "2%",
              marginTop: "2%",
            }}
          >
            <Typography variant="subtitle1" style={{marginRight: "1%"}}>To:</Typography>
            <TextField
              fullWidth
              variant="outlined"
              placeholder="Enter email address"
              style={{width: "95%"}}
            />
          </Box>
          <Box
            component="div"
            style={{
              display: "flex",
              alignItems: "center",
              marginLeft: "2%",
              marginTop: "1%",
            }}
          >
            <Typography variant="subtitle1" style={{marginRight: "1%"}}>Cc:</Typography>
            <TextField
              fullWidth
              variant="outlined"
              placeholder="Enter email address"
              style={{width: "95%"}}
            />
          </Box>
          <Box
            component="div"
            style={{
              display: "flex",
              alignItems: "center",
              marginLeft: "2%",
              marginTop: "1%"
            }}
          >
            <Typography variant="subtitle1" style={{marginRight: "1%"}}>Sb:</Typography>
            <TextField
              fullWidth
              variant="outlined"
              style={{width: "95%"}}
            />
          </Box>
        </Box>
      </Dialog>
    </ThemeProvider>
  );
};

export default EmailCreator;
