import axios from "axios";
import { findUserOrClientEmailAction } from "../slices/emailSlices/findUserOrClientEmail-slice";
import { AppDispatch } from "..";

export const fetchUserAndClientData = () => {
    return async (dispatch: AppDispatch) => {

        const fetchData = async () => {
            const usersResponse = await axios.get(
                "http://localdev:8082/api/auth/get-users"
              );
              const clientsResponse = await axios.get(
                "http://localdev:8082/api/client/get-clients"
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
            
          } catch (error: unknown) {
            if (error instanceof Error) {
              console.error(error.message);
            }else if(axios.isAxiosError(error)){
              console.log(error.message);
            }
            
          }
    }
}