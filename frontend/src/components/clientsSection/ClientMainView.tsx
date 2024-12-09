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

const ClientMainView = () => {
    const dispatch = useDispatch<AppDispatch>();
    const viewType = useSelector((state: RootState) => state.clientView.viewType);
    const editEntityView = useSelector((state: RootState) => state.clientView.openEditEntityView);

    useEffect(() => {
        dispatch(fetchClients());
        dispatch(fetchCompanies());
    }, [dispatch])

    return (
        <Box sx={{width: '100%', height: '100vh', backgroundColor: '#ffffff'}}>
            <TopPanel />
                {viewType === 'companies' && editEntityView ?
                    <EditCompanyContainer /> : <ListOfClientsCompany />  
                }
            <NewEntityDialog />
        </Box>
    );
}

export default ClientMainView;