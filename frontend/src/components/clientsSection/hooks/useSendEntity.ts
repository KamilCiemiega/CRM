import axios from "axios";
import { handleError } from "../../store/thunks/helperFunctions/handleError";
import { useDispatch } from "react-redux";
import { clientViewAction } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { AppDispatch } from "../../store";
import { useState } from "react";
import { Message } from "../../../interfaces/interfaces";

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


type PropsValues = {
    url: string;
    value: NewClientEntity | NewCompanyEntity | number[];
    getData?: boolean;
}


const useSendEntity = () => {
const dispatch = useDispatch<AppDispatch>();
const [ clientMessages, setClientMessages ] = useState<Message[]>([])

const sendData = async ({url, value, getData}: PropsValues) => { 
    try{
        const response = await axios.post(url, value);

        if(response.status === 201 || response.status === 200){
            getData?
            setClientMessages(response.data)
            :
            dispatch(clientViewAction.setApiRequestStatus({status: "success", message: "Save succesfully !"}))
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