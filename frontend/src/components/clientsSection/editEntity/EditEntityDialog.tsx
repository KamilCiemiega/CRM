import { Close } from "@mui/icons-material";
import { Dialog, DialogContent, DialogTitle } from "@mui/material";
import { clientViewAction } from "../../store/slices/crmViewSlices/clientsViewSlices/clientViewSlice";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store";
import EditCompanyContainer from "./editCompany/EditCompanyContainer";

const EditEntityDialog = () => {
    const dispatch = useDispatch();
    const viewType = useSelector((state: RootState) => state.clientView.viewType);
    const openDialog = useSelector((state: RootState) => state.clientView.openEditEntityDialog);

    return(
        <Dialog
        open={openDialog}
        onClose={() => dispatch(clientViewAction.setOpenEditEntityDialog(false))}
      >
        <DialogTitle
          sx={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
            backgroundColor: '#363636',
            color: 'white',
          }}
        >
          EDIT {viewType.toUpperCase()}
          <Close
            sx={{ cursor: 'pointer' }}
            onClick={() => dispatch(clientViewAction.setOpenEditEntityDialog(false))}
          />
        </DialogTitle>
        <DialogContent sx={{width: '600px', display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
          <EditCompanyContainer />
        </DialogContent>
      </Dialog>
    )
}

export default EditEntityDialog;