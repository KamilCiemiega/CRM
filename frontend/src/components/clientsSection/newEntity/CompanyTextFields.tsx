import { Box, Button, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import useValidateFormsValues from "../hooks/useValidateFormsValues";
import { NewCompanyEntity } from "../hooks/useSendEntity";
import useSendEntity from "../hooks/useSendEntity";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import { clientViewAction } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";

interface CompanyTextFieldsProps {
    validateFields: (values: any, options: any) => boolean;
    errors: { [key: string]: string };
}

const CompanyTextFields: React.FC<CompanyTextFieldsProps> = ({ validateFields, errors }) => {
    const dispatch = useDispatch();
    const [formValues, setFormValues] = useState({
        name: "",
        email: "",
        phone: "",
        address: ""
    });
    const [valueToSend, setValueToSend] = useState<NewCompanyEntity>();
    const [isFormsValid, setIsFormsValid] = useState(false);
    const { sendData } = useSendEntity();
    const viewType = useSelector((state: RootState) => state.clientView.viewType);
    const openEditView = useSelector((state: RootState) => state.clientView.editEntityViewType);
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
        if(viewType === 'companies' && openEditView === 'companies'){
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

    }, [viewType, openEditView])

    useEffect(() => {
        dispatch(clientViewAction.setCompanyTextFieldsValues(formValues));
    }, [formValues, setFormValues])

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
            height: '500px',
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
                sx={{ mt: "20px", width: '500px', padding: '5px' }}
            />
        ))}
        {!openEditView &&
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