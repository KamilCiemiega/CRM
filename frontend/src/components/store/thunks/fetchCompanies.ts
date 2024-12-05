import axios from "axios";
import { AppDispatch } from "..";
import { handleError } from "./helperFunctions/handleError";
import { clientViewAction, Company } from "../slices/crmViewSlices/clientsViewSlices/clientViewSlice";

export const fetchCompanies = () => {
    return async (dispatch: AppDispatch) => {
        const fetchData = async () => {
            const response = await axios.get("http://localdev:8082/api/company");
            if (response.status === 200) {
                return response.data as Company[];
            } else {
                console.warn(`Unexpected response format or status: ${response.status}`);
                return [];
            }
        };
        try {
            const allCompanyData = await fetchData();
            dispatch(clientViewAction.setCompanyData(allCompanyData));
        } catch (error) {
            console.log(handleError(error));
        }
    };
};