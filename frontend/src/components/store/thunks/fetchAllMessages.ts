import axios from "axios";
import { AppDispatch } from "..";
import { emailListAction } from "../slices/emailSlices/emailList-slice";
import { handleError } from "./helperFunctions/handleError";

export const fetchAllMessages = () => {
    return async (dispatch: AppDispatch) => {
        const fetchData = async () => {
            const response = await axios.get("http://localdev:8082/api/messages");
            return response.data;
        }
        try{
            const allMessages = await fetchData();
            dispatch(emailListAction.setAllMessages(allMessages))
        }catch (error) {
            console.log(handleError(error));
        }
      
    }
}