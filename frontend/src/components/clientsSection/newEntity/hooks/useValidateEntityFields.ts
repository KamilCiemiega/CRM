import { useFormik } from "formik";
import * as yup from "yup";

type EntityFields = {
    name: string;
    surname?: string;
    email: string;
    phone: string;
    address: string;
};

const useValidateEntityFields = ({ name, surname, email, phone, address }: EntityFields) => {
    const formik = useFormik({
        initialValues: {
            name,
            surname: surname || "",
            email,
            phone,
            address,
        },
        validationSchema: yup.object({
            name: yup.string().required("Name is required"),
            email: yup.string().email("Invalid email").required("Email is required"),
        }),
        validateOnChange: false, 
        validateOnBlur: false,  
        onSubmit: async (values) => {
            console.log("Form submitted with values:", values);
        },
    });

    return {
        values: formik.values,
        errors: formik.errors,
        touched: formik.touched,
        handleChange: formik.handleChange,
        handleBlur: formik.handleBlur,
        handleSubmit: formik.handleSubmit,
        validateForm: formik.validateForm,
    };
};

export default useValidateEntityFields;