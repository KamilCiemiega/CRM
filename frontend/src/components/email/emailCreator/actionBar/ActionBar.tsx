import { ChangeEvent, useState } from "react";
import { Button, Grid } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { FormatColorText, AttachFile } from "@mui/icons-material";
import EditTextBar from "./EditTextBar";
import { findUserOrClientEmailAction } from "../../../store/slices/emailSlices/findUserOrClientEmail-slice";
import {Badge} from "@mui/material";
import { RootState } from "../../../store";

const ActionBar = () => {
  const [isEditTextBarOpen, setIsEditTextBarOpen] = useState(false);
  const [selectedFile, setSelectedFile] = useState<string>('');
  const [uploadFileCounter, setUploadFileCounter] = useState<number>(0);
  const toInputValue = useSelector(
    (state: RootState) => state.findUserOrClientEmail.toInputValue
  );
  const ccInputValue = useSelector(
    (state: RootState) => state.findUserOrClientEmail.ccInputValue
  );

  const dispatch = useDispatch();
  const handleFormatColorTextClick = () => {
    setIsEditTextBarOpen(!isEditTextBarOpen);
  };

  const handleFileInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
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

  const handleSendData = () => {
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
          onClick={() => document.getElementById("fileInput")!.click()}
        />
        </Badge>
      </Grid>
      {isEditTextBarOpen && <EditTextBar />}
    </Grid>
  );
};

export default ActionBar;
