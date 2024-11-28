import axios, { AxiosError } from "axios";
import { emailListAction } from "../slices/emailSlices/emailList-slice";
import { AppDispatch } from "..";


export const updateMessageFolder = (messageId: number, payload: any) => {
    return async (dispatch: AppDispatch) => {
        try{
            const response = await axios.post(`http://localdev:8082/api/messages/${messageId}`, payload);
            if (response.status === 200) {
                dispatch(emailListAction.setFolderMessageRequestStatus({status: "success", message: "Message has been moved to another folder."}))
            } else {
                console.warn(`Unexpected response status: ${response.status}`);
            }
           }catch (error) {
            const errorMessage = axios.isAxiosError(error)
                ? `Axios error: ${error.message}`
                : error instanceof Error
                ? `General error: ${error.message}`
                : "Unknown error occurred";
            dispatch(
                emailListAction.setFolderMessageRequestStatus({
                    status: "error",
                    message: errorMessage,
                })
            );
        }
    }
}