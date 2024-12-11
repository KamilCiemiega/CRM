import { Box } from "@mui/material";
import TopPanel from "./topPanel/TopPanel";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchClients } from "../store/thunks/fetchClients";
import { fetchCompanies } from "../store/thunks/fetchCompanies";
import { AppDispatch, RootState } from "../store";
import ListOfClientsCompany from "./topPanel/listOfClientsCompany/ListOfClientsCompany";
import NewEntityDialog from "./newEntity/NewEntityDialog"; 
import EditCompanyContainer from "./editEntity/editCompany/EditCompanyContainer";
import EditClientContainer from "./editEntity/editClient/EditClientContainer";

const ClientMainView = () => {
    const dispatch = useDispatch<AppDispatch>();
    const apiRequest = useSelector((state: RootState) => state.clientView.apiRequestStatus);
    const editEntityView = useSelector((state: RootState) => state.clientView.editEntityViewType);

    useEffect(() => {
        dispatch(fetchClients());
        dispatch(fetchCompanies());
    }, [dispatch, apiRequest])

    return (
        <Box sx={{width: '100%', height: '100vh', backgroundColor: '#ffffff'}}>
            <TopPanel />
                {editEntityView === 'companies' ?
                    <EditCompanyContainer /> 
                : editEntityView === "clients" ?
                    <EditClientContainer /> 
                :    
                    <ListOfClientsCompany />  
                }
            <NewEntityDialog />
        </Box>
    );
}

export default ClientMainView;