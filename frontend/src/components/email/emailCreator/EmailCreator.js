import { useDispatch, useSelector } from 'react-redux';
import React from 'react';
import { editTextAction } from "../../store/editText-slice";
import { Dialog, DialogTitle, Typography, TextField, Box } from "@mui/material";
import { ThemeProvider } from "@mui/material/styles";
import { Close } from "@mui/icons-material";
import EmailCreatorTheme from "../../../themes/EmailCreatorTheme";
import ActionBar from "./actionBar/ActionBar";
import { Editor } from "draft-js";
import "../../../style/EmailCreator.css";
import { emailCreatorAction } from '../../store/emailCreator-slice';
import FindUserOrClientEmail from './FindUserOrClientEmail';
import { findUserOrClientEmailAction } from '../../store/findUserOrClientEmail-slice';

const EmailCreator = () => {
  const openDialog = useSelector((state) => state.emailCreator.openDialog);

  const dispatch = useDispatch();
  const editorState = useSelector(state => state.editText.editorState);
  const openToSearchBox = useSelector(state => state.findUserOrClientEmail.openToSearchBox);
  const openCcSearchBox = useSelector(state => state.findUserOrClientEmail.openCcSearchBox);
  const toInputValue = useSelector(state => state.findUserOrClientEmail.toInputValue);
  const ccInputValue = useSelector(state => state.findUserOrClientEmail.ccInputValue);



  const handleEditorChange = (newEditorState) => {
    dispatch(editTextAction.setEditorState(newEditorState));
  };

  const handleCloseDialog = () => {
    dispatch(emailCreatorAction.setOpenDialog(false))
  }

  const handleInputChange = (e, field) => {
    const value = e.target.value;
    if (field === "to") {
        dispatch(findUserOrClientEmailAction.setToInputValue(value));
    } else if (field === "cc") {
        dispatch(findUserOrClientEmailAction.setCcInputValue(value));
    }
};

  return (
    <ThemeProvider theme={EmailCreatorTheme}>
      <Dialog open={openDialog}>
        <DialogTitle>
          <Typography component="h6" variant="h6">
            New message
          </Typography>
          <Close onClick={handleCloseDialog} sx={{cursor: "pointer"}}/>
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
              style={{ width: "95%" }}
              onChange={e => handleInputChange(e, "to")}
              value={toInputValue}
            />
            {openToSearchBox && <FindUserOrClientEmail/>}
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
              style={{ width: "95%" }}
              onChange={e => handleInputChange(e, "cc")}
              value={ccInputValue}
            />
            {openCcSearchBox && <FindUserOrClientEmail/>}
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
            <TextField fullWidth variant="outlined" style={{ width: "95%" }} />
          </Box>
          <Editor
            editorState={editorState}
            onChange={handleEditorChange}
            className="editorStyle"
          />
          <ActionBar />
        </Box>
      </Dialog>
    </ThemeProvider>
  );
};

export default EmailCreator;
