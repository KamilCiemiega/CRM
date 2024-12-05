import React, { useEffect, useState } from "react";
import {
    Box,
    TextField,
    Select,
    MenuItem,
    Chip,
    OutlinedInput,
    Typography,
    Button,
    SelectChangeEvent,
    Alert,
} from "@mui/material";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import useValidateFormsValues from "../hooks/useValidateFormsValues";
import { Company } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import useSendEntity from "../hooks/useSendEntity";
import { NewClientEntity } from "../hooks/useSendEntity";


const ClientTextFields = () => {
    const [formValues, setFormValues] = useState({
        name: "",
        surname: "",
        email: "",
        phone: "",
        address: "",
        selectedOptions: [] as Company[],
    });
    const [isFormsValid, setIsFormsValid] = useState(false);
    const [valueToSend, setValueToSend] = useState<NewClientEntity>();
    const { validateFields, errors } = useValidateFormsValues();
    const { sendData } = useSendEntity();
    const expandedCompanyData = useSelector((state: RootState) => state.clientView.expandedCompanyData);
    const newClientData = useSelector((state: RootState) => state.clientView.selectedNewClient);

    type TextFieldValue = {
        name: keyof typeof formValues;
        label: string;
        required: boolean;
    };

    const mapTextFieldsValues: TextFieldValue[] = [
        { name: "name", label: "Name", required: true },
        { name: "surname", label: "Surname", required: true },
        { name: "email", label: "Email", required: true },
        { name: "phone", label: "Phone number", required: false },
        { name: "address", label: "Address", required: false },
    ];

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormValues((prev) => ({ ...prev, [name]: value }));
    };

    const handleSelectChange = (event: SelectChangeEvent<string[]>) => {
        const selectedIds = event.target.value as string[];
        const selectedCompanies = expandedCompanyData.filter((company) =>
            selectedIds.includes(company.id.toString())
        );
        setFormValues((prev) => ({
            ...prev,
            selectedOptions: selectedCompanies,
        }));
    };

    const handleSaveClick = () => {
        const isValid = validateFields(formValues, {
            validateSelect: true,
            validateSurname: true,
        });

        if (isValid) {
            const valueToSend = {
                clientDTO: {
                    name: formValues.name,
                    surname: formValues.surname,
                    email: formValues.email,
                    phone: formValues.phone,
                    address: formValues.address
                },
                companyDTO: {id: formValues.selectedOptions[0].id}
            } 
            setValueToSend(valueToSend);
            setIsFormsValid(true);
        } 
    };


    useEffect(() => {
        let endpoint = "";
        if(newClientData.email.length > 0){
            setFormValues((prev) => ({ ...prev, email: newClientData.email}));
            endpoint = `http://localdev:8082/api/clients/messages?message-id=${newClientData.messageId}`;
            if (isFormsValid && valueToSend) {
                sendData({url: endpoint, value: valueToSend});
            }
        }else {
            if (isFormsValid && valueToSend) {
                endpoint = "http://localdev:8082/api/clients/messages";
                sendData({url: endpoint, value: valueToSend});
            }
        }
    }, [isFormsValid, valueToSend, newClientData]);

    return (
        <Box
            component="form"
            noValidate
            sx={{
                width: "100%",
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
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
                    sx={{ mt: "4%" }}
                />
            ))}

            <Typography sx={{ mt: "20%", width: "500px" }}>
                Please choose one company
            </Typography>
            <Select
                value={formValues.selectedOptions.map((option) =>
                    option.id.toString()
                )}
                onChange={handleSelectChange}
                input={<OutlinedInput label="Select Options" />}
                renderValue={(selected) => (
                    <Box
                        sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}
                    >
                        {selected.map((id) => {
                            const company = expandedCompanyData.find(
                                (company) =>
                                    company.id.toString() === id
                            );
                            return (
                                <Chip
                                    key={id}
                                    label={company?.name || "Unknown"}
                                />
                            );
                        })}
                    </Box>
                )}
                sx={{ mt: "4%" }}
                error={!!errors.selectedOptions}
            >
                {expandedCompanyData.map((company, index) => (
                    <MenuItem key={index} value={company.id.toString()}>
                        <Box
                            className="listOfClientsImage"
                            sx={{
                                backgroundImage: `url(${company.image})`,
                                width: "40px",
                                height: "40px",
                                backgroundSize: "cover",
                                backgroundPosition: "center",
                                borderRadius: "8px",
                            }}
                        />
                        <Typography sx={{ ml: "20px" }}>
                            {company.name}
                        </Typography>
                    </MenuItem>
                ))}
            </Select>

            {errors.selectedOptions && (
                <Typography
                    color="error"
                    sx={{ mt: "8px", fontSize: "0.8rem" }}
                >
                    {errors.selectedOptions}
                </Typography>
            )}
            <Button
                type="button"
                variant="contained"
                onClick={handleSaveClick}
                sx={{ mt: "15px", width: "30px" }}
            >
                SAVE
            </Button>
        </Box>
    );
};

export default ClientTextFields;