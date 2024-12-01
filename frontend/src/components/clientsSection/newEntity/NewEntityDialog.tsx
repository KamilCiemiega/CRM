import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField, Typography } from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../store";
import { clientViewAction } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import {Close} from '@mui/icons-material';
import ClientsTextFields from "./ClientTextFields";

const NewEntityDialog = () => {
    const dispatch = useDispatch<AppDispatch>();
    const addNewEntityStatus = useSelector((state: RootState) => state.clientView.openNewEntityDialog);
    const viewType = useSelector((state: RootState) => state.clientView.viewType);




    return (
        <Dialog
            open={addNewEntityStatus} 
            onClose={() => dispatch(clientViewAction.setOpenNewEntityDialog(false))}>
            <DialogTitle sx={{
                display: 'flex', 
                alignItems: 'center', 
                justifyContent: 'space-between', 
                backgroundColor: "#363636", 
                color: 'white'}}
                >
                    ADD NEW {viewType.toUpperCase()}
             <Close sx={{cursor: 'pointer'}}/>   
            </DialogTitle>
            <DialogContent>
                <ClientsTextFields />
            </DialogContent>
        </Dialog>
    );
}

export default NewEntityDialog;