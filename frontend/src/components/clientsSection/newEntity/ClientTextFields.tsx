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
    SelectChangeEvent 
} from "@mui/material";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import useValidateFormsValues from "../hooks/useValidateFormsValues";
import { Company } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";

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

    const { validateFields, errors} = useValidateFormsValues();
    const expandedCompanyData = useSelector((state: RootState) => state.clientView.expandedCompanyData);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormValues((prev) => ({ ...prev, [name]: value }));
    };

    const handleSelectChange = (event: SelectChangeEvent<string[]>) => {
        const selectedIds = event.target.value as string[];
        const selectedCompanies = expandedCompanyData.filter(company =>
            selectedIds.includes(company.id.toString())
        );
        setFormValues((prev) => ({ ...prev, selectedOptions: selectedCompanies }));
    };

    const handleSaveClick = () => {
        const isValid = validateFields(formValues, {validateSelect: true, validateSurname: true} ); 
        isValid && setIsFormsValid(true);

        console.log("Form is valid:", formValues);       
    };

    useEffect(() => {
        const endpoint = "http://localdev:8082/api/clients/messages/31";
        if(isFormsValid){
            
        }
        
    }, [isFormsValid])

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
            <TextField
                name="name"
                value={formValues.name}
                onChange={handleInputChange}
                error={!!errors.name}
                helperText={errors.name}
                sx={{ mt: "4%" }}
                required
                label="Name"
            />
            <TextField
                name="surname"
                value={formValues.surname}
                onChange={handleInputChange}
                error={!!errors.surname}
                helperText={errors.surname}
                sx={{ mt: "4%" }}
                required
                label="Surname"
            />
            <TextField
                name="email"
                value={formValues.email}
                onChange={handleInputChange}
                error={!!errors.email}
                helperText={errors.email}
                sx={{ mt: "4%" }}
                required
                label="Email"
            />
            <TextField
                name="phone"
                value={formValues.phone}
                onChange={handleInputChange}
                error={!!errors.phone}
                helperText={errors.phone}
                sx={{ mt: "4%" }}
                label="Phone number"
            />
            <TextField
                name="address"
                value={formValues.address}
                onChange={handleInputChange}
                error={!!errors.address}
                helperText={errors.address}
                sx={{ mt: "4%" }}
                label="Address"
            />

            <Typography sx={{ mt: "20%", width: '500px' }}>Please choose one company</Typography>
            <Select
    multiple
    value={formValues.selectedOptions.map(option => option.id.toString())}
    onChange={handleSelectChange}
    input={<OutlinedInput label="Select Options" />}
    renderValue={(selected) => (
        <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
            {selected.map((id) => {
                const company = expandedCompanyData.find(company => company.id.toString() === id);
                return <Chip key={id} label={company?.name || "Unknown"} />;
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
            <Typography sx={{ ml: "20px" }}>{company.name}</Typography>
        </MenuItem>
    ))}
</Select>

            {errors.selectedOptions && (
                <Typography color="error" sx={{ mt: "8px", fontSize: "0.8rem" }}>
                    {errors.selectedOptions}
                </Typography>
            )}
            <Button
                type="button"
                variant="contained"
                onClick={handleSaveClick}
                sx={{ mt: "15px", width: '30px' }}
            >
                SAVE
            </Button>
        </Box>
    );
};

export default ClientTextFields;