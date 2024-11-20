import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { DataGrid, GridColDef, GridRowParams, GridRowSelectionModel} from '@mui/x-data-grid';
import { AppDispatch, RootState } from "../../../store";
import { Rows } from "../../../../interfaces/interfaces";
import useParticipantsData from "../hooks/useParticipantData";
import { emailPreviewAction } from "../../../store/slices/emailSlices/emailPreview-slice";
import { emailListAction } from "../../../store/slices/emailSlices/emailList-slice";
import SearchTableData from "./SearchTableData";


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
    const { loadingData } = useParticipantsData();
    const [selectedRows, setSelectedRows] = useState<GridRowSelectionModel>([]);
    const [filteredRows, setFilteredRows] = useState<Rows[]>([]);

    const columns: GridColDef[] = [
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
            subject: m.subject,
            sendDate: formatDate(m.sentDate),
            size: m.size,
          }))
        : [];
    };

    const paginationModel = { page: 2, pageSize: 50 };

    const handleDataToDisplay = (rowParam?: number, checkboxParam?: number) => {
      if (rowParam === undefined && checkboxParam === undefined) return;
    
      const index = rowParam ?? checkboxParam;
      if (index !== undefined && typeof index === "number") {
        const messageObject = filtredListOfMessages[index];
        const messageRoles = messageObject.messageRoles;
        dispatch(emailPreviewAction.setClickedMessage(messageObject));
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
   
      if (newSelection.length === 1) {
          const selectedId = newSelection[0];
          handleDataToDisplay(undefined, typeof selectedId === "number" ? selectedId : undefined);
      } 
    };

    useEffect(() => {
      if (participantsData.length > 0 && !showMessagePreview && shouldShowPreview) {
          dispatch(emailPreviewAction.setMessagePreview(true));
      }
    }, [participantsData, dispatch, showMessagePreview, shouldShowPreview]);
  

    return (
      <>
      <SearchTableData rows={rowsFunction()} onSearch={setFilteredRows} />
      <DataGrid
        rows={filteredRows}
        columns={columns}
        initialState={{ pagination: { paginationModel } }}
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