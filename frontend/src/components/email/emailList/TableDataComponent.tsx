import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { DataGrid, GridColDef, GridRowParams, GridRowSelectionModel } from '@mui/x-data-grid';
import { RootState } from "../../store";
import { Rows } from "../../../interfaces/interfaces";
import useParticipantsData from "../../../hooks/useParticipantData";
import { emailPreviewAction } from "../../store/slices/emailSlices/emailPreview-slice";
import { emailListAction } from "../../store/slices/emailSlices/emailList-slice";

export interface MessageRole {
  status: "TO" | "CC";
  participantId: number;
}

const TableDataComponent = () => {
    const filtredListOfMessages = useSelector((state: RootState) => state.emailList.filtredMessages);
    const showMessagePreview = useSelector((state: RootState)=> state.emailPreview.showMessagePreview);
    const participantsData = useSelector((state: RootState) => state.emailPreview.dataToDisplay.participant);
    const shouldShowPreview = useSelector((state: RootState) => state.emailPreview.shouldShowPreview);
    const { loadingData } = useParticipantsData();
    const dispatch = useDispatch();

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

    const handleRowClick = (params: GridRowParams) => {
      console.log(params);
      const messageObject = filtredListOfMessages[params.row.id];
      const messageRoles = messageObject.messageRoles;
  
      dispatch(emailPreviewAction.setMessageRoles(messageRoles));
  
      dispatch(emailPreviewAction.setDataToDisplay({
          body: messageObject.body,
          subtitle: messageObject.subject,
          attachmentsNumber: messageObject.attachments.length
      }));

      dispatch(emailPreviewAction.setShouldShowPreview(true));
    };

    const handleSelectionChange = (newSelection: GridRowSelectionModel) => {
      dispatch(emailListAction.setClickedChecboxes(Array.from(newSelection) as string[]));
    };
  
    useEffect(() => {
      if (participantsData.length > 0 && !showMessagePreview && shouldShowPreview) {
          dispatch(emailPreviewAction.setMessagePreview(true));
      }
  }, [participantsData, dispatch, showMessagePreview, shouldShowPreview]);

    return (
        <DataGrid
        rows={rowsFunction()}
        columns={columns}
        initialState={{ pagination: { paginationModel } }}
        pageSizeOptions={[10, 20]}
        checkboxSelection
        onRowSelectionModelChange={(newSelection) => handleSelectionChange(newSelection)}
        onRowClick={handleRowClick}
        sx={{
          border: 0,
          "& .MuiDataGrid-row": {
            cursor: "pointer"
          }
        }}
      />
    );
}

export default TableDataComponent;