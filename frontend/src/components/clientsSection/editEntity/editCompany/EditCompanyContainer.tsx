import { Alert, Box, Button } from "@mui/material";
import CompanyTextFields from "../../newEntity/CompanyTextFields";
import ListOfClients from "./listOfClients/ListOfClients";
import "../../../../style/EditCompanyContainer.css";
import ListOfEmails from "../listOfEmails/ListOfEmails";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import useValidateFormsValues from "../../hooks/useValidateFormsValues";
import { useEffect, useState } from "react";
import { Company } from "../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { ExpandedCompany } from "../../topPanel/listOfClientsCompany/helperfunctions/initializeData";
import useSendEntity from "../../hooks/useSendEntity";


const EditCompanyContainer = () => {
    const sendRequestStatus = useSelector((state: RootState) => state.clientView.apiRequestStatus);
    const [valueToSend, setValueToSend] = useState<Company>();
    const [isFormsValid, setIsFormsValid] = useState(false);
    const companyFieldsValues = useSelector((state: RootState) => state.clientView.companyTextFieldsValues);
    const clientsData = useSelector((state: RootState) => state.clientView.companyClientsData);
    const clickedCompany = useSelector((state: RootState) => state.clientView.clickedEntity);
    const { validateFields, errors } = useValidateFormsValues();
    const { sendData } = useSendEntity();

    const hasCompany = (data: any): data is ExpandedCompany => {
        return data && data.createdAt;
    };

    const handleSaveClick = () => {
        const isValid = validateFields(companyFieldsValues, {
            validateSelect: false,
            validateSurname: false,
        });
        const expandedCompany = hasCompany(clickedCompany);

        if(isValid && expandedCompany) {
            const {name, email, phone, address } = companyFieldsValues;
            const companyObject = {
              id: clickedCompany.id,
              name,
              email,
              phoneNumber: phone,
              address,
              createdAt: clickedCompany.createdAt,
              clients: clientsData
            }
            setIsFormsValid(true);
            setValueToSend(companyObject)
        }
    }

    useEffect(() => {
        if(isFormsValid && valueToSend){
            const endpoint = `http://localdev:8082/api/company/${clickedCompany?.id}`;
            sendData({url: endpoint, value: valueToSend});
        }

    }, [valueToSend, isFormsValid])

    return (
        <Box className="editContainer">
            {sendRequestStatus.status === "success" ? 
                <Alert severity="success">{sendRequestStatus.message}</Alert> : 
            sendRequestStatus.status === "error" ?
                <Alert severity="error">{sendRequestStatus.message}</Alert> : null
            }
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