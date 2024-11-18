import axios from "axios";
import { AppDispatch } from "..";

export const updateMessageFolder = () => {
    return async (dispatch: AppDispatch) => {
        const postData = async () => {
            const response = await axios.post("http://localdev:8082/api/messages/1", "");
            return response.status
        }
        try{
            await postData();
        }catch (error: unknown) {
            if (error instanceof Error) {
              console.error(error.message);
            }else if(axios.isAxiosError(error)){
              console.log(error.message);
            }
          }
    }
}