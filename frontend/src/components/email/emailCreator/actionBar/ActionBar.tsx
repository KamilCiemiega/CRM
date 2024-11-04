import { ChangeEvent, useState } from "react";
import { Button, Grid } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { FormatColorText, AttachFile } from "@mui/icons-material";
import EditTextBar from "./EditTextBar";
import { findUserOrClientEmailAction } from "../../../store/slices/emailSlices/findUserOrClientEmail-slice";
import {Badge} from "@mui/material";
import { RootState } from "../../../store";
import axios from "axios";

const ActionBar = () => {
  const [isEditTextBarOpen, setIsEditTextBarOpen] = useState(false);
  const [selectedFile, setSelectedFile] = useState<File[]>([]);
  const [uploadFileCounter, setUploadFileCounter] = useState<number>(0);
  const toInputValue = useSelector((state: RootState) => state.findUserOrClientEmail.toInputValue);
  const ccInputValue = useSelector((state: RootState) => state.findUserOrClientEmail.ccInputValue);
  const subtitle = useSelector((state: RootState) => state.findUserOrClientEmail.subtitleValue);
  

  const dispatch = useDispatch();
  const handleFormatColorTextClick = () => {
    setIsEditTextBarOpen(!isEditTextBarOpen);
  };

  const onFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      const filesArray = Array.from(event.target.files);
      setSelectedFile(filesArray);
      setUploadFileCounter(filesArray.length);
      
      const formData = new FormData();
      filesArray.forEach((file) => {
        formData.append('files', file);
      });
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

  const handleSendData = async () => {
    handleFieldValidation();
    if(toInputValue && ccInputValue){
      const sentDate = new Date().getTime();

      const response = await axios.post("")
    }
    
    
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
          multiple
          id="fileInput"
          style={{ display: "none" }}
          accept=".pdf,.doc,.docx,.jpg,.png"
          onChange={onFileChange}
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
  )};

export default ActionBar;
