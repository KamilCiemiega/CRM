import axios from "axios";
import { useEffect, useState } from "react";
import { handleError } from "../../store/thunks/helperFunctions/handleError";
import { Company } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";

type EntityFields = {
    name: string;
    surname?: string;
    email: string;
    phone: string;
    address: string;
    selectedOptions?: Company[];
};

type AdditionalFieldsToValidate = {
    validateSelect: boolean;
    validateSurname: boolean;
}


type ValidationErrors = Partial<Record<keyof EntityFields, string>>;

const useValidateFormsValues = () => {
    const [errors, setErrors] = useState<ValidationErrors>({});

    const validateFields = (values: EntityFields, additionalFields: AdditionalFieldsToValidate): boolean => {
        const newErrors: ValidationErrors = {};

        if (!values.name.trim()) newErrors.name = "Name is required.";
        if(additionalFields.validateSurname && !values.surname?.trim()){
            newErrors.surname = "Surname is required.";
        }
        if (!values.email.trim()) {
            newErrors.email = "Email is required.";
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(values.email)) {
            newErrors.email = "Invalid email format.";
        }

        if (additionalFields.validateSelect && (!values.selectedOptions || values.selectedOptions.length === 0)) {
            newErrors.selectedOptions = "Please select at least one option.";
        }

        setErrors(newErrors);

        return Object.keys(newErrors).length === 0;
    };

    return {
        validateFields,
        errors
    };
};

export default useValidateFormsValues;