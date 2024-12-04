import { useState, useEffect, useCallback } from "react";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../../store";
import { TextField, Box, Typography, Select, SelectChangeEvent, MenuItem, OutlinedInput, Chip, InputLabel, FormControl } from "@mui/material";
import EmailPreviewTextFields from "./EmailPreviewTextFields";
import TextEditor from "../../emailCreator/TextEditor";
import ActionBar from "../../emailCreator/actionBar/ActionBar"; 
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import '../../../../style/EmailPreviewContainer.css';
import { emailPreviewAction } from "../../../store/slices/emailSlices/emailPreview-slice";



const EmailPreviewContainer = () => {
const [newClientsEmails, setNewClientsEmails] = useState<string[]>([])
const [ selectedValue, setSelectedValue] = useState("");
const dataToDisplay = useSelector((state: RootState) => state.emailPreview.dataToDisplay);
const dispatch = useDispatch();

const handleBackClik = useCallback(() => {
  dispatch(emailPreviewAction.setMessagePreview(false));
  dispatch(emailPreviewAction.setShouldShowPreview(false));
}, [dispatch]);

const getNewClientEmail = () => {
  const newEmails = dataToDisplay.participant
    .filter(e => typeof e.newClientEmail === 'string') 
    .map(e => e.newClientEmail as string);

  setNewClientsEmails(newEmails); 
};

const handleSelectChange = (event: SelectChangeEvent) => {
  setSelectedValue(event.target.value);
}


useEffect(() => {
  getNewClientEmail();
}, [dataToDisplay])

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
          <Box sx={{display: 'flex', width: "98.5%", mt: "2%"}}>
          <ActionBar/>
          <FormControl
            sx={{ mt: "10px", width: "300px" }}
            variant="outlined"
            >
          <InputLabel id="select-email-label">Add new emails</InputLabel>
            <Select
              labelId="select-email-label"
              value={selectedValue}
              onChange={handleSelectChange}
              input={<OutlinedInput label="Select Email" />}
              renderValue={(selected) => (
                <Chip
                  label={selected}
                  sx={{
                    backgroundColor: "#e0e0e0",
                    fontSize: "0.9rem",
                    padding: "0.2rem 0.5rem",
                  }}
                />
              )}
            >
              {newClientsEmails.map((email, index) => (
                <MenuItem key={index} value={email}>
                  {email}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          </Box>
        </Box>
    );
}

export default EmailPreviewContainer;