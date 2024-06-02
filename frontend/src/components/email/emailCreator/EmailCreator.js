import { useDispatch, useSelector } from "react-redux";
import React, { useEffect, useState } from "react";
import { Dialog, DialogTitle, Typography, TextField, Box } from "@mui/material";
import { ThemeProvider } from "@mui/material/styles";
import { Close } from "@mui/icons-material";
import EmailCreatorTheme from "../../../themes/EmailCreatorTheme";
import ActionBar from "./actionBar/ActionBar";
import { emailCreatorAction } from "../../store/emailCreator-slice";
import FindUserOrClientEmail from "./FindUserOrClientEmail";
import { findUserOrClientEmailAction } from "../../store/findUserOrClientEmail-slice";
import TextEditor from "./TextEditor";

const EmailCreator = () => {
  const openDialog = useSelector((state) => state.emailCreator.openDialog);

  const dispatch = useDispatch();
  const openToSearchBox = useSelector(
    (state) => state.findUserOrClientEmail.openToSearchBox
  );
  const openCcSearchBox = useSelector(
    (state) => state.findUserOrClientEmail.openCcSearchBox
  );
  const toInputValue = useSelector(
    (state) => state.findUserOrClientEmail.toInputValue
  );
  const ccInputValue = useSelector(
    (state) => state.findUserOrClientEmail.ccInputValue
  );
  const [error, setError] = useState(false);

  const handleCloseDialog = () => {
    dispatch(emailCreatorAction.setOpenDialog(false));
  };
  const handleSendSubtitleValue = (e) => {
    const value = e.target.value
    if(value){
      dispatch(findUserOrClientEmailAction.setSubtitleValue(value));
    }
  };
  const fieldErrorState = useSelector(
    (state) => state.findUserOrClientEmail.fieldErrorState
  );

  const handleInputChange = (e, field) => {
    const value = e.target.value;
    if (field === "to") {
      dispatch(findUserOrClientEmailAction.setToInputValue(value));
      if (value) {
        dispatch(findUserOrClientEmailAction.setFieldErrorState({ to: false }));
      }
    } else if (field === "cc") {
      dispatch(findUserOrClientEmailAction.setCcInputValue(value));
      if (value) {
        dispatch(findUserOrClientEmailAction.setFieldErrorState({ cc: false }));
      }
    }
  };

  useEffect(() => {
    if (fieldErrorState.to && fieldErrorState.cc) {
      setError(true);
    } else {
      setError(false);
    }
  }, [fieldErrorState]);

  return (
    <ThemeProvider theme={EmailCreatorTheme}>
      <Dialog open={openDialog}>
        <DialogTitle>
          New message
          <Close onClick={handleCloseDialog} sx={{ cursor: "pointer" }} />
        </DialogTitle>
        <Box component="form" noValidate style={{ height: "100%" }}>
          <Box
            component="div"
            id="emailBox"
            style={{
              display: "flex",
              alignItems: "center",
              marginLeft: "2%",
              marginTop: "2%",
            }}
          >
            <Typography variant="subtitle1" style={{ marginRight: "1%" }}>
              To:
            </Typography>
            <TextField
              fullWidth
              variant="outlined"
              placeholder="Enter email address"
              error={error}
              helperText={error && "One of the field must be fill"}
              style={{ width: "95%" }}
              onChange={(e) => handleInputChange(e, "to")}
              value={toInputValue}
            />
            {openToSearchBox && <FindUserOrClientEmail />}
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
            <Typography variant="subtitle1" style={{ marginRight: "1%" }}>
              Cc:
            </Typography>
            <TextField
              fullWidth
              variant="outlined"
              placeholder="Enter email address"
              error={error}
              helperText={error && "One of the field must be fill"}
              style={{ width: "95%" }}
              onChange={(e) => handleInputChange(e, "cc")}
              onClick={() => console.log("test")}
              value={ccInputValue}
            />
            {openCcSearchBox && <FindUserOrClientEmail />}
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
            <Typography variant="subtitle1" style={{ marginRight: "1%" }}>
              Sb:
            </Typography>
            <TextField
              fullWidth
              variant="outlined"
              style={{ width: "95%" }}
              onChange={(e) => handleSendSubtitleValue(e)}
            />
          </Box>
          <TextEditor />
          <ActionBar />
        </Box>
      </Dialog>
    </ThemeProvider>
  );
};

export default EmailCreator;
