import { AppDispatch } from "../../../store";
import { findUserOrClientEmailAction } from "../../../store/slices/emailSlices/findUserOrClientEmail-slice";
import { UserAndClient } from "../../../../interfaces/UserAndClient";


export const filterData = (
    inputValue: string,
    users: UserAndClient[],
    clients: UserAndClient[],
    openToSearchBox: boolean,
    openCcSearchBox: boolean,
    dispatch: AppDispatch
  ) => {
    if (inputValue.length > 0) {
      const lowerCaseInput = inputValue.toLowerCase();
  
      const filteredUsers = users.filter((user) =>
        user.email.toLowerCase().includes(lowerCaseInput)
      );
  
      const filteredClients = clients.filter((client) =>
        client.email.toLowerCase().includes(lowerCaseInput)
      );
  
      if (filteredUsers.length === 0 && filteredClients.length === 0) {
        if (openToSearchBox) {
          dispatch(findUserOrClientEmailAction.setOpenToSearchBox(false));
        } else if (openCcSearchBox) {
          dispatch(findUserOrClientEmailAction.setOpenCcSearchBox(false));
        }
      }
  
      return { filteredUsers, filteredClients };
    } else {
      return { filteredUsers: [], filteredClients: [] };
    }
};