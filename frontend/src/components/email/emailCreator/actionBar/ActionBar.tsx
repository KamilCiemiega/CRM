import { ChangeEvent, useEffect, useState } from "react";
import { Button, Grid } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { FormatColorText, AttachFile } from "@mui/icons-material";
import EditTextBar from "./EditTextBar";
import { findUserOrClientEmailAction } from "../../../store/slices/emailSlices/findUserOrClientEmail-slice";
import {Badge} from "@mui/material";
import { RootState } from "../../../store";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { sendEmailAction } from "../../../store/slices/emailSlices/sendEmail-slice";

const ActionBar: React.FC<{attachmentsNumber?: number}> = ({attachmentsNumber}) => {
  const navigate = useNavigate(); 
  const [isEditTextBarOpen, setIsEditTextBarOpen] = useState(false);
  const [fileNames, setFileNames] = useState<String[]>([]);
  const [uploadFileCounter, setUploadFileCounter] = useState<number | any>(0);
  const toInputValue = useSelector((state: RootState) => state.findUserOrClientEmail.toInputValue);
  const ccInputValue = useSelector((state: RootState) => state.findUserOrClientEmail.ccInputValue);
  const subtitle = useSelector((state: RootState) => state.sendEmail.subtitleValue);
  const messageRoles = useSelector((state: RootState) => state.sendEmail.messageRoles);
  const textEditorValue = useSelector((state: RootState) => state.sendEmail.editorContent);
  const fieldsErrorState = useSelector((state: RootState) => state.findUserOrClientEmail.fieldErrorState);
  const dispatch = useDispatch();

  useEffect(() => {
    setUploadFileCounter(attachmentsNumber)
  }, [attachmentsNumber]);
  
  const onFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      const fileNames = Array.from(event.target.files).map((file) => file.name);
      setFileNames(fileNames);
      setUploadFileCounter(fileNames.length);
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

  const handleApiRequest = async (payload: {}) => {
    try{
      const response = await axios.post("http://localdev:8082/api/messages", payload);
      if(response.status === 201){
        navigate('/emailView');
        dispatch(sendEmailAction.setSendMessageStatus({
          status: "success",
          message: "Message send success",
          openAlert: true
      }));
      }

    }catch (error: unknown) {
      let errorMessage = "An unknown error occurred";
      if (axios.isAxiosError(error)) {
        errorMessage = error.message;
      } else if (error instanceof Error) {
        errorMessage = error.message;
      }
      dispatch(sendEmailAction.setSendMessageStatus({
        status: "error",
        message: errorMessage,
        openAlert: true
    }));
      
    } 
  }

  const handleSendData = async () => {
    handleFieldValidation();
    if(!fieldsErrorState.to && !fieldsErrorState.cc){
      const sentDate = new Date().getTime();
      const payload = {
        subject: subtitle,
        body: JSON.stringify(textEditorValue),
        sentDate: sentDate,
        status: "SENT",
        messageFolders: [
          {
            "id": 2
          }
        ],
        messageRoles: messageRoles,
        attachments: fileNames.map((name) => ({ filePath: name }))
      }
      await handleApiRequest(payload);
    }
  };


  return (
    <Grid
      container
      spacing={2}
      alignItems="center"
      sx={{ ml: 5, mt: 2, width: "100%"}}
    >
      <Grid item>
        <Button variant="contained" sx={{ mr: 1 }} onClick={handleSendData}>
          Send
        </Button>
      </Grid>
      <EditTextBar />
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
    </Grid>
  )};

export default ActionBar;
