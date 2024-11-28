import axios from "axios";
import { AppDispatch } from "..";
import { clientViewAction } from "../slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { Client } from "../slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { handleError } from "./helperFunctions/handleError";

export const fetchClients = () => {
    return async (dispatch: AppDispatch) => {
        const fetchData = async (): Promise<Client[]> => {
            const response = await axios.get("http://localdev:8082/api/clients");
            if (response.status === 200 && Array.isArray(response.data)) {
                return response.data as Client[];
            } else {
                console.warn(`Unexpected response format or status: ${response.status}`);
                return [];
            }
        };
        try {
            const allClientsData = await fetchData();
            dispatch(clientViewAction.setClientsData(allClientsData));
        } catch (error) {
            console.log(handleError(error));
        }
    };
};