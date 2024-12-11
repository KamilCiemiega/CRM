import axios, { AxiosResponse } from "axios";
import { handleError } from "../../store/thunks/helperFunctions/handleError";
import { useDispatch } from "react-redux";
import { clientViewAction } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { AppDispatch } from "../../store";
import { useState } from "react";
import { Message } from "../../../interfaces/interfaces";
import { Company } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";

export type NewClientEntity = {
    clientDTO: {
        name: string;
        surname: string;
        email: string;
        phone: string;
        address: string;
    },
    companyDTO: {
        id: number;
    }
}

export type NewCompanyEntity = {
    name: string;
    email: string;
    phoneNumber: string;
    address: string;
    createdAt: string;
} 

type PropsValues<T> = {
    url: string;
    value: T;
    getData?: boolean;
}


const useSendEntity = () => {
const dispatch = useDispatch<AppDispatch>();
const [ clientMessages, setClientMessages ] = useState<Message[]>([])

const sendData = async <T>({url, value, getData}: PropsValues<T>) => { 
    try{
        const response: AxiosResponse<T> = await axios({
            url,
            method: 'POST',
            data: value
        }) 

        if (response.status === 201 || response.status === 200) {
            if (getData) {
                setClientMessages(response.data as unknown as Message[]);
            } else {
                console.log("test");
                dispatch(clientViewAction.setApiRequestStatus({
                    status: "success",
                    message: "Save successfully!",
                }));
            }
        }

    }catch(error){
        dispatch(clientViewAction.setApiRequestStatus({status: "error", message: handleError(error)}));
    }
}

return ({
    sendData,
    clientMessages
});

}

export default useSendEntity;