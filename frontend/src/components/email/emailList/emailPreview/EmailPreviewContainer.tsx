import { useCallback, useEffect, useReducer, useRef, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../../store";
import { TextField, Box, Typography, CircularProgress } from "@mui/material";
import EmailPreviewTextFields from "./EmailPreviewTextFields";
import TextEditor from "../../emailCreator/TextEditor";
import ActionBar from "../../emailCreator/actionBar/ActionBar"; 
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import '../../../../style/EmailPreviewContainer.css';
import { emailPreviewAction } from "../../../store/slices/emailSlices/emailPreview-slice";
import SelectNewClientsEmails from "./SelectNewClientsEmails";
import { useNavigate } from "react-router-dom";


const EmailPreviewContainer = () => {
const navigate = useNavigate();  
const [selectedValue, setSelectedValue] = useState("");  
const dataToDisplay = useSelector((state: RootState) => state.emailPreview.dataToDisplay);
const dispatch = useDispatch();
const isFirstRender = useRef(true);

const handleBackClik = useCallback(() => {
  dispatch(emailPreviewAction.setMessagePreview(false));
  dispatch(emailPreviewAction.setShouldShowPreview(false));
}, [dispatch]);

const handleValueChange = (value: string) => {
  setSelectedValue(value); 
  console.log("Nowa wartość selectedValue:", value);
};

   useEffect(() => {
    if (isFirstRender.current) {
      isFirstRender.current = false;
      return;
  }

  if(selectedValue) {
    const timer = setTimeout(() => {
      navigate("/clientView");
      setSelectedValue("");
  }, 2000);

      return () => clearTimeout(timer);
  }
    
    }, [selectedValue])

    return (
      <>
      {selectedValue.length > 0 &&
      <div className="backdropContainer">
        <CircularProgress size={"8rem"} />
      </div>}
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
          <SelectNewClientsEmails onValueChange={handleValueChange} />
          </Box>
        </Box>
        </>
    );
}

export default EmailPreviewContainer;