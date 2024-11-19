import axios, { AxiosError } from "axios";
import { Message } from "../../../interfaces/interfaces";


export const updateMessageFolder = async (messageId: number, payload: Message) => {
   try{
    const response = await axios.post(`http://localdev:8082/api/messages/${messageId}`, payload);
    if (response.status === 200) {
        console.log("Request was successful");
    } else {
        console.warn(`Unexpected response status: ${response.status}`);
    }
   }catch (error) {
    if (axios.isAxiosError(error)) {
        console.error("Axios error:", (error as AxiosError).message);
    } else if (error instanceof Error) {
        console.error("General error:", error.message);
    } else {
        console.error("Unknown error occurred");
    }
    }
}