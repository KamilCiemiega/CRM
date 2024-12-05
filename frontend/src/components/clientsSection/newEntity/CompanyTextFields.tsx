import { Box, Button, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import useValidateFormsValues from "../hooks/useValidateFormsValues";
import { NewCompanyEntity } from "../hooks/useSendEntity";
import useSendEntity from "../hooks/useSendEntity";
import { useSelector } from "react-redux";
import { RootState } from "../../store";


const CompanyTextFields = () => {
    const [formValues, setFormValues] = useState({
        name: "",
        email: "",
        phone: "",
        address: "",
    });
    const [valueToSend, setValueToSend] = useState<NewCompanyEntity>();
    const [isFormsValid, setIsFormsValid] = useState(false);
    const { validateFields, errors } = useValidateFormsValues();
    const { sendData } = useSendEntity();
    const viewType = useSelector((state: RootState) => state.clientView.viewType);
    const openDialog = useSelector((state: RootState) => state.clientView.openEditEntityDialog);
    const clickedEntityData = useSelector((state: RootState) => state.clientView.clickedEntity);

    type TextFieldValue = {
        name: keyof typeof formValues;
        label: string;
        required: boolean;
    };

    const mapTextFieldsValues: TextFieldValue[] = [
        { name: "name", label: "Name", required: true },
        { name: "email", label: "Email", required: true },
        { name: "phone", label: "Phone number", required: false },
        { name: "address", label: "Address", required: false },
    ];

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormValues((prev) => ({ ...prev, [name]: value }));
    };

    const handleSaveClick= () => {
        const isValid = validateFields(formValues, {
            validateSelect: false,
            validateSurname: false,
        });

        if(isValid) {
            const valueToSend = {
                name: formValues.name,
                email: formValues.email,
                phoneNumber: formValues.phone,
                address: formValues.address,
                createdAt: new Date().toISOString()
            }
            setValueToSend(valueToSend);
            setIsFormsValid(true);
        }
    }

    useEffect(() => {
        if(viewType === 'companies' && openDialog){
            if(clickedEntityData != null){
                setFormValues({
                    name: clickedEntityData.name,
                    email: clickedEntityData.email,
                    phone: 'phone' in clickedEntityData ? clickedEntityData.phone : clickedEntityData.phoneNumber,
                    address: clickedEntityData.address
                })
            }
            
            console.log(clickedEntityData)
        }

    }, [viewType, openDialog])

    useEffect(() => {
        if(isFormsValid && valueToSend){
            const endpoint = "http://localdev:8082/api/company";
            sendData({url: endpoint, value: valueToSend});
        }

    }, [isFormsValid, valueToSend])

    return (
        <Box
        component="form"
        noValidate
        sx={{
            width: "100%",
            height: '450px',
            display: "flex",
            flexDirection: "column"
        }}
    >
        {mapTextFieldsValues.map((field) => (
            <TextField
                key={field.name}
                name={field.name}
                value={formValues[field.name as keyof typeof formValues]}
                onChange={handleInputChange}
                error={!!errors[field.name]}
                helperText={errors[field.name]}
                required={field.required}
                label={field.label}
                sx={{ mt: "6%", width: '500px' }}
            />
        ))}
        {!openDialog &&
            <Button
            type="button"
            variant="contained"
            onClick={handleSaveClick}
            sx={{ mt: "15%", width: "30px" }}
        >
            SAVE
        </Button>
        }
        
    </Box>
    );
}

export default CompanyTextFields;