import { Box, Button, Paper } from "@mui/material";
import CompanyTextFields from "../../newEntity/CompanyTextFields";
import ListOfClients from "./listOfClients/ListOfClients";
import "../../../../style/EditCompanyContainer.css";
import ListOfEmails from "../listOfEmails/ListOfEmails";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import useValidateFormsValues from "../../hooks/useValidateFormsValues";

const EditCompanyContainer = () => {
    const companyFieldsValues = useSelector((state: RootState) => state.clientView.companyTextFieldsValues);
    const { validateFields, errors } = useValidateFormsValues();

    const handleSaveClick = () => {
        const isValid = validateFields(companyFieldsValues, {
            validateSelect: false,
            validateSurname: false,
        });
    }

    return (
        <Box className="editContainer">
            <Box className="clientsContentContainer">
                <Box className="companyTextFields">
                <CompanyTextFields 
                        validateFields={validateFields} 
                        errors={errors} 
                    />
                </Box>
                <ListOfClients />
            </Box>
            <Box className="emailsContainer">
                <ListOfEmails />
            </Box>
            <Box className="buttonContainer">
            <Button variant="contained" onClick={handleSaveClick}>SAVE</Button>
            </Box>
        </Box>
    );

}

export default EditCompanyContainer;