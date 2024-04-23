import { useState } from "react";
import { Button, Grid } from "@mui/material";
import {
  FormatColorText,
  AttachFile,
  AddReaction,
  AddPhotoAlternate,
} from "@mui/icons-material";
import EditTextBar from "./EditTextBar";


const ActionBar = () => {

  const [isEditTextBarOpen, setIsEditTextBarOpen] = useState(false);
  const [selectedFile, setSelectedFile] = useState(null);

  const handleFormatColorTextClick = () => {
    setIsEditTextBarOpen(!isEditTextBarOpen);
    console.log(isEditTextBarOpen);
  };

  const handleFileInputChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleUploadFile = () => {
    console.log("Wgrano plik:", selectedFile);
  };

  return (

    <Grid container spacing={2} alignItems="center" sx={{ml: 5, mt: 2, width: "80%"}}>
      <Grid item>
        <Button variant="contained" sx={{ mr: 1 }} onClick={handleUploadFile}>Send</Button>
      </Grid>
      <Grid item>
        <FormatColorText sx={{cursor: "pointer"}} onClick={handleFormatColorTextClick}/>
      </Grid>
      <Grid item>
      <input
          type="file"
          id="fileInput"
          style={{ display: "none" }}
          accept=".pdf,.doc,.docx"
          onChange={handleFileInputChange}
        />
        <AttachFile sx={{ cursor: "pointer" }} onClick={() => document.getElementById("fileInput").click()} />
      </Grid>
      <Grid item>
        <AddReaction sx={{cursor: "pointer"}}/>
      </Grid>
      <Grid item>
        <AddPhotoAlternate sx={{cursor: "pointer"}}/>
      </Grid>
      {isEditTextBarOpen && <EditTextBar />}
    </Grid>
  );
};

export default ActionBar;
