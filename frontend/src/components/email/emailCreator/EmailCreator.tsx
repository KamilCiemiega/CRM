import { useDispatch, useSelector } from "react-redux";
import React, { useEffect, useState } from "react";
import { Dialog, DialogTitle, Typography, TextField, Box, Alert } from "@mui/material";
import { ThemeProvider } from "@mui/material/styles";
import { Close } from "@mui/icons-material";
import EmailCreatorTheme from "../../../themes/EmailCreatorTheme";
import ActionBar from "./actionBar/ActionBar";
import { emailCreatorAction } from "../../store/slices/emailSlices/emailCreator-slice";
import FindUserOrClientEmail from "./findUserOrClientEmail/FindUserOrClientEmail";
import { findUserOrClientEmailAction } from "../../store/slices/emailSlices/findUserOrClientEmail-slice";
import TextEditor from "./TextEditor";
import { AppDispatch, RootState } from "../../store";
import { sendEmailAction } from "../../store/slices/emailSlices/sendEmail-slice";
import useSubFoldersEmailActions from "../../../hooks/useSubFoldersEmailActions";
import { emailListAction } from "../../store/slices/emailSlices/emailList-slice";

const EmailCreator = () => {
  const [error, setError] = useState(false);
  const dispatch: AppDispatch = useDispatch();
  const openDialog = useSelector((state: RootState) => state.emailCreator.openDialog);
  const openToSearchBox = useSelector((state:RootState) => state.findUserOrClientEmail.openToSearchBox);
  const openCcSearchBox = useSelector((state:RootState) => state.findUserOrClientEmail.openCcSearchBox);
  const toInputValue = useSelector((state:RootState) => state.findUserOrClientEmail.toInputValue);
  const ccInputValue = useSelector((state:RootState) => state.findUserOrClientEmail.ccInputValue);
  const theSameUserInInput = useSelector((state:RootState) => state.findUserOrClientEmail.theSameUserInInput)
  const sendMessageStatus = useSelector((state:RootState) => state.sendEmail.sendMessageStatus);
  const subtitleValue = useSelector((state: RootState) => state.sendEmail.subtitleValue);
  useSubFoldersEmailActions();

  const handleCloseDialog = () => {
    dispatch(emailCreatorAction.setOpenDialog(false));
    dispatch(emailListAction.setSecondaryTabNumber(null));
    dispatch(emailListAction.setResetCheckboxes([]));
    dispatch(findUserOrClientEmailAction.batchUpdate({toInputValue:"", ccInputValue:""}));
  };
  
  const handleSendSubtitleValue = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const value = e.target.value
    if(value){
      dispatch(sendEmailAction.setSubtitleValue(value));
    }
  };
  const fieldErrorState = useSelector(
    (state:RootState) => state.findUserOrClientEmail.fieldErrorState
  );

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, field: string) => {
    const value = e.target.value;

    if (field === "to") {
      dispatch(findUserOrClientEmailAction.setToInputValue({value, valuType: ''}));
      if (value) {
        dispatch(findUserOrClientEmailAction.setFieldErrorState({ to: false }));
      }
    } else if (field === "cc") {
      dispatch(findUserOrClientEmailAction.setCcInputValue({value, valuType: ''}));
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

  useEffect(() => {
    const timer = setTimeout(() => {
      dispatch(findUserOrClientEmailAction.setTheSameUserAlert(false));
    }, 3000);

    return () => clearTimeout(timer);
  }, [theSameUserInInput]);

  useEffect(() => {
    const timer = setTimeout(() => {
      if(sendMessageStatus.status === "success"){
          handleCloseDialog();
      }
      dispatch(sendEmailAction.setSendMessageStatus({
        status: "",
        message: "",
        openAlert: false
      }))
    }, 2000);

    return () => clearTimeout(timer);
  }, [sendMessageStatus, dispatch]);

  return (
    <ThemeProvider theme={EmailCreatorTheme}>
      <Dialog open={openDialog}>
        {theSameUserInInput && (
          <Alert severity="warning">
          This user is already added
        </Alert>
        )}
        {sendMessageStatus.openAlert && (
         <Alert
         severity={(sendMessageStatus.status || "info") as "error" | "warning" | "info" | "success"}
         onClose={() => {
           dispatch(sendEmailAction.setSendMessageStatus({
             status: "",
             message: "",
             openAlert: false
           }));
         }}
       >
         {sendMessageStatus.message}
       </Alert>
       
        )}
        <DialogTitle>
          New message
          <Close onClick={handleCloseDialog} sx={{ cursor: "pointer" }} />
        </DialogTitle>
        <Box component="form" noValidate style={{ height: "100%",overflow: "hidden" }}>
          <Box
            component="div"
            id="emailBox"
            style={{
              display: "flex",
              alignItems: "center",
              marginLeft: "2%",
              marginTop: "2%"
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
              onChange={e => handleInputChange(e, "to")}
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
              onChange={e => handleInputChange(e, "cc")}
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
              value={subtitleValue}
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


