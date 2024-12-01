import { Box } from "@mui/material";
import TopPanel from "./topPanel/TopPanel";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchClients } from "../store/thunks/fetchClients";
import { AppDispatch, RootState } from "../store";
import ListOfClientsCompany from "./topPanel/listOfClientsCompany/ListOfClientsCompany";
import NewEntityDialog from "./newEntity/NewEntityDialog"; 

const ClientMainView = () => {
    const dispatch = useDispatch<AppDispatch>();

    useEffect(() => {
        dispatch(fetchClients());
    }, [dispatch])

    return (
        <Box sx={{width: '100%', height: '100vh', backgroundColor: '#ffffff'}}>
            <TopPanel />
            <ListOfClientsCompany />
            <NewEntityDialog /> 
        </Box>
    );
}

export default ClientMainView;