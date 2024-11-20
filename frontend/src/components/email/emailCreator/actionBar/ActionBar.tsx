import { ChangeEvent, useEffect, useState } from "react";
import { Grid } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { findUserOrClientEmailAction } from "../../../store/slices/emailSlices/findUserOrClientEmail-slice";
import { RootState } from "../../../store";
import ActionBarContent from "./ActionBarContent";
import { SendNewMessageRequest } from "./SendNewMessageRequest";
import { useNavigate } from "react-router-dom";

const ActionBar = () => {
  const navigate = useNavigate();
  const [fileNames, setFileNames] = useState<String[]>([]);
  const [uploadFileCounter, setUploadFileCounter] = useState<number | any>(0);
  const toInputValue = useSelector((state: RootState) => state.findUserOrClientEmail.toInputValue);
  const ccInputValue = useSelector((state: RootState) => state.findUserOrClientEmail.ccInputValue);
  const subtitle = useSelector((state: RootState) => state.sendEmail.subtitleValue);
  const messageRoles = useSelector((state: RootState) => state.sendEmail.messageRoles);
  const textEditorValue = useSelector((state: RootState) => state.sendEmail.editorContent);
  const fieldsErrorState = useSelector((state: RootState) => state.findUserOrClientEmail.fieldErrorState);
  const reduxAttachmentsValue = useSelector((state : RootState) => state.emailPreview.dataToDisplay.attachmentsNumber);
  const dispatch = useDispatch();

  useEffect(() => {
    setUploadFileCounter(reduxAttachmentsValue)
  }, [reduxAttachmentsValue]);
  
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
            "id": 8
          }
        ],
        messageRoles: messageRoles,
        attachments: fileNames.map((name) => ({ filePath: name }))
      }
      await SendNewMessageRequest(payload, navigate, dispatch);
    }
  };


  return (
    <Grid container spacing={2} alignItems="center" sx={{ ml: 5, mt: 2, width: "100%"}}>
      <ActionBarContent
        handleSendData={handleSendData}
        onFileChange={onFileChange}
        uploadFileCounter={uploadFileCounter}
      />
    </Grid>
  )};

export default ActionBar;
