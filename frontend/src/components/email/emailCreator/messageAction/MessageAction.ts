import axios from "axios";
import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";



    useEffect(() => {
        try{
            const response = await axios.get("http://localdev:8082/api/message-participant/by-id?participantId=3")
        }catch{

        }
    }, [])


// const MessageAction = () => {



//     return(
        
//     );
// }

// export default MessageAction;