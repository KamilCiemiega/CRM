import axios from "axios";
import { handleError } from "../../store/thunks/helperFunctions/handleError";
import { useDispatch } from "react-redux";
import { clientViewAction } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { AppDispatch } from "../../store";

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

type PropsValues = {
    url: string;
    value: NewClientEntity
}


const useSendEntity = () => {
const dispatch = useDispatch<AppDispatch>();

const sendData = async ({url, value}: PropsValues) => { 
    try{
        const response = await axios.post(url, value);

        if(response.status === 201){
            dispatch(clientViewAction.setApiRequestStatus({status: "success", message: "Save succesfully !"}))
        }

    }catch(error){
        dispatch(clientViewAction.setApiRequestStatus({status: "error", message: handleError(error)}));
    }
}

return ({
    sendData
});

}

export default useSendEntity;