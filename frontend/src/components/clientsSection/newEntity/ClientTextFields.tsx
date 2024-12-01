import React, { useState } from "react";
import { Box, TextField, Select, MenuItem, Chip, OutlinedInput, SelectChangeEvent, Typography, Button } from "@mui/material";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import useValidateEntityFields from "./hooks/useValidateEntityFields";

const ClientsTextFields = () => {
    const [formValues, setFormValues] = useState({
        name: "",
        surname: "",
        email: "",
        phone: "",
        address: "",
    });
    const [selectedOptions, setSelectedOptions] = useState<string[]>([]);
    const companyData = useSelector((state: RootState) => state.clientView.expandedCompanyData);
    const { values, errors, validateForm, handleSubmit, handleChange } = useValidateEntityFields(formValues);

    const handleSaveClick = async () => {
        const validationErrors = await validateForm(); 
        if (Object.keys(validationErrors).length === 0) {
            handleSubmit(); 
        } else {
            console.log("Validation errors:", validationErrors);
        }
    };


    return (
        <Box
            component="form"
            noValidate
            sx={{
                display: "flex",
                justifyContent: "center",
                flexDirection: "column",
            }}
        >
        <TextField
                name="name"
                value={values.name}
                onChange={handleChange} 
                error={!!errors.name}
                helperText={errors.name}
                sx={{ mt: "4%" }}
                required
                label="name"
            />
            <TextField
                name="surname"
                value={values.surname}
                onChange={handleChange}
                sx={{ mt: "4%" }}
                label="surname"
            />
            <TextField
                name="email"
                value={values.email}
                onChange={handleChange}
                error={!!errors.email}
                helperText={errors.email}
                sx={{ mt: "4%" }}
                required
                label="email"
            />
            <TextField
                name="phone"
                value={values.phone}
                onChange={handleChange}
                sx={{ mt: "4%" }}
                label="phone"
            />
            <TextField
                name="address"
                value={values.address}
                onChange={handleChange}
                sx={{ mt: "4%", mb: "10%" }}
                label="address"
            />
            <Typography>Please select the company to which the client belongs</Typography>
            <Select
                multiple
                value={values.selectedOptions}
                onChange={(e) => console.log(e.target.value)} 
                input={<OutlinedInput label="Select Options" />}
                renderValue={(selected) => (
                    <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
                        {(selected as string[]).map((value) => (
                            <Chip key={value} label={value} />
                        ))}
                    </Box>
                )}
                sx={{ mt: "4%" }}
            >
                {companyData.map((company, index) => (
                    <MenuItem key={index} value={company.name}>
                        {company.name}
                    </MenuItem>
                ))}
            </Select>
            <Button
                type="button"
                variant="contained"
                onClick={handleSaveClick}
                sx={{ mt: "15px" }}
            >
                SAVE
            </Button>
        </Box>
    );
};

export default ClientsTextFields;