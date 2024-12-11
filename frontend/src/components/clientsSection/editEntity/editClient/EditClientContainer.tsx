import { Alert, Box, Button } from "@mui/material";
import '../../../../style/EditClientContainer.css'
import ClientTextFields from "../../newEntity/ClientTextFields";
import { useSelector } from "react-redux";
import { RootState } from "../../../store";
import useValidateFormsValues from "../../hooks/useValidateFormsValues";
import ListOfEmails from "../listOfEmails/ListOfEmails";
import { ExpandedClient } from "../../topPanel/listOfClientsCompany/helperfunctions/initializeData";
import { useEffect, useState } from "react";
import { Client, Company } from "../../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import useSendEntity from "../../hooks/useSendEntity";

const EditClientContainer = () => {
    const [valueToSend, setValueToSend] = useState<Client>();
    const [isFormsValid, setIsFormsValid] = useState(false);
    const clientTextsFieldsValues = useSelector((state: RootState) => state.clientView.clientTextFieldsValues);
    const clickedEntity = useSelector((state: RootState) => state.clientView.clickedEntity);
    const sendRequestStatus = useSelector((state: RootState) => state.clientView.apiRequestStatus);
    const { validateFields, errors } = useValidateFormsValues();
    const { sendData } = useSendEntity();

    const isExpandedClient = (data: any): data is ExpandedClient => {
        return data && "surname" in data;
    };

    const isCompanyArray = (data: any): data is Company[] => {
        return Array.isArray(data) && data.every(item => item && typeof item.id === "number" && typeof item.name === "string");
    };

    const handleSaveClick = () => {
        const isValid = validateFields(clientTextsFieldsValues, {
            validateSelect: true,
            validateSurname: true,
        });

        const isClient = isExpandedClient(clickedEntity);

        if(isValid && isClient){
            const company = isCompanyArray(clientTextsFieldsValues.selectedOptions)
            ? clientTextsFieldsValues.selectedOptions[0]
            : null;
            const {name, email, phone, address } = clientTextsFieldsValues;
            const clientObject = {
                id: clickedEntity.id,
                name,
                surname: clientTextsFieldsValues.surname || "",
                email: email,
                phone: phone,
                address: address,
                company
            }
            setIsFormsValid(true);
            setValueToSend(clientObject)
        }
    }

    useEffect(() => {
        if(isFormsValid && valueToSend){
            const url = `http://localdev:8082/api/clients/${clickedEntity?.id}`;
            sendData({url, value: valueToSend})
        }

    }, [valueToSend, isFormsValid])

    return (
        <Box className="editContainer">
             {sendRequestStatus.status === "success" ? 
                <Alert severity="success">{sendRequestStatus.message}</Alert> : 
            sendRequestStatus.status === "error" ?
                <Alert severity="error">{sendRequestStatus.message}</Alert> : null
            }
            <Box className="clientTextFields">
                <ClientTextFields validateFields={validateFields} errors={errors}/>
            </Box>
            <Box className="emailContainer">
                <ListOfEmails />
            </Box>
            <Box className="buttonContainer">
            <Button variant="contained" onClick={handleSaveClick}>SAVE</Button>
            </Box>
        </Box>
        
    );
}

export default EditClientContainer;