import { Box } from "@mui/material";
import TopPanel from "./topPanel/TopPanel";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { fetchClients } from "../store/thunks/fetchClients";
import { AppDispatch } from "../store";
import ListOfClientsCompany from "./topPanel/listOfClientsCompany/ListOfClientsCompany";


const ClientMainView = () => {
    const dispatch = useDispatch<AppDispatch>();

    useEffect(() => {
        dispatch(fetchClients());
    }, [dispatch])

    return (
        <Box sx={{width: '100%', height: '100vh', backgroundColor: '#ffffff'}}>
            <TopPanel />
            <ListOfClientsCompany />
        </Box>
    );
}

export default ClientMainView;