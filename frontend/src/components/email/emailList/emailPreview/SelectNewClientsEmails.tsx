import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../store";
import { Chip, FormControl, InputLabel, MenuItem, OutlinedInput, Select, SelectChangeEvent } from "@mui/material";
import { clientViewAction } from "../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { useNavigate } from "react-router-dom";

interface SelectNewClientsEmailsProps {
    onValueChange: (value: string) => void;
}

const SelectNewClientsEmails: React.FC<SelectNewClientsEmailsProps> = ({ onValueChange }) => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [newClientsEmails, setNewClientsEmails] = useState<string[]>([])
    const [ selectedValue, setSelectedValue] = useState("");
    const dataToDisplay = useSelector((state: RootState) => state.emailPreview.dataToDisplay);
    const clickedMessageData = useSelector((state: RootState) => state.emailPreview.clickedMessage);

    const getNewClientEmail = () => {
        const newEmails = dataToDisplay.participant
          .filter(e => typeof e.newClientEmail === 'string') 
          .map(e => e.newClientEmail as string);
      
        setNewClientsEmails(newEmails); 
    };
      
      const handleSelectChange = (event: SelectChangeEvent) => {
        const value = event.target.value;
        setSelectedValue(value);
        onValueChange(value);
        const addNewClientData = { messageId: clickedMessageData.id, email: value };
    
        dispatch(clientViewAction.setOpenNewEntityDialog(true));
        dispatch(clientViewAction.setSelectedNewClient(addNewClientData));
      };

    useEffect(() => {
        getNewClientEmail();
    }, [dataToDisplay])  

    return (
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
    )
}

export default SelectNewClientsEmails;