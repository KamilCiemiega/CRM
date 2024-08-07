import { AppDispatch } from "../../../../store";
import { findUserOrClientEmailAction } from "../../../../store/slices/emailSlices/findUserOrClientEmail-slice";
import { UserAndClient } from "../../../../../interfaces/UserAndClient";


const filterHelper = (input: string, users: UserAndClient[], clients: UserAndClient[]) => {
  const lowerCaseInput = input.toLowerCase().trim();
  const filteredUsers = users.filter(user => user.email.toLowerCase().includes(lowerCaseInput));
  const filteredClients = clients.filter(client => client.email.toLowerCase().includes(lowerCaseInput));
  
  return { filteredUsers, filteredClients, lowerCaseInput };
};

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
    const lastCommaIndex = lowerCaseInput.lastIndexOf(',');

    let filteredUsers: UserAndClient[] = [];
    let filteredClients: UserAndClient[] = [];
    let matchingValue = ''

    if (lastCommaIndex !== -1) {
      const valueAfterLastComma = inputValue.slice(lastCommaIndex + 1).trim();

      if (valueAfterLastComma) {
        const result = filterHelper(valueAfterLastComma, users, clients);
        filteredUsers = result.filteredUsers;
        filteredClients = result.filteredClients;
        matchingValue = result.lowerCaseInput;
        if (filteredUsers.length === 0 && filteredClients.length === 0) {
          console.log("empty")
    
          if (openToSearchBox) {
            dispatch(findUserOrClientEmailAction.setOpenToSearchBox(false));
          }
           else if (openCcSearchBox) {
            dispatch(findUserOrClientEmailAction.setOpenCcSearchBox(false));
          }
        }
        

        return { filteredUsers, filteredClients, matchingValue };
      }
    }

    const result = filterHelper(lowerCaseInput, users, clients);
    filteredUsers = result.filteredUsers;
    filteredClients = result.filteredClients;
    matchingValue = result.lowerCaseInput;

    console.log("filtredUsers" + filteredUsers);

    if (filteredUsers.length === 0 && filteredClients.length === 0) {

      if (openToSearchBox) {
        dispatch(findUserOrClientEmailAction.setOpenToSearchBox(false));
      }
       else if (openCcSearchBox) {
        dispatch(findUserOrClientEmailAction.setOpenCcSearchBox(false));
      }
    }
  

    return { filteredUsers, filteredClients, matchingValue };
  } else {
    return { filteredUsers: [], filteredClients: [] };
  }
};