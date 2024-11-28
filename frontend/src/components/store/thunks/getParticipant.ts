import { AppDispatch } from "..";
import { sendEmailAction } from "../slices/emailSlices/sendEmail-slice";
import axios from "axios";
import { MessageRoles } from "../../../interfaces/interfaces";
import { handleError } from "./helperFunctions/handleError";

export const getParticipant = (type: string, id: number, openToSearchBox: boolean) => {
  return async (dispatch: AppDispatch) => {
    const fieldType = openToSearchBox ? "TO" : "CC";
    
      try{
        const response = await axios.get(`http://localdev:8082/api/message-participant/by-type-and-id?type=${type.toUpperCase()}&${type}Id=${id}`)
        const messageRoles: MessageRoles = {
          "status": fieldType,
          "participantId": response.data.id
        }
        dispatch(sendEmailAction.addMessageRole(messageRoles));

      }catch(error) {
        console.log(handleError(error));
      }
    }
  }