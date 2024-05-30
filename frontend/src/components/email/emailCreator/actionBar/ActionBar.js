import { useState } from "react";
import { Button, Grid } from "@mui/material";
import {
  FormatColorText,
  AttachFile
} from "@mui/icons-material";


const ActionBar = () => {

  const [isEditTextBarOpen, setIsEditTextBarOpen] = useState(false);
  const [selectedFile, setSelectedFile] = useState(null);

  const handleFormatColorTextClick = () => {
    setIsEditTextBarOpen(!isEditTextBarOpen);
  };

  const handleFileInputChange = (event) => {
    setSelectedFile(URL.createObjectURL(event.target.files[0]));
  };

  const handleUploadFile = (event) => {
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
          accept=".pdf,.doc,.docx,.jpg,.png"
          onChange={handleFileInputChange}
        />
        <AttachFile sx={{ cursor: "pointer" }} onClick={() => document.getElementById("fileInput").click()} />
      </Grid>
      <img src={selectedFile} alt="Wybrany obraz" />
      {/* {isEditTextBarOpen && <EditTextBar />} */}
    </Grid>
  );
};

export default ActionBar;
