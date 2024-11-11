import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import { TextField, Box , Typography } from "@mui/material";

const EmailPreviewTextFields = () => {
    const [toInputValue, setToInputValue] = useState("");
    const [ccInputValue, setCcInputValue] = useState("");
    const dataToDisplay = useSelector((state: RootState) => state.emailPreview.dataToDisplay);


    const convertTableDataToString = () => {
      console.log(dataToDisplay);
      const toEmails = dataToDisplay.participant
          .filter(participant => participant.status === 'TO')
          .map(participant => participant.email)
          .join(",");
  
      const ccEmails = dataToDisplay.participant
          .filter(participant => participant.status === 'CC')
          .map(participant => participant.email)
          .join(",");
  
      setToInputValue(toEmails);
      setCcInputValue(ccEmails);

      console.log(toInputValue, ccInputValue)
  };

    useEffect(() => {
       convertTableDataToString();
     }, [dataToDisplay]);
  

    return (
        <Box sx={{width: '100%'}}>
        <Box
        component="div"
        id="emailBox"
        style={{
          display: "flex",
          alignItems: "center",
          marginLeft: "3%",
          marginTop: "2%",
        }}
      >
        <Typography variant="subtitle1" style={{ marginRight: "1%" }}>
          To:
        </Typography>
        <TextField
          fullWidth
          variant="outlined"
          placeholder="Enter email address"
          style={{ width: "95%" }}
          value={toInputValue}
        />
      </Box>
      <Box
        component="div"
        style={{
          display: "flex",
          alignItems: "center",
          marginLeft: "3%",
          marginTop: "1%",
        }}
      >
        <Typography variant="subtitle1" style={{ marginRight: "1%" }}>
          Cc:
        </Typography>
        <TextField
          fullWidth
          variant="outlined"
          placeholder="Enter email address"
          style={{ width: "95%" }}
          value={ccInputValue}
        />
      </Box>
      </Box>
    );
}

export default EmailPreviewTextFields;