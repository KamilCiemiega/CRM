import axios from "axios";
import { findUserOrClientEmailAction } from "../slices/emailSlices/findUserOrClientEmail-slice";
import { AppDispatch } from "..";
import { handleError } from "./helperFunctions/handleError";

export const fetchUserAndClientData = () => {
    return async (dispatch: AppDispatch) => {

        const fetchData = async () => {
            const usersResponse = await axios.get(
                "http://localdev:8082/api/users"
              );
              const clientsResponse = await axios.get(
                "http://localdev:8082/api/clients"
              );
            return {
                users: usersResponse.data,
                clients: clientsResponse.data
            }
        }
        try {
            const userAndClientData = await fetchData();

            dispatch(findUserOrClientEmailAction.setUsers(userAndClientData.users));
            dispatch(findUserOrClientEmailAction.setClients(userAndClientData.clients));
            
          } catch (error) {
            console.log(handleError(error));
          }
    }
}