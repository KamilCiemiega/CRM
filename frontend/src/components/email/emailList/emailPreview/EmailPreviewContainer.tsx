import { useState, useEffect } from "react";
import { useSelector, UseSelector } from "react-redux";
import { RootState } from "../../../store";
import { Paper, TextField, Box, Typography } from "@mui/material";
import EmailPreviewTextFields from "./EmailPreviewTextFields";
import TextEditor from "../../emailCreator/TextEditor";
import ActionBar from "../../emailCreator/actionBar/ActionBar"; 

const EmailPreviewContainer = () => {
const dataToDisplay = useSelector((state: RootState) => state.emailPreview.dataToDisplay);

    return (
        <Box component="div" sx={{width: '100%', height: '100vh'}}>
            <EmailPreviewTextFields />
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
            />
          </Box>
          <TextEditor />
          <ActionBar />
        </Box>
    );
}

export default EmailPreviewContainer;