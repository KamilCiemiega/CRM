import { Alert,Dialog, DialogContent, DialogTitle} from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../store";
import { clientViewAction } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import {Close} from '@mui/icons-material';
import ClientsTextFields from "./ClientTextFields";
import { useEffect, useRef} from "react";
import CompanyTextFields from "./CompanyTextFields";

const NewEntityDialog = () => {
    const dispatch = useDispatch<AppDispatch>();
    const addNewEntityStatus = useSelector((state: RootState) => state.clientView.openNewEntityDialog);
    const viewType = useSelector((state: RootState) => state.clientView.viewType);
    const sendRequestStatus = useSelector((state: RootState) => state.clientView.apiRequestStatus);
    const isFirstRender = useRef(true);

    useEffect(() => {
        if (isFirstRender.current) {
            isFirstRender.current = false;
            return;
        }

        const timer = setTimeout(() => {
            dispatch(clientViewAction.setApiRequestStatus({status: "", message: ""}));
            dispatch(clientViewAction.setOpenNewEntityDialog(false));
        }, 3000);

        return () => clearTimeout(timer);
    }, [sendRequestStatus])

    return (
        <Dialog
            open={addNewEntityStatus}
            onClose={() => dispatch(clientViewAction.setOpenNewEntityDialog(false))}>
            {sendRequestStatus.status === "success" ? 
                <Alert severity="success">{sendRequestStatus.message}</Alert> : 
            sendRequestStatus.status === "error" ?
                <Alert severity="error">{sendRequestStatus.message}</Alert> : null
            }
            
            <DialogTitle sx={{
                display: 'flex', 
                alignItems: 'center', 
                justifyContent: 'space-between', 
                backgroundColor: "#363636", 
                color: 'white'}}
                >
                    ADD NEW {viewType.toUpperCase()}
             <Close sx={{cursor: 'pointer'}} onClick={() => dispatch(clientViewAction.setOpenNewEntityDialog(false))}/>   
            </DialogTitle>
            <DialogContent>
                {viewType === 'clients' ?
                    <ClientsTextFields /> :
                    <CompanyTextFields />
                }
            </DialogContent>
        </Dialog>
    );
}

export default NewEntityDialog;