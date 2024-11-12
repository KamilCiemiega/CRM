import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../../store";
import { TextField, Box, Typography } from "@mui/material";
import EmailPreviewTextFields from "./EmailPreviewTextFields";
import TextEditor from "../../emailCreator/TextEditor";
import ActionBar from "../../emailCreator/actionBar/ActionBar"; 
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import '../../../../style/EmailPreviewContainer.css';
import { emailPreviewAction } from "../../../store/slices/emailSlices/emailPreview-slice";



const EmailPreviewContainer = () => {
const dataToDisplay = useSelector((state: RootState) => state.emailPreview.dataToDisplay);
const dispatch = useDispatch();

const handleBackClik = () => {
    dispatch(emailPreviewAction.setMessagePreview(false))
    dispatch(emailPreviewAction.setShouldShowPreview(false));
}

    return (
        <Box component="div" sx={{width: '100%', height: '100vh'}}>
            <Box className="msTitleBox" onClick={handleBackClik}> 
                <Typography variant="h6" >Message Preview</Typography>
                <Box className="backButton">
                    <ArrowBackIcon/>
                    <Typography variant="body2" sx={{ml:'0.5%'}}>Back</Typography>
                </Box>
            </Box>
            <EmailPreviewTextFields />
            <Box
            component="div"
            sx={{
              display: "flex",
              alignItems: "center",
              marginLeft: "3%",
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
              value={dataToDisplay.subtitle}
            />
          </Box>
          <TextEditor />
          <ActionBar/>
        </Box>
    );
}

export default EmailPreviewContainer;