import { useState } from "react";
import { Button, Grid } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { FormatColorText, AttachFile } from "@mui/icons-material";
import EditTextBar from "./EditTextBar";
import { findUserOrClientEmailAction } from "../../../store/findUserOrClientEmail-slice";
import {Badge} from "@mui/material";

const ActionBar = () => {
  const [isEditTextBarOpen, setIsEditTextBarOpen] = useState(false);
  const [selectedFile, setSelectedFile] = useState(null);
  const [uploadFileCounter, setUploadFileCounter] = useState(null);
  const toInputValue = useSelector(
    (state) => state.findUserOrClientEmail.toInputValue
  );
  const ccInputValue = useSelector(
    (state) => state.findUserOrClientEmail.ccInputValue
  );

  const dispatch = useDispatch();
  const handleFormatColorTextClick = () => {
    setIsEditTextBarOpen(!isEditTextBarOpen);
  };

  const handleFileInputChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const url = URL.createObjectURL(file);
      setSelectedFile(url);
      console.log("File selected with URL:", url);
      setUploadFileCounter(1);
    }
  };

  const handleFieldValidation = () => {
    if (!toInputValue && !ccInputValue) {
      dispatch(
        findUserOrClientEmailAction.setFieldErrorState({ to: true, cc: true })
      );
    } else {
      dispatch(
        findUserOrClientEmailAction.setFieldErrorState({ cc: false, to: false })
      );
    }
  };

  const handleSendData = (e) => {
    handleFieldValidation();


    console.log("Wgrano plik:", selectedFile);
  };

  return (
    <Grid
      container
      spacing={2}
      alignItems="center"
      sx={{ ml: 5, mt: 2, width: "80%" }}
    >
      <Grid item>
        <Button variant="contained" sx={{ mr: 1 }} onClick={handleSendData}>
          Send
        </Button>
      </Grid>
      <Grid item>
        <FormatColorText
          sx={{ cursor: "pointer" }}
          onClick={handleFormatColorTextClick}
        />
      </Grid>
      <Grid item>
        <input
          type="file"
          id="fileInput"
          style={{ display: "none" }}
          accept=".pdf,.doc,.docx,.jpg,.png"
          onChange={handleFileInputChange}
        />
        <Badge badgeContent={uploadFileCounter} color="success">
        <AttachFile
          sx={{ cursor: "pointer" }}
          onClick={() => document.getElementById("fileInput").click()}
        />
        </Badge>
      </Grid>
      {isEditTextBarOpen && <EditTextBar />}
    </Grid>
  );
};

export default ActionBar;
