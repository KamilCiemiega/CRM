import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { DataGrid, GridColDef, GridRowParams, GridRowSelectionModel} from '@mui/x-data-grid';
import { AppDispatch, RootState } from "../../store";
import { Rows } from "../../../interfaces/interfaces";
import useParticipantsData from "../../../hooks/useParticipantData";
import { emailPreviewAction } from "../../store/slices/emailSlices/emailPreview-slice";
import { emailListAction } from "../../store/slices/emailSlices/emailList-slice";
import { Alert, Button } from "@mui/material";
import { handleResetDataToDisplay } from "../../store/thunks/handleResetDataToDisplay";


export interface MessageRole {
  status: "TO" | "CC";
  participantId: number;
}

const TableDataComponent = () => {
    const dispatch = useDispatch<AppDispatch>();
    const filtredListOfMessages = useSelector((state: RootState) => state.emailList.filtredMessages);
    const showMessagePreview = useSelector((state: RootState)=> state.emailPreview.showMessagePreview);
    const participantsData = useSelector((state: RootState) => state.emailPreview.dataToDisplay.participant);
    const shouldShowPreview = useSelector((state: RootState) => state.emailPreview.shouldShowPreview);
    const clickedChecboxes = useSelector((state: RootState) => state.emailList.clickedCheckboxes);
    const { loadingData } = useParticipantsData();
    const [showAlert, setAlert] = useState(false);
    const [selectedRows, setSelectedRows] = useState<GridRowSelectionModel>([]);

    const columns: GridColDef[] = [
      { field: 'status', headerName: 'Status', flex: 1 },
      { field: 'subject', headerName: 'Subject', flex: 2 },
      { field: 'sendDate', headerName: 'Time', flex: 1 },
      { field: 'size', headerName: 'Size', flex: 1 },
    ];

    const formatDate = (timestamp: string) => {
      const date = new Date(timestamp).toISOString(); 
      const lastDotIntex = date.lastIndexOf('.');
      const trimmedString = lastDotIntex !== -1 ? date.slice(0, lastDotIntex) : date;

      return trimmedString;
    };

    const rowsFunction = (): Rows[] => {
      return filtredListOfMessages.length > 0
        ? filtredListOfMessages.map((m, i) => ({
            id: i,
            status: m.status,
            subject: m.subject,
            sendDate: formatDate(m.sentDate),
            size: m.size,
          }))
        : [];
    };

    const paginationModel = { page: 0, pageSize: 20 };

    const handleDataToDisplay = (rowParam?: number, checkboxParam?: number) => {
      if (rowParam === undefined && checkboxParam === undefined) return;
    
      const index = rowParam ?? checkboxParam;
      if (index !== undefined && typeof index === "number") {
        const messageObject = filtredListOfMessages[index];
        const messageRoles = messageObject.messageRoles;
    
        dispatch(emailPreviewAction.setMessageRoles(messageRoles));
        dispatch(emailPreviewAction.setDataToDisplay({
          body: messageObject.body,
          subtitle: messageObject.subject,
          attachmentsNumber: messageObject.attachments.length
        }));
      }
    };

    const handleRowClick = (params: GridRowParams) => {
      handleDataToDisplay(Number(params.row.id))
      dispatch(emailPreviewAction.setShouldShowPreview(true));
    };

    const handleSelectionChange = (newSelection: GridRowSelectionModel) => {
      dispatch(emailListAction.setClickedChecboxes(Array.from(newSelection) as string[]));
      setSelectedRows(newSelection);

      const lengthOfTable = newSelection.length;
      if(lengthOfTable > 1) {
        setAlert(true);
      }else if(lengthOfTable === 1) {
        const selectedId = newSelection[0];
        handleDataToDisplay(undefined, typeof selectedId === "number" ? selectedId : undefined);
      }
      dispatch(handleResetDataToDisplay())
    };

    useEffect(() => {
      if(selectedRows.length > 0){
        setSelectedRows([]);
      }
    }, [handleResetDataToDisplay, dispatch])
  
    useEffect(() => {
      if (participantsData.length > 0 && !showMessagePreview && shouldShowPreview) {
          dispatch(emailPreviewAction.setMessagePreview(true));
      }
    }, [participantsData, dispatch, showMessagePreview, shouldShowPreview]);

    const resetSelection = () => {
      dispatch(emailListAction.setClickedChecboxes([]));
    }

    return (
      <>
      {/* <Alert  severity="warning"></Alert> */}
      <Button onClick={resetSelection}>Click me</Button>
        <DataGrid
        rows={rowsFunction()}
        columns={columns}
        initialState={{ pagination: { paginationModel } }}
        pageSizeOptions={[10, 20]}
        checkboxSelection
        rowSelectionModel={selectedRows}
        onRowSelectionModelChange={handleSelectionChange}
        onRowClick={handleRowClick}
        sx={{
          border: 0,
          "& .MuiDataGrid-row": {
            cursor: "pointer"
          }
        }}
      />
      </>
    );
}

export default TableDataComponent;